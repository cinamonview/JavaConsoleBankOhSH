package banking5;

import java.io.*;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;

public class AccountManager {
    
    private HashSet<Account> accountSet;
         
    public AccountManager() {
        accountSet = new HashSet<Account>();
        // 프로그램 시작 직후 복원할 수 있도록 생성자에서 파일을 로드합니다.
        loadAccount();
    }
    
    public void makeAccount() {
        System.out.println("-----계좌선택------");
        System.out.println("1.보통예금계좌 2.고신용계좌");
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

        if (choice == 1) {
            System.out.print("기본이율(%) : ");
            int interRate = BankingSystemMain.scanner.nextInt();
            newAccount = new NormalAccount(accNum, name, balance, interRate);
            
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
        }

        if (newAccount != null) {
            boolean isAdded = accountSet.add(newAccount);
            
            if (!isAdded) {
                System.out.println("중복계좌발견됨. 덮어쓸까요?(y or n)");
                System.out.print("선택: ");
                String answer = BankingSystemMain.scanner.next();
                
                if (answer.equalsIgnoreCase("y")) {
                    accountSet.remove(newAccount);
                    accountSet.add(newAccount);
                    System.out.println("기존 정보 위에 덮어쓰기 처리가 완료되었습니다.\n");
                } else {
                    System.out.println("새로운 정보가 무시되고 기존 정보가 유지되었습니다.\n");
                }
            } else {
                System.out.println("계좌 개설이 완료되었습니다.\n");
            }
        }
    }
    
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
            BankingSystemMain.scanner.nextLine(); 
            return;
        }

        if (money < 0) {
            System.out.println("\n[오류] 음수를 입금할 수 없습니다.\n");
            return;
        }
        if (money % 500 != 0) {
            System.out.println("\n[오류] 입금액은 500원 단위로만 가능합니다.\n");
            return;
        }

        for (Account acc : accountSet) {
            if (acc.getAccountNumber().equals(accNum)) {
                acc.deposit(money);
                System.out.println("입금이 완료되었습니다.\n");
                return;
            }
        }
        System.out.println("해당 계좌가 존재하지 않습니다.\n");
    }

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
            BankingSystemMain.scanner.nextLine(); 
            return;
        }

        if (money < 0) {
            System.out.println("\n[오류] 음수를 출금할 수 없습니다.\n");
            return;
        }
        if (money % 1000 != 0) {
            System.out.println("\n[오류] 출금은 1000원 단위로만 가능합니다.\n");
            return;
        }

        for (Account acc : accountSet) {
            if (acc.getAccountNumber().equals(accNum)) {
                if (acc.getAccountBalance() < money) {
                    System.out.println("잔고가 부족합니다. 금액전체를 출금할까요?");
                    System.out.print("YES(Y) / NO(N) : ");
                    String answer = BankingSystemMain.scanner.next();
                    
                    if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
                        int allBalance = acc.getAccountBalance();
                        acc.withdraw(allBalance);
                        System.out.println("금액 전체(" + allBalance + "원) 출금이 완료되었습니다.\n");
                    } else {
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

    public void deleteAccount() {
        System.out.println("***계좌정보삭제***");
        System.out.print("삭제할 계좌번호를 입력하세요: ");
        String accNum = BankingSystemMain.scanner.next();

        Iterator<Account> itr = accountSet.iterator();
        while (itr.hasNext()) {
            Account acc = itr.next();
            if (acc.getAccountNumber().equals(accNum)) {
                itr.remove(); 
                System.out.println("계좌정보 삭제가 완료되었습니다.\n");
                return;
            }
        }
        System.out.println("일치하는 계좌정보가 존재하지 않습니다.\n");
    }

    public void showAccInfo() {
        System.out.println("***계좌정보출력***");
        for (Account acc : accountSet) {
            acc.showAccountInfo();
        }
        System.out.println("전체계좌정보 출력이 완료되었습니다.\n");
    }

    // [5단계 핵심] 가이드라인 A 반영: 프로그램을 종료하는 시점에 파일로 저장
    public void saveAccount() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("AccountInfo.obj"));
            // HashSet 컬렉션 객체를 통째로 직렬화하여 파일에 기록합니다.
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

    // [5단계 핵심] 가이드라인 B 반영: 프로그램 시작 직후 복원
    @SuppressWarnings("unchecked")
    public void loadAccount() {
        File file = new File("AccountInfo.obj");
        
        // 데이터 파일이 아직 생성되지 않았다면 복원을 건너뜁니다.
        if (!file.exists()) {
            System.out.println("AccountInfo.obj 파일없음");
            return;
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            // 역직렬화하여 저장되어 있던 HashSet 데이터를 복원하고 강제 형변환합니다.
            accountSet = (HashSet<Account>) in.readObject();
            System.out.println("AccountInfo.obj 복원완료");
        } catch (Exception e) {
            System.out.println("[오류] 파일 복원 중 문제가 발생했습니다: " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {}
        }
    }
}