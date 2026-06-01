package banking6.threeby3;

public interface ICustomDefine {
    // 메뉴 상수 (5번 삭제 추가, 6번 종료로 변경)
    int MAKE = 1;
    int DEPOSIT = 2;
    int WITHDRAW = 3;
    int INQUIRE = 4;
    int DELETE = 5; // 추가됨
    int EXIT = 6;   // 변경됨

    // 신용등급별 추가 이율 상수
    int A_GRADE_RATE = 7;
    int B_GRADE_RATE = 4;
    int C_GRADE_RATE = 2;
}