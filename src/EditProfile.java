import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditProfile {
    @FXML
    private Button Adduser;

    @FXML
    private TextField Address;

    @FXML
    private TextField ProfilePic;


    @FXML
    private Button ChooseFilePath;

    @FXML
    private ComboBox<String> Combobox1;

    @FXML
    private ComboBox<String> Combobox2;

    @FXML
    private TextField Email;

    @FXML
    private TextField Password;

    @FXML
    private TextField PhoneNumber;

    @FXML
    private TextField UserId;

    @FXML
    private TextField Username;

    private String selectedFilePath;

    mySqlCon connection;


    @FXML
    public void initialize() {
        ObservableList<String> list1 = FXCollections.observableArrayList("Undergraduate", "Lecture", "Technical Officer");
        Combobox1.setItems(list1);

        ObservableList<String> list2 = FXCollections.observableArrayList("ICT","BST","ET","MD");
        Combobox2.setItems(list2);

        loadData();
    }

    @FXML
    public void ChooseFile(ActionEvent event) {
        FileChooser fileChooser= new FileChooser();

        fileChooser.setTitle("Select File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files","*"),
                new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg","*.gif"),
                new FileChooser.ExtensionFilter("PDF FILES","*.pdf")
        );

        Button sourceButton =(Button) event.getSource();
        Stage stage =(Stage) sourceButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!= null){
            selectedFilePath = selectedFile.getAbsolutePath();
            ProfilePic.setText(selectedFilePath);

        }

    }

    @FXML
    public void handleAddUser(ActionEvent actionEvent) {
        connection = new mySqlCon();
        Connection con = connection.con();

        String userId = UserId.getText().trim();
        String username = Username.getText().trim();
        String address = Address.getText().trim();
        String password = Password.getText().trim();
        String phoneNumber = PhoneNumber.getText().trim();
        String depname = Combobox2.getValue();
        String roll = Combobox1.getValue();
        String email =  Email.getText().trim();

        String query = "INSERT INTO useraccount (user_name ,email,roll,phoneNumber,address,depName,profilePic ) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement(query);

            pstm.setString(1,username);
            pstm.setString(2,email);
            pstm.setString(3,roll);
            pstm.setString(4,phoneNumber);
            pstm.setString(5,address);
            pstm.setString(6,depname);

            pstm.setString(7,selectedFilePath);

            int rowAffected = pstm.executeUpdate();

            if(rowAffected > 0){
                JOptionPane.showMessageDialog(null,"User add successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                UserId.clear();
                Username.clear();
                Email.clear();

                Address.clear();
                ProfilePic.clear();
                PhoneNumber.clear();
                Combobox1.getSelectionModel().clearSelection();
                Combobox2.getSelectionModel().clearSelection();
                selectedFilePath = null;


            }else{
                JOptionPane.showMessageDialog(null,"Unable to Update","Fail Update",JOptionPane.ERROR_MESSAGE);

            }







        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

    }

    public int loadData(){
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



                UserId.setText(rs.getString(1));
                Username.setText(rs.getString(2));
                Email.setText(rs.getString(3));
                Combobox1.setValue(rs.getString(4));
                PhoneNumber.setText(rs.getString(5));
                Address.setText(rs.getString(6));
                Combobox2.setValue(rs.getString(7));
                ProfilePic.setText(rs.getString(9));
            }


        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
        }
        return 0;

    }

}
