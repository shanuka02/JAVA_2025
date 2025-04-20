import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TechnicalMain {
    private static final Stage stage = new Stage();

    public void Go() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/TechnicalOfficer.fxml"));
        try{
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Start.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.2 " + e.getMessage());
        }
    }

    public void attendanceBtn() throws IOException {
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

    public void attendanceAdd() throws IOException {
        stage.close();
        new TechnicalMain().accessToAttendance();
//        System.out.println(event.getClass().getSimpleName());
    }

    public void attendanceView() throws IOException {
        stage.close();
        System.out.println("Attendance Viewed");
    }

    public void backToPage() throws Exception {
        new Start().start(Start.getPrimaryStage());
    }

    private void accessToAttendance(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/AttendanceAdd.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Start.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.4 " + e.getMessage());
        }
    }
}
