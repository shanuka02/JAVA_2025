import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class TechnicalMain {
    public void Go(ActionEvent event) throws IOException {
//        System.out.println("Technical Officer");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/TechnicalOfficer.fxml"));
        try{
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Start.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.2 " + e.getMessage());
        }
    }
}
