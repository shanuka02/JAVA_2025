import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class loginController{

    @FXML
    private BorderPane mainBorder;

    @FXML
    private AnchorPane leftAnchor;

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle11;

    @FXML
    private Label lable1;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userField;

    private NavigationManager navigationManager;


  /*  // this method call automatically by the javafx framework after the FXML fields have been injected nad initialized'
    public void initialize(){
        Stage stage = (Stage) mainBorder.getScene().getWindow();
        //create the navigation manager object
        navigationManager = new NavigationManager(stage);
    }
*/
    public void setStage(Stage stage){
        this.navigationManager =new NavigationManager(stage);
    }

    public void handleLoginButtonClick(ActionEvent actionEvent) {



        mySqlCon conobj = new mySqlCon();
        Connection con  = conobj.con();




        String userName=  userField.getText();
        String password =  passwordField.getText();


        String query = "SELECT * FROM userAccount WHERE user_name = ? AND password = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, userName);
            pstmt.setString(2, password);

            ResultSet  rs = pstmt.executeQuery();
            if (rs.next()) {
                // if roll is admin   direct to admininterface.java
                // if roll is undergraduate   direct to undergraduateinterface.java
                //JOptionPane.showMessageDialog(null, "Login successful!");

                String role =  rs.getString("role");
                if("admin".equalsIgnoreCase(role)){

                    //call the method in NavitionManager to lad interfaces
                    navigationManager.loadInterface("AdminInterface.fxml");   //direct to admin interface

                }else if("undergraduate".equalsIgnoreCase(role)){

                    navigationManager.loadInterface("UndergraduateInterface.fxml"); // direct to undergraduate interface

                }else if("lecture".equalsIgnoreCase(role)){

                    navigationManager.loadInterface("lectureInterface.fxml");

                }else{
                    JOptionPane.showMessageDialog(null,"Unknown Role.","Login Failed",JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);

            }

        }catch(Exception e){
            throw new RuntimeException(e);
        }


    }


    private void loadInterface(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Assuming you have a reference to the current stage
            Stage stage = (Stage) mainBorder.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load interface.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
