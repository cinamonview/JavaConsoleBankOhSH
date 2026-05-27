package banking1;

import java.util.Scanner;

public class AccountManager {
    
	// 계좌 객체를 담을 배열 (최대 50개)
    private Account[] accountArray;
    // 현재 저장된 계좌의 개수를 카운트하는 변수
    private int accountCount;
    //생성자 : 멤버변수 초기화를 기본기능으로 함     
    public AccountManager(int num) {
    	accountArray = new Account[num];
    	accountCount = 0;
    }
    
    // 계좌개설을 위한 함수
    public void makeAccount() {
        System.out.println("***신규계좌개설***");
        //3가지 정보를 입력받는다. 
        System.out.print("계좌번호 : ");        
        String accNum = BankingSystemMain.scanner.next();
        System.out.print("고객이름 : ");
        String name = BankingSystemMain.scanner.next();
        System.out.print("잔고 : ");
        int balance = BankingSystemMain.scanner.nextInt();

        // 
        if (accountCount >= accountArray.length) {
            System.out.println("더 이상 계좌를 개설할 수 없습니다.");
            return;
        }

        // 
        accountArray[accountCount++] = new Account(accNum, name, balance);
        System.out.println("계좌계설이 완료되었습니다.\n");
    }

    // 입 금
    public void depositMoney() {
        System.out.println("***Main 입   금***");
        System.out.println("계좌번호와 입금할 금액을 입력하세요");
        System.out.print("계좌번호:");
        String accNum = BankingSystemMain.scanner.next();
        System.out.print("입금액:");
        int money = BankingSystemMain.scanner.nextInt();

        // 순
        for (int i = 0; i < accountCount; i++) {
            if (accountArray[i].getAccountNumber().equals(accNum)) {
                accountArray[i].deposit(money);
                System.out.println("입금이 완료되었습니다.\n");
                return; // 입금 성공 시 메서드 종료
            }
        }
        // 가이드 조건: 계좌번호가 없다면 별도의 오류 처리 없이 완료 문구만 출력하거나 무시
        System.out.println("입금이 완료되었습니다. (해당 계좌 없음)\n");
    }

    // 출 금
    public void withdrawMoney() {
        System.out.println("***출   금***");
        System.out.println("계좌번호와 출금할 금액을 입력하세요");
        System.out.print("계좌번호:");
        String accNum = BankingSystemMain.scanner.next();
        System.out.print("출금액:");
        int money = BankingSystemMain.scanner.nextInt();

        // 순차 검색을 통해 계좌 찾기
        for (int i = 0; i < accountCount; i++) {
            if (accountArray[i].getAccountNumber().equals(accNum)) {
                // 1단계 제약조건에 따라 잔액 부족 검사 등은 생략하고 바로 차감
                accountArray[i].withdraw(money);
                System.out.println("출금이 완료되었습니다.\n");
                return;
            }
        }
        System.out.println("출금이 완료되었습니다. (해당 계좌 없음)\n");
    }

    // 전체계좌정보출력
    public void showAccInfo() {
        System.out.println("***계좌정보출력***");
        for (int i = 0; i < accountCount; i++) {
            accountArray[i].showAccountInfo();
        }
        System.out.println("전체계좌정보 출력이 완료되었습니다.\n");
    }
}

