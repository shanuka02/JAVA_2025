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



    }


}