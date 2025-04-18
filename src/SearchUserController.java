import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

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
    private TableView<UserAccountModel> table1;

    @FXML
    private TableView<UserAccountModel> table2;


    @FXML
    private TableView<UserAccountModel> table3;

    @FXML
    private TableColumn<UserAccountModel, String> colUserId1;
    @FXML
    private TableColumn<UserAccountModel, String> colUserName1;
    @FXML
    private TableColumn<UserAccountModel, String> colEmail1;
    @FXML
    private TableColumn<UserAccountModel, String> colRoll1;
    @FXML
    private TableColumn<UserAccountModel, String> colPhone1;
    @FXML
    private TableColumn<UserAccountModel, String> colAddress1;
    @FXML
    private TableColumn<UserAccountModel, String> colDep1;
    @FXML
    private TableColumn<UserAccountModel, String> colPassword1;


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
        // Setup Table Columns with properties

        colUserId3.setCellValueFactory(data -> data.getValue().user_idProperty());
        colUserName3.setCellValueFactory(data -> data.getValue().user_nameProperty());
        colEmail3.setCellValueFactory(data -> data.getValue().emailProperty());
        colRoll3.setCellValueFactory(data -> data.getValue().rollProperty());
        colPhone3.setCellValueFactory(data -> data.getValue().phoneNumberProperty());
        colAddress3.setCellValueFactory(data -> data.getValue().addressProperty());
        colDep3.setCellValueFactory(data -> data.getValue().depNameProperty());
        colPassword3.setCellValueFactory(data -> data.getValue().passwordProperty());

        colUserId1.setCellValueFactory(data -> data.getValue().user_idProperty());
        colUserName1.setCellValueFactory(data -> data.getValue().user_nameProperty());
        colEmail1.setCellValueFactory(data -> data.getValue().emailProperty());
        colRoll1.setCellValueFactory(data -> data.getValue().rollProperty());
        colPhone1.setCellValueFactory(data -> data.getValue().phoneNumberProperty());
        colAddress1.setCellValueFactory(data -> data.getValue().addressProperty());
        colDep1.setCellValueFactory(data -> data.getValue().depNameProperty());
        colPassword1.setCellValueFactory(data -> data.getValue().passwordProperty());

        colUserId2.setCellValueFactory(data -> data.getValue().user_idProperty());
        colUserName2.setCellValueFactory(data -> data.getValue().user_nameProperty());
        colEmail2.setCellValueFactory(data -> data.getValue().emailProperty());
        colRoll2.setCellValueFactory(data -> data.getValue().rollProperty());
        colPhone2.setCellValueFactory(data -> data.getValue().phoneNumberProperty());
        colAddress2.setCellValueFactory(data -> data.getValue().addressProperty());
        colDep2.setCellValueFactory(data -> data.getValue().depNameProperty());
        colPassword2.setCellValueFactory(data -> data.getValue().passwordProperty());

        loadAllUsers();
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
            }
            table1.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void handleButton1Click(ActionEvent actionEvent) {
        connection = new mySqlCon();
        Connection con = connection.con();

        String userId = TextField1.getText().trim();
        ObservableList<UserAccountModel> data = FXCollections.observableArrayList();

        String query = "SELECT  user_id ,user_name,email , roll,phoneNumber ,address ,depName ,password   FROM useraccount WHERE user_id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, userId);
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
            e.printStackTrace();
        }
    }

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
            e.printStackTrace();
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



