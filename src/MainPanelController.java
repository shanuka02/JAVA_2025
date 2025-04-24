import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPanelController{
    @FXML
    private BorderPane mainPanel1;

    @FXML
    private Button logbutton;



    public void handleLogClick(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\loginform.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("Error: "+e.getMessage());
        }



    }
}
