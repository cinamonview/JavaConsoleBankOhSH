package banking3;

//개발자가 직접 정의한 예외처리 클래스
public class MenuSelectException extends Exception {
 public MenuSelectException(String message) {
     super(message);
 }
}