package banking3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystemMain {
	
	//정적변수로 만들면 프로그램 전체에서 사용가능 
	public static Scanner scanner = new Scanner(System.in);
	
    // 메뉴 출력
    public static void showMenu() {
        System.out.println("-----Menu------");
        System.out.println("1.계좌개설");
        System.out.println("2.입\t금");
        System.out.println("3.출\t금");
        System.out.println("4.계좌정보출력");
        System.out.println("5.프로그램종료");
        System.out.print("선택:");
    }
    
	public static void main(String[] args) {
		
		//main에서 메니져 인스턴스 생성시 배열의 크기 50을 전달 
		AccountManager manager = new AccountManager(50);		
		
      
        
        while(true) {
            showMenu();
            int choice = 0;         
            try {
                choice = scanner.nextInt();
                System.out.println();
                
                // 1~5 범위를 벗어나면 수동으로 사용자 정의 예외 발생시킴
                if (choice < ICustomDefine.MAKE || choice > ICustomDefine.EXIT) {
                    throw new MenuSelectException("1~5 사이의 정수만 입력할 수 있습니다.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("\n [오 류] 메뉴 선택은 숫자만 입력!!\n");
                scanner.nextLine();             
                continue;
            }
            catch(MenuSelectException e) {
                // 직접 정의한 예외 처리구문 실행
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
            case ICustomDefine.EXIT:
                System.out.println(" 프로그램을 종료합니다. ");
                return;
            } //switch end
        } //while end       

	} //main end 
} //class end 
