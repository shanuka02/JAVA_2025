import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class controller {
    public BorderPane firstBorderPane;
    public Circle profilePic;
    public ImageView editProfileIcon;
    public ImageView noticesIcon;
    public ImageView manageCourseIcon;
    public ImageView viewStudentIcon;
    public ImageView StudentMarksIcon;
    public ImageView studentAttendenceIcon;
    public ImageView studentMedicalIcon;
    public ImageView studentEligibilityIcon;

    @FXML
    private Button noticeButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button manageCourseButton;

    @FXML
    private Button studentEligibilityButton;

    @FXML
    private Button studentMarksButton;

    @FXML
    private Button studentMedicalButton;

    @FXML
    private Button studentAttendenceButton;

    @FXML
    private Button viewStudentButton;

    @FXML
    private Label lectureNameLabel;

    @FXML
    private Label departmentNameLabel;

    @FXML private TextField CourseName1;
    @FXML private  TextField CourseName2;
    @FXML private  TextField CourseName3;
    @FXML private  TextField CourseName4;
    @FXML private TextField CourseName5;

    private TextField[] fields ;


    @FXML
    public void initialize() {
        fields = new TextField[]{CourseName1, CourseName2, CourseName3, CourseName4, CourseName5};
        loadCourseNames();
    }

    private void loadCourseNames() {
        try{

            Connection conn = dbConnection.getConnection();
            String query = "SELECT courseName FROM courseunit ";
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            TextField[] fields = {CourseName1, CourseName2, CourseName3, CourseName4, CourseName5};

            int index = 0;
            while (rs.next() && index < fields.length) {
                if (fields[index] != null) {
                    fields[index].setText(rs.getString("courseName"));
                }
                index++;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Set default values if database fails
            for (int i = 0; i < fields.length; i++) {
                if (fields[i] != null) {
                    fields[i].setText("Course " + (i + 1));
                }
            }
        }
    }

    @FXML
    private void handleMangeCourseButton(javafx.event.ActionEvent actionEvent) throws IOException {
        //load the new fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manageCourses.fxml"));
        Parent root = loader.load();

        // Get the controller of the new window
        controller manageCoursesController = loader.getController();

        //create new stage (window)
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();



















    }


}

