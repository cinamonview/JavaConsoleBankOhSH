package banking.jdbc;

public interface MenuConnect {
    // 오라클 DB 접속 상수
    String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
    String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    String DB_USER = "education"; 
    String DB_PW = "1234";
}