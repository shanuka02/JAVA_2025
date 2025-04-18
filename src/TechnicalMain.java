import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TechnicalMain {
    private static Stage stage = new Stage();

    public void Go(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/TechnicalOfficer.fxml"));
        try{
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Start.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.2 " + e.getMessage());
        }
    }

    public void attendanceBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/Selection1.fxml"));
        try{
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Attendance");
            stage.setResizable(false);
            stage.setX(500);
            stage.setY(300);
            stage.show();

        }catch (Exception e){
            System.out.println("Error_code: x0000e1.3 " + e.getMessage());
        }
    }

    public void attendanceAdd(ActionEvent event) throws IOException {
        stage.close();
        System.out.println("Attendance Added");
    }

    public void attendanceView(ActionEvent event) throws IOException {
        stage.close();
        System.out.println("Attendance Viewed");
    }
}
