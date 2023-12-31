import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLJava {
    // 데이터베이스 연결 메서드
    public static Connection makeConnection() {
        String url = "jdbc:mysql://localhost:3306/book_db";
        String id = "root";
        String password = "";
        Connection con = null;

        try {
            // JDBC 드라이버를 로드합니다.
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("드라이버 적재 성공");

            // 데이터베이스에 연결합니다.
            con = DriverManager.getConnection(url, id, password);
            System.out.println("데이터베이스 연결 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버를 찾을 수 없습니다.");
        } catch (SQLException e) {
            System.out.println("연결에 실패하였습니다.");
        }

        return con;
    }

    // 책 추가 메서드
    private static void addBook(String title, String publisher, String year, int price) {
        Connection con = makeConnection(); // 데이터베이스 연결

        try {
            Statement stmt = con.createStatement();
            String query = "INSERT INTO books(title, publisher, year, price) VALUES " +
                           "('" + title + "', '" + publisher + "', '" + year + "', '" + price + "')";

            System.out.println(query);
            int i = stmt.executeUpdate(query); // SQL 쿼리 실행

            if (i == 1)
                System.out.println("레코드 추가 성공");
            else
                System.out.println("레코드 추가 실패");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } finally {
            try {
                if (con != null) {
                    con.close(); // 연결을 닫습니다.
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        // 메인 메서드에서 책 추가를 호출합니다.
        addBook("Artificial Intelligence", "Addison Wesley", "2002", 35000);
    }
}
