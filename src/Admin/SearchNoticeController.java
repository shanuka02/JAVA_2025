package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchNoticeController {

    @FXML
    private TableColumn<NoticeModel, String> Content2;

    @FXML
    private TableColumn<NoticeModel, String> Content3;



    @FXML
    private TableColumn<NoticeModel, String> Date2;

    @FXML
    private TableColumn<NoticeModel, String> Date3;

    @FXML
    private TextField DateTextField2;



    @FXML
    private TableColumn<NoticeModel, String>Id2;

    @FXML
    private TableColumn<NoticeModel, String> Id3;


    @FXML
    private TableColumn<NoticeModel, String> Roll2;

    @FXML
    private TableColumn<NoticeModel, String>Roll3;

    @FXML
    private Button SearchButton;



    @FXML
    private TableView<NoticeModel> Table2;

    @FXML
    private TableView<NoticeModel> Table3;



    @FXML
    private TableColumn<NoticeModel, String> Title2;

    @FXML
    private TableColumn<NoticeModel, String> Title3;

    @FXML
    private TextField TitleTexttField;

    mySqlCon connection;

    @FXML
    public void initialize(){


        Id2.setCellValueFactory(new PropertyValueFactory<>("id"));
        Title2.setCellValueFactory(new PropertyValueFactory<>("title"));
        Date2.setCellValueFactory(new PropertyValueFactory<>("date"));
        Roll2.setCellValueFactory(new PropertyValueFactory<>("content"));
        Content2.setCellValueFactory(new PropertyValueFactory<>("roll"));

        Id3.setCellValueFactory(new PropertyValueFactory<>("id"));
        Title3.setCellValueFactory(new PropertyValueFactory<>("title"));
        Date3.setCellValueFactory(new PropertyValueFactory<>("date"));
        Roll3.setCellValueFactory(new PropertyValueFactory<>("content"));
        Content3.setCellValueFactory(new PropertyValueFactory<>("roll"));

        loadData();

        //keep watching what user types into th e text filed
        //Add listener , every time the text changes, this code runs
        //observable,oldValue,newValue  this say text changed from old value to newValue
        TitleTexttField.textProperty().addListener((observable,oldValue,newValue )->{
            //If the new text is not empty, call the function searchByTitle()
            if(!newValue.trim().isEmpty()){
                searchByUserTitle(newValue.trim());
            }else{
                //if table is empty clear the table
                //Table2.getItems().clear();
                loadData();
            }
        });



    }

    private void searchByUserTitle(String inputTitle) {
        connection = new mySqlCon();
        Connection con = connection.con();

        ObservableList<NoticeModel> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM notice WHERE title  LIKE ?";


        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,inputTitle + "%");
            ResultSet rs  = pstm.executeQuery();

            while(rs.next()){
                NoticeModel notice = new NoticeModel(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("postedDay"),
                        rs.getString("content"),
                        rs.getString("userRoll")

                );
                data.add(notice);
            }
            Table2.setItems(data);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadData(){
        connection = new mySqlCon();
        Connection con = connection.con();

        ObservableList<NoticeModel> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM notice";

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null,"No Notice Found");
            }

            while(rs.next()){
                NoticeModel notice = new NoticeModel(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("postedDay"),
                        rs.getString("content"),
                        rs.getString("userRoll")

                );
                data.add(notice);
            }
            Table2.setItems(data);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    @FXML
    void HandleSearch(ActionEvent event) {
        connection = new mySqlCon();
        Connection con = connection.con();
        String date = DateTextField2.getText().trim();

        ObservableList<NoticeModel> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM notice WHERE postedDay = ? ";
        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,date);
            ResultSet rs = pstm.executeQuery();

          /*  if(!rs.next()){
                JOptionPane.showMessageDialog(null,"No Notice Found For the day");
            }*/

            while(rs.next()){
                NoticeModel notice = new NoticeModel(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("postedDay"),
                        rs.getString("content"),
                        rs.getString("userRoll")

                );
                data.add(notice);
            }
            Table3.setItems(data);


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }


    }

    @FXML
    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLfiles/ManageNotice.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
