package banking5;

import java.util.InputMismatchException;

public class BankingSystemMain {
	
    public static java.util.Scanner scanner = new java.util.Scanner(System.in);
	
    public static void showMenu() {
        System.out.println("-----Menu------");
        System.out.println("1.계좌개설");
        System.out.println("2.입\t금");
        System.out.println("3.출\t금");
        System.out.println("4.계좌정보출력");
        System.out.println("5.계좌정보삭제"); // 추가됨
        System.out.println("6.프로그램종료"); // 번호 변경됨
        System.out.print("선택:");
    }
    
    public static void main(String[] args) {
		
        // 배열 크기를 받지 않는 기본 생성자 호출로 변경
        AccountManager manager = new AccountManager();		
		
        while(true) {
            showMenu();
            int choice = 0;         
            try {
                choice = scanner.nextInt();
                System.out.println();
                
                // 1~6 범위를 벗어나면 예외 던지기
                if (choice < ICustomDefine.MAKE || choice > ICustomDefine.EXIT) {
                    throw new MenuSelectException("1~6 사이의 정수만 입력할 수 있습니다.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("\n [오 류] 메뉴 선택은 숫자만 입력!!\n");
                scanner.nextLine(); 
                continue;
            }
            catch(MenuSelectException e) {
                System.out.println("\n [오 류] " + e.getMessage() + "\n");
                continue;
            }
            
            switch(choice) {
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
            case ICustomDefine.DELETE: // 5번 선택 시 삭제 동작
                manager.deleteAccount();
                break;
            case ICustomDefine.EXIT:
                System.out.println(" 프로그램을 종료합니다. ");
                return;
            } 
        } 
    } 
}