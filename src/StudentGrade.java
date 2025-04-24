import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentGrade {



    @FXML
    private TextField DatabaseGrade;

    @FXML
    private TextField NetworkingGrade;

    @FXML
    private TextField OperatingSystemsGrade;

    @FXML
    private TextField ProgrammingGrade;

    @FXML
    private TextField WebDesignGrade;

    @FXML
    private TextField SGPAGrade;

    @FXML
    private TextField CGPAGrade;

    @FXML
    private void handleBackButton(ActionEvent event) {

        TechmisApp back = new TechmisApp();
        back.start(TechmisApp.getPrimaryStage());


    }



    private String calculateGrade(double finalMark) {
        if (finalMark >= 90) return "A+";
        else if (finalMark >= 85) return "A";
        else if (finalMark >= 80) return "A-";
        else if (finalMark >= 75) return "B+";
        else if (finalMark >= 70) return "B";
        else if (finalMark >= 65) return "B-";
        else if (finalMark >= 60) return "C+";
        else if (finalMark >= 55) return "C";
        else if (finalMark >= 50) return "C-";
        else if (finalMark >= 45) return "D+";
        else if (finalMark >= 40) return "D";
        else return "F";
    }


    @FXML
    public void loadStudentGrades() {
        String quary = "SELECT * FROM mark WHERE studentId = 1";

        try {
            Connection connection  = DBConnection.getConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(quary);

            while (rs.next()) {
                String courseCode = rs.getString("courseCode");

                double assessments = rs.getInt("assesment_01") + rs.getInt("assesment_02") + rs.getInt("assesment_03");
                double quizzes = rs.getInt("quiz_1") + rs.getInt("quiz_2") + rs.getInt("quiz_3");
                double mid = rs.getInt("midExam");
                double finalE = rs.getInt("finalExam");

                double finalMark = (assessments * 0.2) + (quizzes * 0.2) + (mid * 0.2) + (finalE * 0.4);
                String grade = calculateGrade(finalMark);
                System.out.println(finalMark);

                switch (courseCode) {
                    case "ICT001" -> ProgrammingGrade.setText(grade);
                    case "ICT002" -> NetworkingGrade.setText(grade);
                    case "ICT003" -> DatabaseGrade.setText(grade);
                    case "ICT004" -> WebDesignGrade.setText(grade);
                    case "ICT005" -> OperatingSystemsGrade.setText(grade);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


}