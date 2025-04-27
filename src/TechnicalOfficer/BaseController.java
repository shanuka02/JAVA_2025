package TechnicalOfficer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseController {

    @FXML
    private Label dep;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    public void handleLogout(){
        Session.clearSession();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/loginform.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void handleEditprofile(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLfiles/EditProfile.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void loadProfileData() {

    }

}
