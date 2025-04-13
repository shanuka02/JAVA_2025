import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationManager {

    private Stage stage;



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadInterface(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            if (stage != null) {
                stage.setScene(scene);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
           ;e.getMessage();
        }
    }

}
