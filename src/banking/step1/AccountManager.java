package banking.step1;

import java.util.Scanner;

public class AccountManager {
    // 계좌 객체를 담을 배열 (최대 50개)
    private Account[] accountArray = new Account[50];
    // 현재 저장된 계좌의 개수를 카운트하는 변수
    private int accountCount = 0;
    
    private static Scanner scanner = new Scanner(System.in);

    // 메뉴 출력
    public void showMenu() {
        System.out.println("-----Menu------");
        System.out.println("1.계좌개설");
        System.out.println("2.입\t금");
        System.out.println("3.출\t금");
        System.out.println("4.계좌정보출력");
        System.out.println("5.프로그램종료");
        System.out.print("선택:");
    }

    // 계좌개설을 위한 함수
    public void makeAccount() {
        System.out.println("***신규계좌개설***");
        System.out.print("계좌번호 : ");
        String accNum = scanner.next();
        System.out.print("고객이름 : ");
        String name = scanner.next();
        System.out.print("잔고 : ");
        int balance = scanner.nextInt();

        // 배열 크기 초과 체크 (방어적 코딩)
        if (accountCount >= accountArray.length) {
            System.out.println("더 이상 계좌를 개설할 수 없습니다.");
            return;
        }

        // 객체 생성 후 배열에 저장 및 카운트 증가 (1단계는 중복검사 없음)
        accountArray[accountCount++] = new Account(accNum, name, balance);
        System.out.println("계좌계설이 완료되었습니다.\n");
    }

    // 입 금
    public void depositMoney() {
        System.out.println("***Main 입   금***");
        System.out.println("계좌번호와 입금할 금액을 입력하세요");
        System.out.print("계좌번호:");
        String accNum = scanner.next();
        System.out.print("입금액:");
        int money = scanner.nextInt();

        // 순차 검색을 통해 계좌 찾기
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
        String accNum = scanner.next();
        System.out.print("출금액:");
        int money = scanner.nextInt();

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