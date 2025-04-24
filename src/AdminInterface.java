import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdminInterface extends  BaseController {


    @FXML
    private Label Email;

    @FXML
    private ImageView image;

    @FXML
    private Label dep;

    @FXML
    private Label name;
    @FXML
    private Button B1;
    @FXML
    private Button Notice;

    @FXML
    private Button Editbutton;

    mySqlCon connection;

    @FXML
    public void  initialize(){

        loadProfileData();
    }

    @FXML
    void handleEditProfile(ActionEvent event) {
        handleEditprofile();
    }

    @FXML
    public void handleManageUser(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageUser.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }


    @FXML
    void handleManageNotice(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageNotice.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    @FXML
    public void handleManageTimetable(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageTimetable.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    @FXML
    public void handleManageCourse(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageCourse.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    @Override
    public void loadProfileData(){
        connection = new mySqlCon();
        Connection con = connection.con();


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
                Email.setText(rs.getString(3));

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
