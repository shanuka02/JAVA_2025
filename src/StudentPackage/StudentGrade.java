package StudentPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentGrade {

    @FXML
    private TextField ProgrammingGrade;

    @FXML
    private TextField NetworkingGrade;

    @FXML
    private TextField DatabaseGrade;

    @FXML
    private TextField WebDesignGrade;

    @FXML
    private TextField OperatingSystemsGrade;

    @FXML
    private TextField SGPAGrade;

    @FXML
    private TextField CGPAGrade;

    @FXML
    private void handleBackButton(ActionEvent event) {
        TechmisApp back = new TechmisApp();
        back.start(TechmisApp.getPrimaryStage());
    }

    @FXML
    public void loadStudentGrades() {
        String studentId = "TG1301";
        String query = "SELECT * FROM finalmarks WHERE studentId = '" + studentId + "'";

        try {
            Connection connection = DBConnection.getConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String courseCode = rs.getString("courseId");
                String grade = rs.getString("grade");

                switch (courseCode) {
                    case "ICT2113":
                        ProgrammingGrade.setText(grade);
                        break;
                    case "ICT2122":
                        NetworkingGrade.setText(grade);
                        break;
                    case "ICT2133":
                        DatabaseGrade.setText(grade);
                        break;
                    case "ICT2142":
                        WebDesignGrade.setText(grade);
                        break;
                    case "ICT2152":
                        OperatingSystemsGrade.setText(grade);
                        break;
                    default:
                        break;
                }
            }

            String sgpaQuery = "SELECT sgpa, cgpa FROM finalmarks WHERE studentId = '" + studentId + "' LIMIT 1";
            ResultSet sgpaRs = statement.executeQuery(sgpaQuery);
            if (sgpaRs.next()) {
                double sgpa = sgpaRs.getDouble("sgpa");
                double cgpa = sgpaRs.getDouble("cgpa");
                SGPAGrade.setText(String.format("%.2f", sgpa));
                CGPAGrade.setText(String.format("%.2f", cgpa));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
