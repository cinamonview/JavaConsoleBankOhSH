package banking6; // 기존 뱅킹6 패키지

import java.util.Scanner;
// 💡 [위치 1] 최상단 import 구문에 퍼즐게임 클래스를 불러옵니다.
import banking6.threeby3.PuzzleGame; 

public class MainApp { // 클래스명은 본인 메인파일명과 동일해야 합니다.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            // 💡 [위치 2] 콘솔에 출력되는 메뉴 안내 텍스트에 퍼즐 게임을 추가합니다.
            System.out.println("------- Menu -------");
            System.out.println("1. 계좌개설");
            System.out.println("2. 입 금");
            System.out.println("3. 출 금");
            System.out.println("4. 전체출력");
            System.out.println("5. 3x3 숫자퍼즐게임"); // <-- 새로 추가된 메뉴 안내
            System.out.println("6. 프로그램종료");
            System.out.print("선택: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // 입력 버퍼 비우기 (오류 방지)
            
            // 💡 [위치 3] switch문 내부에 case 5를 새로 만들고 퍼즐게임을 실행합니다.
            switch (choice) {
                case 1:
                    // 기존 계좌개설 메서드 호출...
                    break;
                case 2:
                    // 기존 입금 메서드 호출...
                    break;
                case 3:
                    // 기존 출금 메서드 호출...
                    break;
                case 4:
                    // 기존 전체출력 메서드 호출...
                    break;
                    
                // ==========================================
                // 🔥 여기에 정확히 넣어주시면 됩니다!
                // ==========================================
                case 5: 
                    System.out.println("숫자 퍼즐 게임으로 이동합니다...");
                    PuzzleGame game = new PuzzleGame(); // 퍼즐 객체 생성
                    game.start();                       // 퍼즐 게임 시작 (게임이 끝나야 다음 줄로 넘어감)
                    break; 
                // ==========================================
                    
                case 6: // 기존 종료 메뉴 (원래 case 5였다면 번호를 6으로 수정해야 합니다)
                    System.out.println("프로그램을 종료합니다.");
                    return; // 또는 System.exit(0);
                    
                default:
                    System.out.println("잘못 선택하셨습니다. 1~6 사이의 숫자를 입력해주세요.");
            }
        }
    }
}