package banking6.threeby3;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class PuzzleGame {
    // 퍼즐 보드 (9번은 빈칸 'x'를 의미)
    private int[][] board = new int[3][3];
    // 정답 보드 비교용
    private final int[][] ANSWER = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9} // 9가 'x'
    };
    
    // 빈칸(9)의 현재 좌표
    private int blankRow;
    private int blankCol;
    
    private Scanner scanner = new Scanner(System.in);

    // 게임 시작 메서드 (외부 계좌관리 메뉴에서 호출할 곳)
    public void start() {
        while (true) {
            initBoard();
            shuffle(100); // 테스트 시 3으로 변경하여 확인 가능합니다.
            
            System.out.println("\n====== 3x3 숫자 퍼즐 게임을 시작합니다 ======");
            
            while (!isGameClear()) {
                printBoard();
                System.out.print("이동 (a:좌, d:우, w:위, s:아래) -> ");
                String input = scanner.nextLine().trim().toLowerCase();
                
                if (input.isEmpty()) continue;
                
                char move = input.charAt(0);
                moveBlock(move);
            }
            
            // 게임 클리어 시
            printBoard();
            System.out.println("\n🎉 축하합니다! 퍼즐을 모두 맞추셨습니다! 🎉");
            
            System.out.print("게임이 완료되었습니다. 재시작하시겠습니까? (y/n) : ");
            String restart = scanner.nextLine().trim().toLowerCase();
            if (!restart.equals("y")) {
                System.out.println("계좌관리 메인 메뉴로 돌아갑니다.");
                break;
            }
        }
    }

    // 1. 초기 정답 상태로 보드 세팅
    private void initBoard() {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = count++;
            }
        }
        // 빈칸 'x'의 초기 위치는 우측 하단 (2, 2)
        blankRow = 2;
        blankCol = 2;
    }

    // 2. 풀 수 있는 퍼즐을 만들기 위한 셔플 (완성된 상태에서 랜덤 이동)
    private void shuffle(int count) {
        Random random = new Random();
        char[] moves = {'a', 'd', 'w', 's'};
        int shuffledCount = 0;
        
        while (shuffledCount < count) {
            char randomMove = moves[random.nextInt(4)];
            // 셔플할 때는 이동 불가 메시지를 띄우지 않고, 실제 이동 성공했을 때만 카운트 증가
            if (executeMove(randomMove, true)) {
                shuffledCount++;
            }
        }
    }

    // 3. 화면에 현재 퍼즐 상태 출력
    private void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 9) {
                    System.out.print("x "); // 9 대신 x 출력
                } else {
                    System.out.print(board[i][j] + " ");
                }
                System.out.print("| ");
            }
            System.out.println("\n-------------");
        }
    }

    // 4. 사용자 입력에 따른 블록 이동 처리 및 메시지 출력
    private void moveBlock(char move) {
        boolean success = executeMove(move, false);
        if (!success) {
            System.out.println("⚠️ 이동할 수 없는 방향입니다! 다시 입력해주세요.");
        }
    }

    /**
     * 실제 타일 이동 로직
     * @param move 이동 방향 키
     * @param isShuffle 셔플 중인지 여부 (셔플 중에는 에러 메시지 생략용)
     * @return 이동 성공 여부
     */
    private boolean executeMove(char move, boolean isShuffle) {
        // 'x'가 움직이는 방향은 사용자가 누른 키의 '반대' 타일을 가져오는 개념입니다.
        // ex) 'a'(좌로 이동) -> 'x' 기준 우측에 있는 타일이 좌측으로 와야 하므로, x의 열(Col) 좌표가 +1 되어야 함.
        int targetRow = blankRow;
        int targetCol = blankCol;

        switch (move) {
            case 'a': // 좌로 이동 (x는 우로 이동)
                targetCol++;
                break;
            case 'd': // 우로 이동 (x는 좌로 이동)
                targetCol--;
                break;
            case 'w': // 위로 이동 (x는 아래로 이동)
                targetRow++;
                break;
            case 's': // 아래로 이동 (x는 위로 이동)
                targetRow--;
                break;
            default:
                if (!isShuffle) System.out.println("알 수 없는 키입니다. (a, s, w, d만 사용 가능)");
                return false;
        }

        // 배열 범위를 벗어나는지 체크 (이동 불가 상태 경계선 예외 처리)
        if (targetRow < 0 || targetRow > 2 || targetCol < 0 || targetCol > 2) {
            return false;
        }

        // 스왑(Swap) 진행
        int temp = board[blankRow][blankCol];
        board[blankRow][blankCol] = board[targetRow][targetCol];
        board[targetRow][targetCol] = temp;

        // 빈칸 위치 갱신
        blankRow = targetRow;
        blankCol = targetCol;

        return true;
    }

    // 5. 정답 배열과 현재 배열이 일치하는지 확인
    private boolean isGameClear() {
        return Arrays.deepEquals(board, ANSWER);
    }
}