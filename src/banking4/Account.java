package banking4;

public abstract class Account {
    protected String accountNumber;
    protected String accountName;
    protected int accountBalance;
	
    public Account(String accountNumber, String accountName, int accountBalance) {
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
	
    public abstract void deposit(int money);
    
    public void withdraw(int money) {
        this.accountBalance -= money;
    }
	
    public void showAccountInfo() {
        System.out.println(" ############ ");
        System.out.println(" 계좌 번호 " + accountNumber);
        System.out.println(" 고객 이름 : " + accountName);
        System.out.println(" 잔    고 : " + accountBalance);
    }

    // [4단계 핵심] HashSet의 중복 체크를 위한 hashCode() 오버라이딩
    @Override
    public int hashCode() {
        // 계좌번호(String)의 해시코드를 그대로 반환하여 계좌번호가 같으면 같은 해시값이 나오게 합니다.
        return accountNumber != null ? accountNumber.hashCode() : 0;
    }

    // [4단계 핵심] HashSet의 중복 체크를 위한 equals() 오버라이딩
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account other = (Account) obj;
        if (accountNumber == null) {
            return other.accountNumber == null;
        }
        // 계좌번호 문자열이 일치하면 동일한 객체로 판단(true 반환)
        return accountNumber.equals(other.accountNumber);
    }
}