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
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteTimeTable {

    @FXML
    private TableColumn<TimetableModel, String> Caption;


    @FXML
    private TableColumn<TimetableModel, String> Date;


    @FXML
    private TableColumn<TimetableModel, String> ID;

    @FXML
    private TableView<TimetableModel> Table1;
    mySqlCon connection;

    @FXML
    public void initialize(){
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Date.setCellValueFactory(new PropertyValueFactory<>("submiteddate"));

        Caption  .setCellValueFactory(new PropertyValueFactory<>("depname"));

        loadData();
    }
    private void loadData() {
        connection = new mySqlCon();
        Connection con = connection.con();

        ObservableList<TimetableModel> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM timeTable";

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                TimetableModel timetable = new TimetableModel(
                        rs.getString("id"),
                        rs.getString("caption"),
                        rs.getString("submiteddate")


                );
                data.add(timetable);
            }
            Table1.setItems(data);
        } catch (SQLException e) {
            System.out.println("Error" +e.getMessage());
        }

    }

    @FXML
    void HandleDelete(ActionEvent event) {
        TimetableModel selectNotice = Table1.getSelectionModel().getSelectedItem();

        if(selectNotice != null){
            String id = selectNotice.getId();


            connection = new mySqlCon();
            Connection con = connection.con();

            String query = "DELETE FROM notice WHERE notice_id = ?";

            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1,id);
                pstm.executeUpdate();




            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Select a Notice Before Delete","warning",JOptionPane.ERROR_MESSAGE);
            }


        }
        loadData();


    }

    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageTimetable.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
