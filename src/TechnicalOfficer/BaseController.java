package TechnicalOfficer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public abstract class BaseController
{
    public void handleLogout(){
        Session.clearSession();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\loginform.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void handleEditprofile(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\EditProfile.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public abstract  void loadProfileData();


}
