import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class ApplicationDrive extends Application {

    private static Stage PrimaryStage;

    public static void main(String[] args) {
        //start the application by calling the lunch()
        launch(args);
    }

    public static Stage getPrimaryStage(){
        return PrimaryStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        PrimaryStage = stage;
        stage.setTitle("lms");
        stage.setWidth(600);
        stage.setHeight(600);

        //creates a new FXMLLoader object that will load  FXML file containing the interface definition.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
        //loads the FXML file and creates the Ui component hierarchy
        Parent root = loader.load();
        Scene scene = new Scene(root);

        //sets this scene as the current scene for application window
        stage.setScene(scene);
        //make  application visible to users
        stage.show();
    }
}
