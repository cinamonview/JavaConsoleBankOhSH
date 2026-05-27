package banking1;

import java.util.InputMismatchException;
import java.util.Scanner;

import banking.step1.AccountManager;
import banking.step1.ICustomDefine;

public class BankingSystemMain {
	//github연동
		public static void main(String[] args) {
			
			AccountManager manager = new AccountManager();
			Scanner mainScanner = new Scanner(System.in);
			
			while(true) {
				manager.showMenu();
				int choice = 0;
				
				try {
					choice = mainScanner.nextInt();
					System.out.println();
				}
				catch(InputMismatchException e){
					System.out.println(" \n [오 류] 메뉴 선택은 숫자만 입력!!\n");
					mainScanner.nextLine();
					
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
					manager.makeAccount();
					return;
				default:
					System.out.println(" 잘못된 선택입니다. 1~5 사이의 숫자를 선택해주세요. \n");
				
					
					
				}
			}
		
		
	}
}