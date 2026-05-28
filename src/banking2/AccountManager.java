package banking2;

public class AccountManager {
    
    private Account[] accountArray;
    private int accountCount;
         
    public AccountManager(int num) {
        accountArray = new Account[num];
        accountCount = 0;
    }
    
    // 2-D. 매니저클래스의 계좌개설 재정의
    public void makeAccount() {
        System.out.println("-----계좌선택------");
        System.out.println("1.보통예금계좌 2.고신용계좌");
        System.out.print("선택: ");
        int choice = BankingSystemMain.scanner.nextInt();
        
        System.out.println("***신규계좌개설***");
        System.out.print("계좌번호 : ");        
        String accNum = BankingSystemMain.scanner.next();
        System.out.print("고객이름 : ");
        String name = BankingSystemMain.scanner.next();
        System.out.print("잔고 : ");
        int balance = BankingSystemMain.scanner.nextInt();

        if (accountCount >= accountArray.length) {
            System.out.println("더 이상 계좌를 개설할 수 없습니다.");
            return;
        }

        // 2-D-2. 선택한 계좌 종류에 맞는 멤버변수를 입력받고 인스턴스 생성
        if (choice == 1) {
            System.out.print("기본이율(%) : ");
            int interRate = BankingSystemMain.scanner.nextInt();
            
            // 다형성 활용: 부모 배열에 자식 인스턴스 저장
            accountArray[accountCount++] = new NormalAccount(accNum, name, balance, interRate);
            System.out.println("보통예금계좌 개설이 완료되었습니다.\n");
            
        } else if (choice == 2) {
            System.out.print("기본이율(%) : ");
            int interRate = BankingSystemMain.scanner.nextInt();
            System.out.print("신용등급(A/B/C) : ");
            String creditLevel = BankingSystemMain.scanner.next();
            
            // 고신용계좌 생성 및 저장 (생성자 내부에서 신용이율 계산 자동화)
            accountArray[accountCount++] = new HighCreditAccount(accNum, name, balance, interRate, creditLevel);
            System.out.println("고신용계좌 개설이 완료되었습니다.\n");
        }
    }

    // 입 금 (수정 없음 - 다형성에 의해 오버라이딩된 자식의 deposit()이 자동 호출됨)
    public void depositMoney() {
        System.out.println("***입   금***");
        System.out.println("계좌번호와 입금할 금액을 입력하세요");
        System.out.print("계좌번호:");
        String accNum = BankingSystemMain.scanner.next();
        System.out.print("입금액:");
        int money = BankingSystemMain.scanner.nextInt();

        for (int i = 0; i < accountCount; i++) {
            if (accountArray[i].getAccountNumber().equals(accNum)) {
                // 자식 클래스들의 오버라이딩된 이자 가산 deposit()이 호출됩니다.
                accountArray[i].deposit(money);
                System.out.println("입금이 완료되었습니다.\n");
                return;
            }
        }
        System.out.println("입금이 완료되었습니다. (해당 계좌 없음)\n");
    }

    // 출 금 (동일)
    public void withdrawMoney() {
        System.out.println("***출   금***");
        System.out.println("계좌번호와 출금할 금액을 입력하세요");
        System.out.print("계좌번호:");
        String accNum = BankingSystemMain.scanner.next();
        System.out.print("출금액:");
        int money = BankingSystemMain.scanner.nextInt();

        for (int i = 0; i < accountCount; i++) {
            if (accountArray[i].getAccountNumber().equals(accNum)) {
                accountArray[i].withdraw(money);
                System.out.println("출금이 완료되었습니다.\n");
                return;
            }
        }
        System.out.println("출금이 완료되었습니다. (해당 계좌 없음)\n");
    }

    // 전체계좌정보출력 (수정 없음 - 동적 바인딩으로 각 계좌 타입에 맞는 정보출력)
    public void showAccInfo() {
        System.out.println("***계좌정보출력***");
        for (int i = 0; i < accountCount; i++) {
            accountArray[i].showAccountInfo();
        }
        System.out.println("전체계좌정보 출력이 완료되었습니다.\n");
    }
}