import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class ManageCourse {

    @FXML
    private Button AddCourse;

    @FXML
    private Button DeleteCourse;

    @FXML
    private Button SearchCourse;

    @FXML
    private Button UpdateCourse;

    @FXML
    void HandleAddcourse(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\createCourse.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }

    }

    @FXML
    void HandleUpdateCourse(ActionEvent event) {

    }

    @FXML
    void handleDeletecourse(ActionEvent event) {

    }

    @FXML
    void handleSearchCourse(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\searchCourse.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }

    }

    public void HandleHomeButton(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\AdminInterface.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
