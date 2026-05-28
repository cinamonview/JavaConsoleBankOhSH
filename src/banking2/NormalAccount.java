package banking2;

public class NormalAccount extends Account {
    private int interRate; // 이율정보(이자비율 %)

    // 생성자를 통해 이율정보를 초기화할 수 있도록 정의
    public NormalAccount(String accountNumber, String accountName, int accountBalance, int interRate) {
        super(accountNumber, accountName, accountBalance);
        this.interRate = interRate;
    }

    // 이자를 적용하여 입금 메서드 오버라이딩 (기본 잔액 증가 + 이자 계산)
    @Override
    public void deposit(int money) {
        // 이자 = 원금 * 이율 / 100
        int interest = (int)(money * (interRate / 100.0));
        super.deposit(money + interest);
    }

    @Override
    public void showAccountInfo() {
        super.showAccountInfo();
        System.out.println(" 기 본 이 율 : " + interRate + "%");
        System.out.println(" ############ ");
    

	
	}

}
