import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainPanelController {
    @FXML
    private BorderPane mainPanel1;

    @FXML
    private Button logbutton;

    private NavigationManager navigationManager;


    // this method call automatically by the javafx framework after the FXML fields have been injected nad initialized'
    public void initialize(){
        Stage stage = (Stage)  mainPanel1.getScene().getWindow();
        //create the navigation manager object
        navigationManager = new NavigationManager(stage);
    }

    public void handleLogClick(ActionEvent actionEvent) {
        navigationManager.loadInterface("loginController.fxml");   //direct to admin interface


    }
}
