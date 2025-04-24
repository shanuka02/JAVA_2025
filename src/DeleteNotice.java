import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteNotice {
    @FXML
    private TableColumn<NoticeModel, String> Date;


    @FXML
    private TableColumn<NoticeModel, Integer> Id;

    @FXML
    private TableView<NoticeModel> Table1;

    @FXML
    private TableColumn<NoticeModel,String > Title;

    @FXML
    private TextField NoticeIdField;




    mySqlCon connection;


    public void initialize() {
        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadData();

        NoticeIdField.textProperty().addListener((observable,oldValue,newValue )->{
            //If the new text is not empty, call the function searchByTitle()
            if(!newValue.trim().isEmpty()){
                searchByNoticeId(newValue.trim());
            }else{
                //if table is empty clear the table
                loadData();
                //Table1.getItems().clear();
            }
        });
    }

    public void loadData(){
        connection = new mySqlCon();
        Connection con = connection.con();

        ObservableList<NoticeModel> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM notice";

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                NoticeModel notice = new NoticeModel(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("postedDay")


                );
                data.add(notice);
            }
            Table1.setItems(data);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void  searchByNoticeId(String NoticeId) {
        connection = new mySqlCon();
        Connection con = connection.con();

        ObservableList<NoticeModel> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM notice WHERE notice_id  LIKE ?";


        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,NoticeId + "%");
            ResultSet rs  = pstm.executeQuery();

            while(rs.next()){
                NoticeModel notice = new NoticeModel(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("postedDay")
                );
                data.add(notice);
            }
            Table1.setItems(data);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }


    public void handleDeleteButtonAction(ActionEvent actionEvent) {
        NoticeModel selectNotice = Table1.getSelectionModel().getSelectedItem();

        if(selectNotice != null){
            int noticeId = selectNotice.getId();
            NoticeIdField.setText(String.valueOf(noticeId));

            connection = new mySqlCon();
            Connection con = connection.con();

            String query = "DELETE FROM notice WHERE notice_id = ?";

            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setInt(1,noticeId);
                int rowDeleted= pstm.executeUpdate();
                NoticeIdField.clear();



                if(  rowDeleted > 0){
                    JOptionPane.showMessageDialog(null,"Notice Deleted Successfully ","Success",JOptionPane.INFORMATION_MESSAGE);

                }else{
                    JOptionPane.showMessageDialog(null,"Notice Deleted Unsuccessfully ");

                }




            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }


        }
        loadData();
    }

    @FXML
    void HandleSearchUSer(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\SearchNotice.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());

        }
    }

    @FXML
    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageNotice.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());

        }
    }
}
