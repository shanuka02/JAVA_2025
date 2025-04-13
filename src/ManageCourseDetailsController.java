import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class ManageCourseDetailsController {
    @FXML private Label courseNameLabel;
        public void setCourseLabel(String clabel){
            courseNameLabel.setText(clabel);
        }
}
