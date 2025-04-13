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

    private NavigationManager navigationManager;

    public void setNavigationManager(NavigationManager navManager) {
        this.navigationManager = navManager;
    }




    public void handleLogClick(ActionEvent actionEvent) {
      //  navigationManager.loadInterface("loginform.fxml");
        // direct to admin interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginform.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
