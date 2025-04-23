import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Integer.parseInt;

public class AttendanceMedical {
    public void setAttendance(){
        String quary = "SELECT * FROM Medical WHERE Status_ = 'approved'";
        int name;
        Connection connection = DBConnection.getConnection();
        Statement statement = null;
        ResultSet results = null;
        try {
            assert connection != null;
            statement = connection.createStatement();
            results = statement.executeQuery(quary);
            while (results.next()) {
                System.out.println( results.getString(1) + " " +results.getString(2) + " " + results.getString(3) + " " + results.getString(4));
                name = parseInt(results.getString(1));
            }
        } catch (SQLException e) {

        }
    }

    public void backToPage() throws IOException {
        new TechnicalMain().Go();
    }
}
