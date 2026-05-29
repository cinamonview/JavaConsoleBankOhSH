package banking3;

public class Account {
	
	private String accountNumber;
	private String accountName;
	private int accountBalance;
	
	public Account(String accountNumber, String accountName,int accountBalance) {
		this.accountNumber = accountNumber;
		this.accountName = accountName;
		this.accountBalance = accountBalance;
		
	}
	
	

	public String getAccountNumber() {
		return accountNumber;
	}
	public int getAccountBalance() {
		return accountBalance;
	}
	
	public void deposit(int money) {
		this.accountBalance += money;
	}
	public void withdraw(int money) {
		this.accountBalance -= money;
	}





	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void showAccountInfo() {
		System.out.println(" ############ ");
		System.out.println(" 계좌 번호 " + accountNumber);
		System.out.println(" 고객 이름 : " + accountName);
		System.out.println(" 잔    고 : " + accountBalance);
		System.out.println(" ############ ");
	}

}
