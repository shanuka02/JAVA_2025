package TechnicalOfficer;

import Admin.mySqlCon;
import Admin.BaseController;
import  Admin.ApplicationDrive;
import Admin.Session;
import Admin.mySqlCon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TechnicalMain extends BaseController{

    @FXML
    private Label dep;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    private static final Stage stage = new Stage();


    @FXML
    public void initialize(){
        loadProfileData();
    }


    void mainLoader() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/TechnicalOfficer.fxml"));
        Parent root = null;
        try{
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.2 " + e.getMessage());
        }
    }

    @FXML
    public void attendanceBtn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Attendance View and Add");
        alert.setContentText("Choose your option.");

        ButtonType buttonYes = new ButtonType("View");
        ButtonType buttonNo = new ButtonType("Add");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == buttonYes) {
                accessToViewAttendance();
            } else if (result.get() == buttonNo) {
                accessToAddAttendance();
            }
        }
    }

    @FXML
    public void medicalBtn(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Medical Add and View");
        alert.setContentText("Choose your option.");

        ButtonType buttonYes = new ButtonType("Add");
        ButtonType buttonNo = new ButtonType("View");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == buttonYes) {
                accessToAttendanceMedical();
            } else if (result.get() == buttonNo) {
                accessToAttendanceMedical();
            }
        }
    }


    public void backToPage() {
//        new Start().start(Start.getPrimaryStage());
        handleLogout();
    }

    @FXML
    public void accessToAddAttendance(){
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/AttendanceAdd.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.4 " + e.getMessage());
        }
    }

    @FXML
    public void accessToViewAttendance(){
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/AttendanceView.fxml"));
        Parent root = null;
        try {
             root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.4 " + e.getMessage());
        }
    }

    @FXML
    public void accessToAttendanceMedical(){
        stage.close();
        new AttendanceMedical().setAttendance();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/AttendanceMedical.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.4 " + e.getMessage());
        }
    }

    @FXML
    public void accessToExamMedical(){
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/ExamMedical.fxml"));
        Parent root = null;
        try {
             root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.4 " + e.getMessage());
        }
    }


@FXML
    public void edit() {
        handleEditprofile();
    }

    @FXML
    public void noticeBtn(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/Notice.fxml"));
        Parent root = null;
        try{
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.5 " + e.getMessage());
        }
    }

@FXML
    public void loadProfileData(){

        Connection con = mySqlCon.getConnection();


        String userID = Session.getUserId();

        String query = "SELECT * FROM  userAccount WHERE user_id = ?";
        try {

            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,Session.getUserId());
            ResultSet rs = pstm.executeQuery();


            if (rs.next()) {
/*
                JOptionPane.showMessageDialog(null,"No notice Found");
*/

//if we didnt put condition null pointer execption generate, becouse db data is null
                name.setText(rs.getString(2));
                //Email.setText(rs.getString(3));

                dep.setText(rs.getString(7));

                String profilePicpath =  rs.getString(9);

                if(!profilePicpath.isEmpty()){
                    File file = new File(profilePicpath);

                    if(file.exists()){
                        try {
                            Image profileImage = new Image(file.toURL().toString());
                            if(image != null)image.setImage(profileImage);
                        } catch (MalformedURLException e) {
                            System.out.println("Error: "+e.getMessage());
                        }
                    }
                }
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

        }

    }
}
