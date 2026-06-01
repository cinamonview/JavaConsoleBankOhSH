package banking6.threeby3;

import java.io.Serializable;

// Serializable 마커 인터페이스를 구현하여 직렬화를 허용합니다.
public abstract class Account implements Serializable {
    // 클래스의 직렬화 버전을 명시적으로 관리하기 위한 ID (권장사항)
    private static final long serialVersionUID = 1L;

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

    // HashSet의 중복 판별 메커니즘 유지
    @Override
    public int hashCode() {
        return accountNumber != null ? accountNumber.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account other = (Account) obj;
        if (accountNumber == null) {
            return other.accountNumber == null;
        }
        return accountNumber.equals(other.accountNumber);
    }
}