import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/lms";
        String user = "root";
        String password = "1234";

        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }
}
