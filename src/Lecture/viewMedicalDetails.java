package Lecture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import java.io.IOException;

public class viewMedicalDetails {

    @FXML
    private TableColumn<?, ?> MediId;

    @FXML
    private Label coIdMedi;

    @FXML
    private Button homebutton;

    @FXML
    private TableColumn<?, ?> reason;

    @FXML
    private TableColumn<?, ?> reqDate;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private TableColumn<?, ?> stuId;

    @FXML
    private TableColumn<?, ?> subDate;




public void setCourseLabel(String updateCoLabel) {
        // Set the label text with the selected course name
        coIdMedi.setText(updateCoLabel);
    }

    public void handleHome(ActionEvent event ) {
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
