import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class  dbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fot";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
