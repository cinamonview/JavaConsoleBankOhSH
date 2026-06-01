package banking6.threeby3;

import java.io.*;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;

public class AccountManager {
    
    // 인스턴스 배열을 대체하는 HashSet 컬렉션 구조
    private HashSet<Account> accountSet;
         
    public AccountManager() {
        accountSet = new HashSet<Account>();
        // 5단계 가이드 B: 프로그램 시작 직후 자동으로 데이터를 불러와 복원
        loadAccount();
    }
    
    // 1~2단계 및 7단계 추가 미션: 계좌개설 재정의
    public void makeAccount() {
        System.out.println("-----계좌선택------");
        System.out.println("1.보통예금계좌 2.고신용계좌 3.특판계좌");
        System.out.print("선택: ");
        int choice = BankingSystemMain.scanner.nextInt();
        
        System.out.println("***신규계좌개설***");
        System.out.print("계좌번호 : ");        
        String accNum = BankingSystemMain.scanner.next();
        System.out.print("고객이름 : ");
        String name = BankingSystemMain.scanner.next();
        System.out.print("잔고 : ");
        int balance = BankingSystemMain.scanner.nextInt();

        Account newAccount = null;

        // 2단계: 일반계좌 개설
        if (choice == 1) {
            System.out.print("기본이율(%) : ");
            int interRate = BankingSystemMain.scanner.nextInt();
            newAccount = new NormalAccount(accNum, name, balance, interRate);
            
        // 2단계: 고신용계좌 개설
        } else if (choice == 2) {
            System.out.print("기본이율(%) : ");
            int interRate = BankingSystemMain.scanner.nextInt();
            String creditLevel = "";
            
            while (true) {
                System.out.print("신용등급(A/B/C) : ");
                creditLevel = BankingSystemMain.scanner.next().toUpperCase();
                if (creditLevel.equals("A") || creditLevel.equals("B") || creditLevel.equals("C")) {
                    break; 
                }
                System.out.println("[오류] 신용등급은 A, B, C 중 하나만 입력해야 합니다. 다시 입력해 주세요.");
            }
            newAccount = new HighCreditAccount(accNum, name, balance, interRate, creditLevel);
            
        // 7단계 추가미션: 특판계좌 개설
        } else if (choice == 3) {
            System.out.print("기본이율(%) : ");
            int interRate = BankingSystemMain.scanner.nextInt();
            // 개설 시점은 입금으로 치지 않으므로 내부 카운트는 0으로 세팅됨
            newAccount = new SpecialAccount(accNum, name, balance, interRate);
        }

        if (newAccount != null) {
            // 4단계 가이드 B: HashSet 구조를 이용한 중복 확인 (equals, hashCode 기반)
            boolean isAdded = accountSet.add(newAccount);
            
            if (!isAdded) {
                System.out.println("중복계좌발견됨. 덮어쓸까요?(y or n)");
                System.out.print("선택: ");
                String answer = BankingSystemMain.scanner.next();
                
                if (answer.equalsIgnoreCase("y")) {
                    // 4단계 1.2: 기존 중복 인스턴스 삭제 후 재생성 및 저장 (덮어쓰기)
                    accountSet.remove(newAccount);
                    accountSet.add(newAccount);
                    System.out.println("기존 정보 위에 덮어쓰기 처리가 완료되었습니다.\n");
                } else {
                    // 4단계 1.3: 계좌개설 취소 (기존 정보 유지)
                    System.out.println("새로운 정보가 무시되고 기존 정보가 유지되었습니다.\n");
                }
            } else {
                System.out.println("계좌 개설이 완료되었습니다.\n");
            }
        }
    }
    
    // 1단계 E & 3단계 B-2: 입금 비즈니스 로직
    public void depositMoney() {
        System.out.println("***입   금***");
        System.out.println("계좌번호와 입금할 금액을 입력하세요");
        System.out.print("계좌번호:");
        String accNum = BankingSystemMain.scanner.next();
        
        int money = 0;
        System.out.print("입금액:");
        try {
            money = BankingSystemMain.scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\n[오류] 금액 입력 시 문자를 입력할 수 없습니다.\n");
            BankingSystemMain.scanner.nextLine(); // 버퍼 비우기
            return;
        }

        // 3단계 2.1: 음수입금 예외처리
        if (money < 0) {
            System.out.println("\n[오류] 음수를 입금할 수 없습니다.\n");
            return;
        }
        // 3단계 2.3: 입금단위 예외처리
        if (money % 500 != 0) {
            System.out.println("\n[오류] 입금액은 500원 단위로만 가능합니다.\n");
            return;
        }

        // 4단계: 컬렉션 순회를 통한 대상 조회
        for (Account acc : accountSet) {
            if (acc.getAccountNumber().equals(accNum)) {
                // 다형성을 통해 대상 인스턴스(Normal, HighCredit, Special)의 오버라이딩된 deposit 호출
                acc.deposit(money);
                System.out.println("입금이 완료되었습니다.\n");
                return;
            }
        }
        System.out.println("해당 계좌가 존재하지 않습니다.\n");
    }

