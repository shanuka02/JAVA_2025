import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class  WebDesignView {
    public void backtopage() throws IOException {
        new ProfileController().handleAttendance();
    }

    @FXML
    private Label presentage;

    @FXML
    private ProgressBar PresentageBar;


    public void Viewpresentageict01(){

        String quary = "SELECT * FROM attendance;";
        try {
            Connection connection  = DBConnection.getConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(quary);

            while (rs.next()) {
                String courseCode = rs.getString("courseCode");


            }
        }catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
