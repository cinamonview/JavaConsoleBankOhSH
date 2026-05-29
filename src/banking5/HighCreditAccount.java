package banking5;

public class HighCreditAccount extends Account {
    private int interRate;      
    private String creditLevel; 
    private int creditRate;     

    public HighCreditAccount(String accountNumber, String accountName, int accountBalance, int interRate, String creditLevel) {
        super(accountNumber, accountName, accountBalance);
        this.interRate = interRate;
        this.creditLevel = creditLevel;
        
        if (creditLevel.equalsIgnoreCase("A")) {
            this.creditRate = ICustomDefine.A_GRADE_RATE;
        } else if (creditLevel.equalsIgnoreCase("B")) {
            this.creditRate = ICustomDefine.B_GRADE_RATE;
        } else if (creditLevel.equalsIgnoreCase("C")) {
            this.creditRate = ICustomDefine.C_GRADE_RATE;
        } else {
            this.creditRate = ICustomDefine.C_GRADE_RATE;
            this.creditLevel = "C";
        }
    }

    @Override
    public void deposit(int money) {
        int basicInterest = (int)(this.accountBalance * (interRate / 100.0));
        int creditInterest = (int)(this.accountBalance * (creditRate / 100.0));
        this.accountBalance = this.accountBalance + basicInterest + creditInterest + money;
    }

    @Override
    public void showAccountInfo() {
        super.showAccountInfo();
        System.out.println(" 기 본 이 율 : " + interRate + "%");
        System.out.println(" 신 용 등 급 : " + creditLevel + "등급");
        System.out.println(" 신용 추가이율: " + creditRate + "%");
        System.out.println(" ############ ");
    }
}