import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class NavigationManager {

    private Stage stage;

 NavigationManager(Stage stage){
        this.stage = stage;
    }

    public void loadInterface(String fxmlFile){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load(), 320, 240);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"fail to load Interface","Error",JOptionPane.ERROR_MESSAGE);
        }

    }
}
