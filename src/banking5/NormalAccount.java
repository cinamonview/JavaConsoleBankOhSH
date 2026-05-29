package banking5;

public class NormalAccount extends Account {
    private int interRate; 

    public NormalAccount(String accountNumber, String accountName, int accountBalance, int interRate) {
        super(accountNumber, accountName, accountBalance);
        this.interRate = interRate;
    }

    @Override
    public void deposit(int money) {
        int interest = (int)(this.accountBalance * (interRate / 100.0));
        this.accountBalance = this.accountBalance + interest + money;
    }

    @Override
    public void showAccountInfo() {
        super.showAccountInfo();
        System.out.println(" 기 본 이 율 : " + interRate + "%");
        System.out.println(" ############ ");
    }
}