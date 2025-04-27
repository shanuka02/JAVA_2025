package Lecture;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationDrive extends Application {

        private static Stage PrimaryStage;

        public static void main(String[] args ) {
            //start the application by calling the luanch()
            launch(args);
        }

        public static Stage getPrimaryStage(){
            return PrimaryStage;
        }

        @Override
        public void start(Stage stage) throws IOException {
            PrimaryStage = stage;
            stage.setTitle("lms");

            //creates a new FXMLLoader object that will load your FXML file containing the interface definition.
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/MainPanel.fxml"));
            //loads the FX  ML file and creates the Ui component hierarchy
            Parent root = loader.load();

            //get the controller object(file)that was specified in the FXML file.
            //Lecture.MainPanelController controller = loader.getController();
            //pass navigation manager instance to the controller , so it can use it to navigate between scenes
            // controller.setNavigationManager(navigationManager);

            //create a new Scene object with my loaded UI components
            Scene scene = new Scene(root);
            //sets this scene as the current scene for your application window
            stage.setScene(scene);
            //make your application visible to users
            stage.show();
        }
    }

