package banking6.threeby3;

// [추가미션] 일반계좌(NormalAccount)를 상속받는 특판계좌
public class SpecialAccount extends NormalAccount {
    private static final long serialVersionUID = 1L;
    
    // 2. 입금회차를 카운트하는 멤버변수 추가
    private int depositCount; 

    public SpecialAccount(String accountNumber, String accountName, int accountBalance, int interRate) {
        // 계좌개설 시점 (입금으로 간주하지 않으므로 count는 0으로 시작)
        super(accountNumber, accountName, accountBalance, interRate);
        this.depositCount = 0;
    }

    // 3. 입금 메서드 오버라이딩
    @Override
    public void deposit(int money) {
        // 3.1. 입금 시 입금회차가 선위증가(++개념) 하도록 설정
        this.depositCount++;
        
        // 우선 NormalAccount의 기본 이자 + 입금액 계산 로직을 호출하여 잔고를 반영합니다.
        // 잔고 + (잔고 * 기본이자) + 입금액
        super.deposit(money);
        
        // 3.2. 2회차(짝수번째) 입금 시 +500원 되도록 if문 작성
        if (this.depositCount % 2 == 0) {
            // 축하금 500원을 잔고에 추가합산
            this.accountBalance += 500; 
            System.out.println("★[특판이벤트] 짝수번째 입금 축하금 500원이 추가 지급되었습니다! ★");
        }
    }

    // 4. 계좌정보출력 오버라이딩
    @Override
    public void showAccountInfo() {
        // NormalAccount의 출력 포맷을 먼저 띄우고 (계좌번호, 이름, 잔고, 기본이율)
        super.showAccountInfo();
        // 4.1. 계좌 정보 출력시 입금회차 변수가 출력되도록 설정
        System.out.println(" 입 금 회 차 : " + this.depositCount + "회차");
        System.out.println(" ############ ");
    }
}