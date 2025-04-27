package Lecture;

import  Admin.ApplicationDrive;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class viewMedicalDetails {

    @FXML
    private TableView<modelMedical> tableView;
    @FXML
    private TableColumn<modelMedical, Integer> MediId;

    @FXML
    private Label coIdMedi;

    @FXML
    private Button homebutton;

    @FXML
    private TableColumn<modelMedical, String> reason;

    @FXML
    private TableColumn<modelMedical, String> reqDate;

    @FXML
    private TableColumn<modelMedical, String> status;

    @FXML
    private TableColumn<modelMedical, String> stuId;

    @FXML
    private TableColumn<modelMedical, String> subDate;


    private ObservableList<modelMedical> medicalList = FXCollections.observableArrayList();

    private String courseId;

    public void setCourseLabel(String updateCoLabel) {
        // Set the label text with the selected course name
        coIdMedi.setText(updateCoLabel);
        this.courseId = updateCoLabel;
        loadMedicalDetails();
    }


    private void loadMedicalDetails() {
        Connection conn = mySqlCon.getConnection();

        if (conn == null) {
            System.out.println("Database connection failed.");
            return;
        }

        String query = "SELECT Medi_id, Me_stu_id, Reason, Request_date, Status_, Submitted_date FROM medical WHERE Me_cou_id = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, courseId);
            ResultSet rs = pst.executeQuery();
            medicalList.clear(); // Clear old data if any


            while (rs.next()) {
                medicalList.add(new modelMedical(
                        rs.getInt("Medi_id"),
                        rs.getString("Me_stu_id"),
                        rs.getString("Reason"),
                        rs.getString("Request_date"),
                        rs.getString("Status_"),
                        rs.getString("Submitted_date")
                ));
            }

            MediId.setCellValueFactory(new PropertyValueFactory<>("mediId"));
            stuId.setCellValueFactory(new PropertyValueFactory<>("stuId"));
            reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
            reqDate.setCellValueFactory(new PropertyValueFactory<>("reqDate"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            subDate.setCellValueFactory(new PropertyValueFactory<>("subDate"));

            tableView.setItems(medicalList);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleHome(ActionEvent event ) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }


}
