import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.IOException;

public class ManageCourseDetailsController {
    @FXML private Button StudentMarksButton;
    @FXML private Label courseNameLabel;

        public void setCourseLabel(String clabel){

            courseNameLabel.setText(clabel);
        }

    public  void handleStudentMarks(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StudentMarks.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = (Stage) StudentMarksButton.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
