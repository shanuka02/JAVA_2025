import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class lectureDrive extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(lectureDrive.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),700,600);
        //stage.setTitle("hello");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
