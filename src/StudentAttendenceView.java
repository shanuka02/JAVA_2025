import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class StudentAttendenceView {
    @FXML
    private void handleBackButton(ActionEvent event) {
        TechmisApp back = new TechmisApp();
        back.start(TechmisApp.getPrimaryStage());
    }




    public void ViewProgram() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/ProgramView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);



    }
}
