package StudentPackage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WebDesignView {

    @FXML
    private ProgressBar PresentageBar;


    @FXML
    private Label presentage;



    @FXML
    void loadAttendance(ActionEvent event) {
        loadTheoryAttendance();

    }

    private void loadTheoryAttendance() {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT " +
                    "SUM(CASE WHEN Status_ = 'PRESENT' THEN 1 ELSE 0 END) AS presentCount, " +
                    "COUNT(*) AS totalCount " +
                    "FROM attendance " +
                    "WHERE Att_stu_id = 'TG1302' AND Att_cou_id ='ICT2152' AND Lec_type = 'Theory'";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int presentCount = rs.getInt("presentCount");
                int totalCount = rs.getInt("totalCount");

                if (totalCount > 0) {
                    double attendancePercentage = (double) presentCount / totalCount;
                    PresentageBar.setProgress(attendancePercentage);
                    presentage.setText(String.format("%.0f%%", attendancePercentage * 100));
                } else {
                    PresentageBar.setProgress(0);
                    presentage.setText("0%");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void backtopage() throws IOException {
        new ProfileController().handleAttendance();
    }
}
