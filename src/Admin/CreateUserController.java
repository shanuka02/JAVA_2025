package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUserController {

    @FXML
    private Button Adduser;

    @FXML
    private TextField Address;

    @FXML
    private TextField ProfilePic;


    @FXML
    private Button ChooseFilePath;

    @FXML
    private ComboBox<String> Combobox;

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
        Combobox.setItems(list1);

      ObservableList<String> list2 = FXCollections.observableArrayList("ICT","BST","ET","MD");
      Combobox2.setItems(list2);
    }


    @FXML
    void ChooseFile(ActionEvent event) {
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

        }else{
            //set default image to store
            selectedFilePath = "C:\\Users\\User\\IdeaProjects\\JAVA_2025\\resources\\Images\\man.png";
        }
    }

@FXML
    public int handleAddUser(ActionEvent actionEvent) {
        connection = new mySqlCon();
        Connection con = connection.con();

        if(UserId.getText().trim().isEmpty() || Username.getText().trim().isEmpty() ||Email.getText().trim().isEmpty()||
        Password.getText().trim().isEmpty() ||Combobox.getSelectionModel() == null || Combobox2.getSelectionModel() == null || ProfilePic.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Required  fields must be filed","Error",JOptionPane.ERROR_MESSAGE);
            return  1;

        }

        String userId = UserId.getText().trim();
        String username = Username.getText().trim();
        String address = Address.getText().trim();
        String password = Password.getText().trim();
        String phoneNumber = PhoneNumber.getText().trim();
        String depname = Combobox2.getValue();
        String roll = Combobox.getValue();
        String email =  Email.getText().trim();



        String query = "INSERT INTO useraccount (user_id ,user_name ,email,roll,phoneNumber,address,depName,password,profilePic ) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,userId);
            pstm.setString(2,username);
            pstm.setString(3,email);
            pstm.setString(4,roll);
            pstm.setString(5,phoneNumber);
            pstm.setString(6,address);
            pstm.setString(7,depname);
            pstm.setString(8,password);
            pstm.setString(9,ProfilePic.getText().trim());

            int rowAffected = pstm.executeUpdate();

            if(rowAffected > 0){
                JOptionPane.showMessageDialog(null,"User add successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                UserId.clear();
                Username.clear();
                Email.clear();
                Password.clear();
                Address.clear();
                ProfilePic.clear();
                PhoneNumber.clear();
                Combobox.getSelectionModel().clearSelection();
                Combobox2.getSelectionModel().clearSelection();
                selectedFilePath = null;


            }else{
                JOptionPane.showMessageDialog(null,"Unable Add User","Error",JOptionPane.ERROR_MESSAGE);

            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+ e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

return 1;
    }

    @FXML
    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLfiles/ManageUser.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
