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

    @FXML
    private void handleMangeCourseButton(javafx.event.ActionEvent actionEvent) throws IOException {
        //load the new fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manageCourses.fxml"));
        Parent root = loader.load();

        //create new stage (window)
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();







    }
}

