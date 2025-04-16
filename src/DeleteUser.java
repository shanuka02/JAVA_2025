import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteUser{

        @FXML
        private Button DeleteAllButton;

        @FXML
        private Button DeleteUser;

        @FXML
        private Button SearchButton;

        @FXML
        private TextField UserIDField;

        mySqlCon connection;

        @FXML
        int HandleDeleteAll(ActionEvent event) {
            connection = new mySqlCon();
            Connection con = connection.con();



            String query = "SELECT COUNT(*) From useraccount";
            try {
                PreparedStatement countstm = con.prepareStatement(query);
                ResultSet rs = countstm.executeQuery();

                if(rs.next()){
                        int count = rs.getInt(1);

                        if(count == 0){
                                JOptionPane.showMessageDialog(null, "No users Found,Table is Empty","Warning",JOptionPane.ERROR_MESSAGE);
                                return 1;

                        }
                        int Choice = JOptionPane.showConfirmDialog(null,
                                "Do you want To delete "+ count + " users from the database",
                                "Confirm Delet All",
                                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

                        if(Choice == JOptionPane.YES_OPTION){
                                String deletequery = "DELETE FROM useraccount";
                                PreparedStatement pstm = con.prepareStatement(deletequery);

                                int rowDeleted = pstm.executeUpdate();

                        }

                }


            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Error: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);

            }
        return 0;

        }

        @FXML
        int HandleDeleteUser(ActionEvent event) {
                connection = new mySqlCon();
                Connection con = connection.con();
                String query = "SELECT COUNT(*) From useraccount WHERE user_id = ?";

                String id = UserIDField.getText().trim();

                if(id.isEmpty()){
                        JOptionPane.showMessageDialog(null,"Enter ID Before Delete","Warning",JOptionPane.ERROR_MESSAGE);

                }
                try {
                        PreparedStatement pstm = con.prepareStatement(query);
                        pstm.setString(1,id);

                        ResultSet rs = pstm.executeQuery();

                        if(rs.next()){
                                int count = rs.getInt(1);


                                if(count == 0){
                                        JOptionPane.showMessageDialog(null, "No users Found,Table is Empty","Warning",JOptionPane.ERROR_MESSAGE);
                                        return 1;

                                }
                                int Choice = JOptionPane.showConfirmDialog(null,
                                         count + " user is  found, confirm to delete!",
                                        "Confirm Delete All",
                                        JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

                                if(Choice == JOptionPane.YES_OPTION){
                                        String deletequery = "DELETE FROM useraccount WHERE user_id = ?" ;
                                        PreparedStatement pstm2 = con.prepareStatement(deletequery);

                                        int rowDeleted = pstm2.executeUpdate();


                                }

                        }


                } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null,"Error: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);

                }
                return 0;

        }

        @FXML
        void HandleSearchUSer(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\search_user.fxml"));
                try {
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        ApplicationDrive.getPrimaryStage().setScene(scene);

                } catch (IOException e) {
                        System.out.println("error: " + e.getMessage());
                }
        }

        }



