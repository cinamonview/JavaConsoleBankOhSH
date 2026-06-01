package banking6.threeby3;

import java.util.InputMismatchException;

public class BankingSystemMain {
	
    public static java.util.Scanner scanner = new java.util.Scanner(System.in);
	
    public static void showMenu() {
        System.out.println("-----Menu------");
        System.out.println("1.계좌개설");
        System.out.println("2.입\t금");
        System.out.println("3.출\t금");
        System.out.println("4.계좌정보출력");
        System.out.println("5.계좌정보삭제"); 
        System.out.println("6.프로그램종료"); 
        System.out.print("선택:");
    }
    
    public static void main(String[] args) {
		
        // 인스턴스가 생성되는 즉시 가이드라인 B에 따라 내부 콘스트럭터에서 역직렬화를 수행합니다.
        AccountManager manager = new AccountManager();		
		
        while(true) {
            showMenu();
            int choice = 0;         
            try {
                choice = scanner.nextInt();
                System.out.println();
                
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
            case ICustomDefine.DELETE: 
                manager.deleteAccount();
                break;
            case ICustomDefine.EXIT:
                // [가이드라인 반영] 종료 전 직렬화 인스턴스 메서드 호출 및 정상 종료
                manager.saveAccount();
                System.out.println(" 프로그램을 종료합니다. ");
                return;
            } 
        } 
    } 
}