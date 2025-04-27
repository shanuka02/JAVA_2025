package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
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

        }else{
            //set default image path
            selectedFilePath = "C:\\Users\\User\\IdeaProjects\\JAVA_2025\\resources\\Images\\man.png";
        }

    }

    @FXML
    public  int handleAddUser(ActionEvent actionEvent) {
        connection = new  mySqlCon();
        Connection con = connection.con();

        if(UserId.getText().trim().isEmpty() || Username.getText().trim().isEmpty() ||Email.getText().trim().isEmpty()||
               Combobox1.getSelectionModel() == null || Combobox2.getSelectionModel() == null || ProfilePic.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Required  fields must be filed","Error",JOptionPane.ERROR_MESSAGE);
            return  1;

        }

        String userId = UserId.getText().trim();
        String username = Username.getText().trim();
        String address = Address.getText().trim();

        String phoneNumber = PhoneNumber.getText().trim();
        String depname = Combobox2.getValue();
        String roll = Combobox1.getValue();
        String email =  Email.getText().trim();
        String filepath = ProfilePic.getText().trim();

        String query = "UPDATE userAccount SET user_name = ?, email = ?, roll = ?, phoneNumber = ?, address = ?, depName = ?, profilePic = ? WHERE user_id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement(query);

            pstm.setString(1,username);
            pstm.setString(2,email);
            pstm.setString(3,roll);
            pstm.setString(4,phoneNumber);
            pstm.setString(5,address);
            pstm.setString(6,depname);

            pstm.setString(7,filepath);
            pstm.setString(8,Session.getUserId());

            int rowAffected = pstm.executeUpdate();

            if(rowAffected > 0){
                JOptionPane.showMessageDialog(null,"Save Data Successfully","Success",JOptionPane.INFORMATION_MESSAGE);


            }else{
                JOptionPane.showMessageDialog(null,"Unable to Update","Fail Update",JOptionPane.ERROR_MESSAGE);

            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
return 1;
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
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        return 0;

    }

    @FXML
    public void HandleHomeButton(ActionEvent event) {

        String roll = Session.getRollVal();
        if(roll.equalsIgnoreCase("Admin")){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLfiles/AdminInterface.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                ApplicationDrive.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                System.out.println("error: " + e.getMessage());
            }
        }else if(roll.equalsIgnoreCase("Technical Officer")){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/TechnicalOfficer.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                ApplicationDrive.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                System.out.println("error: " + e.getMessage());
            }
        }else if(roll.equalsIgnoreCase("Lecture")){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                ApplicationDrive.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                System.out.println("error: " + e.getMessage());
            }
        }else if(roll.equalsIgnoreCase("Undergraduate")){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                ApplicationDrive.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                System.out.println("error: " + e.getMessage());
            }
        }else{
            System.out.println("error");
        }

    }
}
