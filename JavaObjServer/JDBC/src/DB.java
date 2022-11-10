import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
    static final String DB_URL = "jdbc:mysql://localhost:3306/network_db";
    static final String USER = "root";
    static final String PASSWORD = "1234";
    static final String QUERY = "SELECT * FROM new_table";
    public static void main(String[] args) throws Exception {
        // Dirver load
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Open a connection
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
        
                // Extract data from result set
                while (rs.next()) {
                    // Retrieve by column name
                    System.out.println("name: " + rs.getString(0));
                }
                System.out.println("mysql db 연결 성공");
        } catch(SQLException error) {
            System.out.println(error);
            System.out.println("DB 접속 오류");
        }
    }
}