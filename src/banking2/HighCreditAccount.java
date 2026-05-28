package banking2;

public class HighCreditAccount extends Account {
    private int interRate;      // 기본 이율 (%)
    private String creditLevel; // 신용등급 (A, B, C)
    private int creditRate;     // 신용 추가 이율 (%)

    // 생성자를 통해 이율정보를 초기화할 수 있도록 정의
    public HighCreditAccount(String accountNumber, String accountName, int accountBalance, int interRate, String creditLevel) {
        super(accountNumber, accountName, accountBalance);
        this.interRate = interRate;
        this.creditLevel = creditLevel;
        
        // 등급에 따른 신용이자 매핑 (ICustomDefine 상수 사용)
        if (creditLevel.equalsIgnoreCase("A")) {
            this.creditRate = ICustomDefine.A_GRADE_RATE;
        } else if (creditLevel.equalsIgnoreCase("B")) {
            this.creditRate = ICustomDefine.B_GRADE_RATE;
        } else if (creditLevel.equalsIgnoreCase("C")) {
            this.creditRate = ICustomDefine.C_GRADE_RATE;
        } else {
            this.creditRate = 0;
        }
    }

    // 이자를 적용하여 입금 메서드 오버라이딩 (기본이율 + 신용이율 통합 계산)
    @Override
    public void deposit(int money) {
        double totalRate = (interRate + creditRate) / 100.0;
        int interest = (int)(money * totalRate);
        super.deposit(money + interest);
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