package TechnicalOfficer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TechnicalMain extends BaseController{
    private static final Stage stage = new Stage();

    public void Go() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("technicalResources/fxml/TechnicalOfficer.fxml"));
        try{
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.2 " + e.getMessage());
        }
    }

    public void attendanceBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("technicalResource/fxml/Selection1.fxml"));
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

    public void medicalBtn(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("technicalResource/fxml/Selection2.fxml"));
        try{
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Medical");
            stage.setResizable(false);
            stage.setX(500);
            stage.setY(300);
            stage.show();

        }catch (Exception e){
            System.out.println("Error_code: x0000e1.3 " + e.getMessage());
        }
    }

    public void backToPage() throws Exception {
//        new Start().start(Start.getPrimaryStage());
    }

    public void accessToAddAttendance(){
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("technicalResource/fxml/TechnicalOfficer.AttendanceAdd.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.4 " + e.getMessage());
        }
    }

    public void accessToViewAttendance(){
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("technicalResource/fxml/TechnicalOfficer.AttendanceView.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.4 " + e.getMessage());
        }
    }

    public void accessToAttendanceMedical(){
        stage.close();
        new AttendanceMedical().setAttendance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("technicalResource/fxml/TechnicalOfficer.AttendanceMedical.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.4 " + e.getMessage());
        }
    }

    public void accessToExamMedical(){
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("technicalResource/fxml/ExamMedical.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){}
    }

    @Override
    public void loadProfileData() {

    }

    public void edit(ActionEvent event) {
        handleEditprofile();
    }
}
