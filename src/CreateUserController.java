import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        ObservableList<String> list1 = FXCollections.observableArrayList("Undergraduate", "Lecture", "Technical Officer", "Admin");
        Combobox.setItems(list1);

      ObservableList<String> list2 = FXCollections.observableArrayList("ICT","BST","ET","Multy Disiplinary");
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

        }
    }


    public void handleAddUser(ActionEvent actionEvent) {
        connection = new mySqlCon();
        Connection con = connection.con();

        String userId = UserId.getText().trim();
        String username = Username.getText().trim();
        String address = Address.getText().trim();
        String password = Password.getText().trim();
        String phoneNumber = PhoneNumber.getText().trim();
        String depname = Combobox2.getValue();
        String roll = Combobox.getValue();
        String email =  Email.getText().trim();

        String query = "INSERT INTO useraccount VALUES ?,?,?,?,?,?,?,?,?";
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
            pstm.setString(9,selectedFilePath);



        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }


    }
}
