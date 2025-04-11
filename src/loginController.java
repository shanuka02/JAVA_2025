import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

import javax.swing.*;
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
    private Label lable1; // Corrected the typo here

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userField;


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
                JOptionPane.showMessageDialog(null, "Login successful!");

// Error popup
                // You can now redirect or load the next scene
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);

            }

        }catch(Exception e){
            throw new RuntimeException(e);
        }


    }
}
