package banking.step1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystemMain {
    public static void main(String[] args) {
        AccountManager manager = new AccountManager();
        Scanner mainScanner = new Scanner(System.in);
        
        while (true) {
            manager.showMenu();
            int choice = 0;
            
            try {
                choice = mainScanner.nextInt();
                System.out.println(); // 실행결과 줄바꿈 맞춤
            } catch (InputMismatchException e) {
                // 숫자가 아닌 문자 입력 시 예외 처리
                System.out.println("\n[오류] 메뉴 선택은 숫자만 입력해 주세요!\n");
                mainScanner.nextLine(); // 입력 버퍼에 남아있는 잘못된 문자 지우기 (필수)
                continue; // 다시 while문 처음으로 돌아가기
            }

            switch (choice) {
                case ICustomDefine.MAKE:
                    manager.makeAccount();
                    break;
                case ICustomDefine.DEPOSIT:
                    manager.depositMoney();
                    break;
                case ICustomDefine.WITHDRAW:
                    manager.withdrawMoney();
                    break;
                case ICustomDefine.INQUIRE:
                    manager.showAccInfo();
                    break;
                case ICustomDefine.EXIT:
                    System.out.println("프로그램을 종료합니다.");
                    mainScanner.close();
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 1~5 사이의 숫자를 선택해주세요.\n");
            }
        }
    }
}