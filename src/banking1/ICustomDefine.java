package banking1;

public interface ICustomDefine {

	int MAKE = 1;
    int DEPOSIT = 2;
    int WITHDRAW = 3;
    int INQUIRE = 4;
    int EXIT = 5;
    
    
 // 이자율 : 고객의 신용등급을 A, B, C로 나눠서 7%, 4%, 2%로 지정
    int A_GRADE_RATE = 7;
    int B_GRADE_RATE = 4;
    int C_GRADE_RATE = 2;
}
