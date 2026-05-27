package banking.step1;

public class Account {
    private String accountNumber; // 계좌번호
    private String ownerName;     // 고객이름
    private int balance;          // 잔고

    // 생성자
    public Account(String accountNumber, String ownerName, int balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    // 게터 및 세터
    public String getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    // 입금 처리를 위한 메서드
    public void deposit(int money) {
        this.balance += money;
    }

    // 출금 처리를 위한 메서드
    public void withdraw(int money) {
        this.balance -= money;
    }

    // 계좌 정보 출력 메서드
    public void showAccountInfo() {
        System.out.println("-------------");
        System.out.println("계좌번호 : " + accountNumber);
        System.out.println("고객이름 : " + ownerName);
        System.out.println("잔고 : " + balance);
        System.out.println("-------------");
    }
}