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

    //store username and Password
    private  String password;
    private  String userName;


    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void handleLoginButtonClick(ActionEvent actionEvent) {

        mySqlCon conobj = new mySqlCon();
        Connection con  = conobj.con();

         userName=  userField.getText().trim();
         password =  passwordField.getText().trim();


        String query = "SELECT * FROM userAccount WHERE user_name = ? AND password = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, userName);
            pstmt.setString(2, password);

            ResultSet  rs = pstmt.executeQuery();
            if (rs.next()) {


                String roll =  rs.getString("roll");
                if("admin".equalsIgnoreCase(roll)){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\AdminInterface.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                        Scene scene = new Scene(root);
                        ApplicationDrive.getPrimaryStage().setScene(scene);

                    } catch (IOException e) {
                        System.out.println("error");                    }


                }else if("undergraduate".equalsIgnoreCase(roll)){


                }else if("lecture".equalsIgnoreCase(roll)){


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



}
