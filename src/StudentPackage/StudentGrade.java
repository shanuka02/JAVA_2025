package StudentPackage;

import Admin.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

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

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/MainView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        ApplicationDrive.getPrimaryStage().setScene(scene);
    }

    @FXML
    public void loadStudentGrades() {
        // Query to fetch grades for the logged-in student
        String query = "SELECT * FROM finalmarks WHERE studentId = ?";

        try (Connection connection = mySqlCon.getConnection()) {
            if (connection != null) {

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, Session.getUserId());
                ResultSet rs = ps.executeQuery();


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


                String sgpaQuery = "SELECT sgpa, cgpa FROM finalmarks WHERE studentId = ? LIMIT 1";
                PreparedStatement sgpaPs = connection.prepareStatement(sgpaQuery);
                sgpaPs.setString(1, Session.getUserId());
                ResultSet sgpaRs = sgpaPs.executeQuery();

                if (sgpaRs.next()) {
                    double sgpa = sgpaRs.getDouble("sgpa");
                    double cgpa = sgpaRs.getDouble("cgpa");

                    SGPAGrade.setText(String.format("%.2f", sgpa));
                    CGPAGrade.setText(String.format("%.2f", cgpa));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
