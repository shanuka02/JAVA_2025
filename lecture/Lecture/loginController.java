package Lecture;

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


    Connection con = mySqlCon.getConnection();
    public void handleLoginButtonClick(ActionEvent actionEvent) {



        String userName=  userField.getText().trim();
        String password =  passwordField.getText().trim();


        String query = "SELECT * FROM useraccount WHERE user_id = ? AND password = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, userName);
            pstmt.setString(2, password);

            ResultSet  rs = pstmt.executeQuery();
            if (rs.next()) {


                String roll =  rs.getString("roll");
                //create a session
                Session.setSession (password,userName);

                if("admin".equalsIgnoreCase(roll)){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLfiles/AdminInterface.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                        Scene scene = new Scene(root);
                        ApplicationDrive.getPrimaryStage().setScene(scene);

                    } catch (IOException e) {
                        System.out.println("error: "+e.getMessage());                    }


                }else if("undergraduate".equalsIgnoreCase(roll)){


                }else if("lecture".equalsIgnoreCase(roll)){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                        Scene scene = new Scene(root);
                        ApplicationDrive.getPrimaryStage().setScene(scene);

                    } catch (IOException e) {
                        System.out.println("error: "+e.getMessage());                    }




                }else{
                    JOptionPane.showMessageDialog(null,"Unknown Role.","Login Failed",JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);

            }

        }catch(Exception e){
            System.out.println("Error: "+ e.getMessage() );
        }


    }



}
