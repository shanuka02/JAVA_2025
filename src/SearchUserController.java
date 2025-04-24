import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SearchUserController {
    @FXML
    private Button Button1;

    @FXML
    private Button Button2;

    @FXML
    private  ToggleGroup roll;

    @FXML
    private RadioButton B1;

    @FXML
    private RadioButton B2;

    @FXML
    private RadioButton B3;


    @FXML
    private TableView<UserAccountModel> table2;


    @FXML
    private TableView<UserAccountModel> table3;




    @FXML
    private TableColumn<UserAccountModel, String> colUserId2;
    @FXML
    private TableColumn<UserAccountModel, String> colUserName2;
    @FXML
    private TableColumn<UserAccountModel, String> colEmail2;
    @FXML
    private TableColumn<UserAccountModel, String> colRoll2;
    @FXML
    private TableColumn<UserAccountModel, String> colPhone2;
    @FXML
    private TableColumn<UserAccountModel, String> colAddress2;
    @FXML
    private TableColumn<UserAccountModel, String> colDep2;
    @FXML
    private TableColumn<UserAccountModel, String> colPassword2;


    @FXML
    private TableColumn<UserAccountModel, String> colUserId3;

    @FXML
    private TableColumn<UserAccountModel, String> colUserName3;
    @FXML
    private TableColumn<UserAccountModel, String> colEmail3;
    @FXML
    private TableColumn<UserAccountModel, String> colRoll3;
    @FXML
    private TableColumn<UserAccountModel, String> colPhone3;
    @FXML
    private TableColumn<UserAccountModel, String> colAddress3;
    @FXML
    private TableColumn<UserAccountModel, String> colDep3;
    @FXML
    private TableColumn<UserAccountModel, String> colPassword3;

    @FXML
    private TextField TextField1;

    private mySqlCon connection;






    @FXML
    public void initialize() {



        colUserId2.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colUserName2.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colEmail2.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRoll2.setCellValueFactory(new PropertyValueFactory<>("roll"));
        colPhone2.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colAddress2.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDep2.setCellValueFactory(new PropertyValueFactory<>("depName"));
        colPassword2.setCellValueFactory(new PropertyValueFactory<>("password"));


        colUserId3.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colUserName3.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colEmail3.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRoll3.setCellValueFactory(new PropertyValueFactory<>("roll"));
        colPhone3.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colAddress3.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDep3.setCellValueFactory(new PropertyValueFactory<>("depName"));
        colPassword3.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadAllUsers();

        TextField1.textProperty().addListener((observable,oldValue,newValue )->{
            //If the new text is not empty, call the function searchByTitle()
            if(!newValue.trim().isEmpty()){
               SearchBYID(newValue.trim());
            }else{
                //if table is empty clear the table
                //Table2.getItems().clear();
                loadAllUsers();
            }
        });

    }

    public void loadAllUsers(){
        connection = new mySqlCon();
        Connection con = connection.con();

        String userId = TextField1.getText().trim();
        ObservableList<UserAccountModel> data = FXCollections.observableArrayList();

        String query = "SELECT  user_id ,user_name,email , roll,phoneNumber ,address ,depName ,password   FROM useraccount ";

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                UserAccountModel user = new UserAccountModel(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("roll"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        rs.getString("depName"),
                        rs.getString("password")
                );

                data.add(user);
                table2.setItems(data);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }


    public void SearchBYID(String UserId) {
        connection = new mySqlCon();
        Connection con = connection.con();

        //String userId = TextField1.getText().trim();
        ObservableList<UserAccountModel> data = FXCollections.observableArrayList();

        String query = "SELECT  user_id ,user_name,email , roll,phoneNumber ,address ,depName ,password   FROM useraccount WHERE user_id  LIKE ?";
        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, UserId + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                UserAccountModel user = new UserAccountModel(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("roll"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        rs.getString("depName"),
                        rs.getString("password")
                );
                data.add(user);
            }

            table2.setItems(data);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void handleButton2Click(ActionEvent actionEvent) {
        connection = new mySqlCon();
        Connection con = connection.con();


        ObservableList<UserAccountModel> data = FXCollections.observableArrayList();

        RadioButton selectedRadioButton = (RadioButton)roll.getSelectedToggle();
        String rolls = selectedRadioButton.getText();

        String query = " SELECT  user_id ,user_name,email , roll,phoneNumber ,address ,depName ,password   FROM useraccount WHERE roll = ?";
        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, rolls);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                UserAccountModel user = new UserAccountModel(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("roll"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        rs.getString("depName"),
                        rs.getString("password")
                );
                data.add(user);
            }

            table3.setItems(data);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

        }

    }


    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageUser.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}



