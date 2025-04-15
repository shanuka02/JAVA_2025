import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


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
    private TableColumn<UserAccountModel, String> colUserId;
    @FXML
    private TableColumn<UserAccountModel, String> colUserName;
    @FXML
    private TableColumn<UserAccountModel, String> colEmail;
    @FXML
    private TableColumn<UserAccountModel, String> colRoll;
    @FXML
    private TableColumn<UserAccountModel, String> colPhone;
    @FXML
    private TableColumn<UserAccountModel, String> colAddress;
    @FXML
    private TableColumn<UserAccountModel, String> colDep;
    @FXML
    private TableColumn<UserAccountModel, String> colPassword;

    @FXML
    private TextField TextField1;

    private mySqlCon connection;





    @FXML
    public void initialize() {
        // Setup Table Columns with properties
        colUserId.setCellValueFactory(data -> data.getValue().user_idProperty());
        colUserName.setCellValueFactory(data -> data.getValue().user_nameProperty());
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());
        colRoll.setCellValueFactory(data -> data.getValue().rollProperty());
        colPhone.setCellValueFactory(data -> data.getValue().phoneNumberProperty());
        colAddress.setCellValueFactory(data -> data.getValue().addressProperty());
        colDep.setCellValueFactory(data -> data.getValue().depNameProperty());
        colPassword.setCellValueFactory(data -> data.getValue().passwordProperty());

    }


    public void handleButton1Click(ActionEvent actionEvent) {
        connection = new mySqlCon();
        Connection con = connection.con();

        String userId = TextField1.getText().trim();
        ObservableList<UserAccountModel> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM userAccount WHERE user_id = ?";
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
                        rs.getString("PhoneNumber"),
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

        String query = "SELECT * FROM userAccount WHERE roll = ?";
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
                        rs.getString("PhoneNumber"),
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
        table2.getItems().clear();
    }


}



class UserAccountModel {
    private final StringProperty user_id;
    private final StringProperty user_name;
    private final StringProperty email;
    private final StringProperty roll;
    private final StringProperty phoneNumber;
    private final StringProperty address;
    private final StringProperty depName;
    private final StringProperty password;

    public UserAccountModel(String user_id, String user_name, String email, String roll,
                            String phoneNumber, String address, String depName, String password) {
        this.user_id = new SimpleStringProperty(user_id);
        this.user_name = new SimpleStringProperty(user_name);
        this.email = new SimpleStringProperty(email);
        this.roll = new SimpleStringProperty(roll);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.address = new SimpleStringProperty(address);
        this.depName = new SimpleStringProperty(depName);
        this.password = new SimpleStringProperty(password);
    }

    public StringProperty user_idProperty() { return user_id; }
    public StringProperty user_nameProperty() { return user_name; }
    public StringProperty emailProperty() { return email; }
    public StringProperty rollProperty() { return roll; }
    public StringProperty phoneNumberProperty() { return phoneNumber; }
    public StringProperty addressProperty() { return address; }
    public StringProperty depNameProperty() { return depName; }
    public StringProperty passwordProperty() { return password; }
}
