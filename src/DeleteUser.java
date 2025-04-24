import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class DeleteUser {

        @FXML
        private  Button Delete;

        @FXML
        private Button BackButton;

        @FXML
        private TextField TextField2;

        @FXML
        private TableColumn<UserAccountModel, ?> colDep1;

        @FXML
        private TableColumn<UserAccountModel, ?> colRoll1;

        @FXML
        private TableColumn<UserAccountModel, ?> colUserId1;

        @FXML
        private TableColumn<UserAccountModel, ?> colUserName1;

        @FXML
        private TableView<UserAccountModel> table1;

        mySqlCon connection;

        @FXML
        public void initialize()
        {

                colUserId1.setCellValueFactory(new PropertyValueFactory<>("user_id"));
                colUserName1.setCellValueFactory(new PropertyValueFactory<>("user_name"));
                colRoll1.setCellValueFactory(new PropertyValueFactory<>("roll"));

                loadAllUsers();



                TextField2.textProperty().addListener((observable,oldValue,newValue )->{
                        //If the new text is not empty, call the function searchByTitle()
                        if(!newValue.trim().isEmpty()){
                                searchByUserId(newValue.trim());
                        }else{
                                //if table is empty clear the table
                                loadAllUsers();
                                //table1.getItems().clear();
                        }
                });


        }

        private void searchByUserId(String code) {
                connection = new mySqlCon();
                Connection con = connection.con();
                ObservableList<UserAccountModel> data = FXCollections.observableArrayList();



                String query = "select * from useraccount WHERE user_id  LIKE ?";

                try {
                        PreparedStatement pstm =  con.prepareStatement(query);
                        pstm.setString(1,code + "%");
                        ResultSet rs = pstm.executeQuery();

                        while(rs.next()){
                                UserAccountModel user = new UserAccountModel(
                                        rs.getString(1),
                                        rs.getString(2),
                                        rs.getString(4)

                                );
                                data.add(user);
                        }
                        table1.setItems(data);


                } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

                }

        }


        public void loadAllUsers(){
                connection = new mySqlCon();
                Connection con = connection.con();

                String userId = TextField2.getText().trim();
                ObservableList<UserAccountModel> data = FXCollections.observableArrayList();

                String query = "SELECT  user_id ,user_name, roll FROM useraccount ";

                try {
                        PreparedStatement pstm = con.prepareStatement(query);
                        ResultSet rs = pstm.executeQuery();

                        while(rs.next()){
                                UserAccountModel user = new UserAccountModel(
                                        rs.getString("user_id"),
                                        rs.getString("user_name"),
                                        rs.getString("roll")

                                );

                                data.add(user);
                        }
                        table1.setItems(data);
                } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null," " + e.getMessage(),"warning",JOptionPane.ERROR_MESSAGE);
                }

        }

        @FXML
        void handleDeleteButton(ActionEvent event) {
                UserAccountModel selectNotice = table1.getSelectionModel().getSelectedItem();

                if(selectNotice != null){
                        String id = selectNotice.getUser_id();


                        connection = new mySqlCon();
                        Connection con = connection.con();

                        String query = "DELETE FROM useraccount WHERE user_id  = ?";

                        try {
                                PreparedStatement pstm = con.prepareStatement(query);
                                pstm.setString(1,id);
                                int rowDeleted = pstm.executeUpdate();
                                if(  rowDeleted > 0){
                                        JOptionPane.showMessageDialog(null,"User Deleted Successfully ","Success",JOptionPane.INFORMATION_MESSAGE);

                                }else{
                                        JOptionPane.showMessageDialog(null,"User Deleted Unsuccessfully ");

                                }





                        } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null," " + e.getMessage(),"warning",JOptionPane.ERROR_MESSAGE);
                        }


                }
                loadAllUsers();


        }
        @FXML
        void BackbuttonHandle(){
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
