package Lecture;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class ManageCourseDetailsController {
    @FXML private Button StudentMarksButton;
    @FXML private Label courseNameLabel;
    @FXML private Button enrolledStudentButton;
    @FXML private Button LectureMaterialButton;
    private String label1;


    public void setCourseLabel(String clabel){
            label1 = clabel;
            courseNameLabel.setText(label1);
        }



    public  void  handleStudentMarks(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/StudentMarks.fxml"));
        try {
            Parent root = loader.load();

            StudentMarksController controller = loader.getController();
            controller.setCourseLabel(label1);

            Stage stage = (Stage) StudentMarksButton.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public  void handleEnrolledStudentButton(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/EnrolledStudentsView.fxml"));
        try {
            Parent root = loader.load();

            EnrolledStudentsViewController controller = loader.getController();
            controller.setCourseLabel(courseNameLabel.getText());

            Stage stage = (Stage) StudentMarksButton.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public  void handleLectureMaterials(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/uploadLectureMaterials.fxml"));
        try {
            Parent root = loader.load();

            uploadLectureMaterialController controller = loader.getController();
            controller.setCourseLabel(courseNameLabel.getText());

            Stage stage = (Stage) StudentMarksButton.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void handleHome(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/main.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
