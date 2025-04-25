import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
    @Override
    public void start(Stage stage) throws Exception {
        try {
            primaryStage = stage;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/technicalResources/fxml/StartUp.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setTitle("Login");
            stage.show();
        }catch (IOException e) {
            System.out.println("Error_code: x0000e1.1 ," + e.getMessage());
        }
    }
}
