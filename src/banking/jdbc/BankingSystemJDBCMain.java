package banking.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingSystemJDBCMain implements MenuConnect {

    public static Scanner scanner = new Scanner(System.in);

    // [JDBC 공통 기능] Connection 객체 생성 (2단계)
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(ORACLE_DRIVER); // 1단계: 드라이버 로딩
            con = DriverManager.getConnection(ORACLE_URL, DB_USER, DB_PW);
        } catch (Exception e) {
            System.out.println("DB 연결 실패: " + e.getMessage());
        }
        return con;
    }

    public static void showMenu() {
        System.out.println("============= JDBC Menu =============");
        System.out.println("1.계좌개설, 2.입금, 3.출금, 4.전체계좌정보출력");
        System.out.println("5.지정계좌정보출력, 6.계좌삭제, 7.종료");
        System.out.println("=====================================");
        System.out.print("선택: ");
    }

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1: makeAccount(); break;
                case 2: depositMoney(); break;
                case 3: withdrawMoney(); break;
                case 4: showAllAccounts(); break;
                case 5: showAccountByNumber(); break;
                case 6: deleteAccount(); break;
                case 7:
                    System.out.println("JDBC 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("1~7 사이의 메뉴를 선택해주세요.\n");
            }
        }
    }

    // 1. 계좌개설 (INSERT 문, PreparedStatement 사용, 시퀀스 적용)
    public static void makeAccount() {
        System.out.println("***신규계좌개설***");
        System.out.print("계좌번호 : ");
        String accNum = scanner.next();
        System.out.print("고객이름 : ");
        String name = scanner.next();
        System.out.print("잔고 : ");
        int balance = scanner.nextInt();
        System.out.print("이자율(%) : ");
        int interRate = scanner.nextInt();

        Connection con = getConnection();
        PreparedStatement psmt = null;

        try {
            // seq_banking_idx 시퀀스를 사용하여 일련번호 자동 생성
            String sql = "INSERT INTO banking (idx, account_number, name, balance, interest_rate) "
                       + "VALUES (seq_banking_idx.NEXTVAL, ?, ?, ?, ?)";
            
            psmt = con.prepareStatement(sql);
            psmt.setString(1, accNum);
            psmt.setString(2, name);
            psmt.setInt(3, balance);
            psmt.setInt(4, interRate);

            int result = psmt.executeUpdate();
            if (result > 0) {
                System.out.println("계좌 개설이 완료되었습니다.\n");
            }
        } catch (SQLException e) {
            System.out.println("[오류] 계좌개설 실패 (중복 계좌 확인 요망): " + e.getMessage() + "\n");
        } finally {
            closeResources(psmt, con);
        }
    }

    // 2. 입금 (UPDATE 문으로 구현, 가이드라인 지정 이자 계산 공식 적용)
    public static void depositMoney() {
        System.out.println("***입   금***");
        System.out.print("계좌번호: ");
        String accNum = scanner.next();
        System.out.print("입금액: ");
        int money = scanner.nextInt();

        Connection con = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            // 이자율 계산을 위해 먼저 현재 잔액과 이자율을 조회합니다.
            String selectSql = "SELECT balance, interest_rate FROM banking WHERE account_number = ?";
            psmt = con.prepareStatement(selectSql);
            psmt.setString(1, accNum);
            rs = psmt.executeQuery();

            if (rs.next()) {
                int currentBalance = rs.getInt("balance");
                int interestRate = rs.getInt("interest_rate");

                // 가이드라인 수식: 잔액 + (잔액 * 기본이자) + 입금액
                // ※ interest_rate가 정수(예: 5)이므로 100.0으로 나누어 연산 처리
                int newBalance = currentBalance + (int)(currentBalance * (interestRate / 100.0)) + money;

                // 자원 재사용을 위해 한번 닫고 복구
                psmt.close();

                // UPDATE문 실행
                String updateSql = "UPDATE banking SET balance = ? WHERE account_number = ?";
                psmt = con.prepareStatement(updateSql);
                psmt.setInt(1, newBalance);
                psmt.setString(2, accNum);

                psmt.executeUpdate();
                System.out.println("입금(기본이자 반영)이 완료되었습니다.\n");
            } else {
                System.out.println("해당 계좌번호가 존재하지 않습니다.\n");
            }
        } catch (SQLException e) {
            System.out.println("입금 처리 중 에러 발생: " + e.getMessage() + "\n");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            closeResources(psmt, con);
        }
    }

    // 3. 출금 (UPDATE 문으로 구현, 잔액 검증 로직 필수 포함)
    public static void withdrawMoney() {
        System.out.println("***출   금***");
        System.out.print("계좌번호: ");
        String accNum = scanner.next();
        System.out.print("출금액: ");
        int money = scanner.nextInt();

        Connection con = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            // 잔액 한도 검증을 위해 현재 잔액 조회
            String selectSql = "SELECT balance FROM banking WHERE account_number = ?";
            psmt = con.prepareStatement(selectSql);
            psmt.setString(1, accNum);
            rs = psmt.executeQuery();

            if (rs.next()) {
                int currentBalance = rs.getInt("balance");

                // 가이드라인 제약: 출금은 잔액 내에서 가능(잔액보다 큰 금액은 출금 불가)
                if (currentBalance < money) {
                    System.out.println("잔액부족: 잔액보다 큰 금액은 출금할 수 없습니다.\n");
                    return;
                }

                int newBalance = currentBalance - money;
                psmt.close();

                // UPDATE 실행
                String updateSql = "UPDATE banking SET balance = ? WHERE account_number = ?";
                psmt = con.prepareStatement(updateSql);
                psmt.setInt(1, newBalance);
                psmt.setString(2, accNum);

                psmt.executeUpdate();
                System.out.println("출금이 완료되었습니다.\n");
            } else {
                System.out.println("해당 계좌번호가 존재하지 않습니다.\n");
            }
        } catch (SQLException e) {
            System.out.println("출금 처리 중 에러 발생: " + e.getMessage() + "\n");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            closeResources(psmt, con);
        }
    }

    // 4. 전체계좌정보출력 (SELECT 문, ORDER BY idx로 개설된 순서 보장)
    public static void showAllAccounts() {
        System.out.println("***전체계좌정보출력***");

        Connection con = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            // idx(시퀀스 순서) 오름차순 정렬로 개설된 순서대로 출력 만족
            String sql = "SELECT * FROM banking ORDER BY idx ASC";
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery();

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println(" ############ ");
                System.out.println(" 일련번호 : " + rs.getInt("idx"));
                System.out.println(" 계좌번호 : " + rs.getString("account_number"));
                System.out.println(" 고객이름 : " + rs.getString("name"));
                System.out.println(" 잔    액 : " + rs.getInt("balance"));
                System.out.println(" 이 자 율 : " + rs.getInt("interest_rate") + "%");
            }
            if (!hasData) {
                System.out.println("개설된 계좌가 존재하지 않습니다.");
            }
            System.out.println("전체계좌정보 출력이 완료되었습니다.\n");
        } catch (SQLException e) {
            System.out.println("조회 중 오류 발생: " + e.getMessage() + "\n");
        } finally {
            closeResources(rs, psmt, con);
        }
    }

    // 5. 지정계좌정보출력 (SELECT 문, WHERE 조건절 적용)
    public static void showAccountByNumber() {
        System.out.println("***지정계좌정보출력***");
        System.out.print("조회할 계좌번호를 입력하세요: ");
        String accNum = scanner.next();

        Connection con = getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM banking WHERE account_number = ?";
            psmt = con.prepareStatement(sql);
            psmt.setString(1, accNum);
            rs = psmt.executeQuery();

            if (rs.next()) {
                System.out.println(" ############ ");
                System.out.println(" 일련번호 : " + rs.getInt("idx"));
                System.out.println(" 계좌번호 : " + rs.getString("account_number"));
                System.out.println(" 고객이름 : " + rs.getString("name"));
                System.out.println(" 잔    액 : " + rs.getInt("balance"));
                System.out.println(" 이 자 율 : " + rs.getInt("interest_rate") + "%");
                System.out.println(" ############ \n");
            } else {
                System.out.println("입력하신 계좌번호와 일치하는 정보가 없습니다.\n");
            }
        } catch (SQLException e) {
            System.out.println("조회 중 오류 발생: " + e.getMessage() + "\n");
        } finally {
            closeResources(rs, psmt, con);
        }
    }

    // 6. 계좌삭제 (DELETE 문으로 구현)
    public static void deleteAccount() {
        System.out.println("***계좌정보삭제***");
        System.out.print("삭제할 계좌번호를 입력하세요: ");
        String accNum = scanner.next();

        Connection con = getConnection();
        PreparedStatement psmt = null;

        try {
            String sql = "DELETE FROM banking WHERE account_number = ?";
            psmt = con.prepareStatement(sql);
            psmt.setString(1, accNum);

            int result = psmt.executeUpdate();
            if (result > 0) {
                System.out.println("요청하신 계좌 정보가 데이터베이스에서 완전히 삭제되었습니다.\n");
            } else {
                System.out.println("일치하는 계좌번호가 없어 삭제를 수행하지 못했습니다.\n");
            }
        } catch (SQLException e) {
            System.out.println("삭제 중 오류 발생: " + e.getMessage() + "\n");
        } finally {
            closeResources(psmt, con);
        }
    }

    // [JDBC 공통 기능] 5단계 자원 해제 전용 메서드 (오버로딩 구조)
    private static void closeResources(PreparedStatement psmt, Connection con) {
        try { if (psmt != null) psmt.close(); } catch (SQLException e) {}
        try { if (con != null) con.close(); } catch (SQLException e) {}
    }

    private static void closeResources(ResultSet rs, PreparedStatement psmt, Connection con) {
        try { if (rs != null) rs.close(); } catch (SQLException e) {}
        try { if (psmt != null) psmt.close(); } catch (SQLException e) {}
        try { if (con != null) con.close(); } catch (SQLException e) {}
    }
}