    // 1단계 F & 3단계 B-3: 출금 비즈니스 로직
    public void withdrawMoney() {
        System.out.println("***출   금***");
        System.out.println("계좌번호와 출금할 금액을 입력하세요");
        System.out.print("계좌번호:");
        String accNum = BankingSystemMain.scanner.next();
        
        int money = 0;
        System.out.print("출금액:");
        try {
            money = BankingSystemMain.scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\n[오류] 금액 입력 시 문자를 입력할 수 없습니다.\n");
            BankingSystemMain.scanner.nextLine(); // 버퍼 비우기
            return;
        }

        // 3단계 3.1: 음수출금 예외처리
        if (money < 0) {
            System.out.println("\n[오류] 음수를 출금할 수 없습니다.\n");
            return;
        }
        // 3단계 3.3: 출금단위 예외처리
        if (money % 1000 != 0) {
            System.out.println("\n[오류] 출금은 1000원 단위로만 가능합니다.\n");
            return;
        }

        for (Account acc : accountSet) {
            if (acc.getAccountNumber().equals(accNum)) {
                // 3단계 3.2: 잔고보다 많은 금액을 출금할 경우 처리 분기
                if (acc.getAccountBalance() < money) {
                    System.out.println("잔고가 부족합니다. 금액전체를 출금할까요?");
                    System.out.print("YES(Y) / NO(N) : ");
                    String answer = BankingSystemMain.scanner.next();
                    
                    if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
                        // b-3.2.1: 전액 출금 처리
                        int allBalance = acc.getAccountBalance();
                        acc.withdraw(allBalance);
                        System.out.println("금액 전체(" + allBalance + "원) 출금이 완료되었습니다.\n");
                    } else {
                        // b-3.2.2: 출금 요청 취소
                        System.out.println("출금요청이 취소되었습니다.\n");
                    }
                } else {
                    acc.withdraw(money);
                    System.out.println("출금이 완료되었습니다.\n");
                }
                return;
            }
        }
        System.out.println("해당 계좌가 존재하지 않습니다.\n");
    }

    // 4단계 C: 계좌정보삭제 구현
    public void deleteAccount() {
        System.out.println("***계좌정보삭제***");
        System.out.print("삭제할 계좌번호를 입력하세요: ");
        String accNum = BankingSystemMain.scanner.next();

        // 4단계 C-2: HashSet 구조 내에서 데이터 일치 요소 추적 및 가변 삭제를 위해 Iterator 사용
        Iterator<Account> itr = accountSet.iterator();
        while (itr.hasNext()) {
            Account acc = itr.next();
            if (acc.getAccountNumber().equals(accNum)) {
                itr.remove(); // 컬렉션에서 인스턴스 영구 삭제
                System.out.println("계좌정보 삭제가 완료되었습니다.\n");
                return;
            }
        }
        // 4단계 C-4: 없으면 없다고 출력
        System.out.println("일치하는 계좌정보가 존재하지 않습니다.\n");
    }

    // 1단계 D: 전체정보출력 구현
    public void showAccInfo() {
        System.out.println("***계좌정보출력***");
        // 4단계 A-1: 인스턴스 배열 구조에서 컬렉션 기반 향상된 for-each 문으로 교체
        for (Account acc : accountSet) {
            acc.showAccountInfo(); // 각 실체 클래스의 오버라이딩 메서드 작동
        }
        System.out.println("전체계좌정보 출력이 완료되었습니다.\n");
    }

    // 5단계 가이드 A: 프로그램 종료 시 컬렉션에 적재된 인스턴스들을 obj 파일로 직렬화 내보내기 (Output)
    public void saveAccount() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("AccountInfo.obj"));
            // 5단계 A-1.2: writeObject 메소드를 인스턴스 값(통합 컬렉션 객체)으로 호출
            out.writeObject(accountSet);
            System.out.println("AccountInfo.obj 파일로 저장되었습니다.");
        } catch (IOException e) {
            System.out.println("[오류] 파일 저장 중 문제가 발생했습니다: " + e.getMessage());
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {}
        }
    }

    // 5단계 가이드 B: 프로그램 시작 직후 자동으로 역직렬화 파일 로드 (Input)
    @SuppressWarnings("unchecked")
    public void loadAccount() {
        File file = new File("AccountInfo.obj");
        
        // 데이터 파일이 없을 때에 대한 조건 방어
        if (!file.exists()) {
            System.out.println("AccountInfo.obj 파일없음");
            return;
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            // 5단계 B-1.1: Object형으로 저장된 데이터를 강제형변환하여 컬렉션 구조에 적재
            accountSet = (HashSet<Account>) in.readObject();
            System.out.println("AccountInfo.obj 복원완료");
        } catch (EOFException e) {
            // 5단계 B-1.1.2: 더이상 읽을 데이터가 없으면 예외처리 블록으로 안전하게 빠져나옴
        } catch (Exception e) {
            System.out.println("[오류] 파일 복원 중 문제가 발생했습니다: " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {}
        }
    }
}