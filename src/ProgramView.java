import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.io.IOException;

public class ProgramView {
    public void backtopage() throws IOException {
        new ProfileController().handleAttendance();
    }

    @FXML
    private Label presentage;

    @FXML
    private ProgressBar PresentageBar;







}
