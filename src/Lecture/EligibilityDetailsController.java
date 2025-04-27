package Lecture;

import Admin.ApplicationDrive;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EligibilityDetailsController {
    @FXML private Label couSelectLabel;
    @FXML private TableView<EligibilityModel> eligibilityTable;
    @FXML private TableColumn<EligibilityModel, String> studentIdEligibility;
    @FXML private TableColumn<EligibilityModel, String> EligibilityStates;

    private ObservableList<EligibilityModel> dataList = FXCollections.observableArrayList();

    public void setCourseLabel(String courseName) {
        couSelectLabel.setText(courseName);
        loadEligibilityData(courseName);
    }

    private void loadEligibilityData(String courseId){
        Connection conn = mySqlCon.getConnection();

        if (conn != null) {
            System.out.println("Database connected successfully");

            try{
                Statement stmt = conn.createStatement();
                String query = "SELECT a.Att_stu_id AS studentId, " +
                        "CASE " +
                        "WHEN (SUM(CASE WHEN a.Status_ IN ('Present', 'Medical') THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) >= 80.0 " +
                        "AND cm.eligibility = 'yes' THEN 'Eligi' " +
                        "ELSE 'Not' " +
                        "END AS eligibilityStatus " +
                        "FROM attendance a " +
                        "JOIN caMarks cm ON a.Att_stu_id = cm.studentId AND a.Att_cou_id = cm.courseId " +
                        "WHERE a.Att_cou_id = '" + courseId + "' " +
                        "GROUP BY a.Att_stu_id, cm.eligibility";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    String studentId = rs.getString("studentId");
                    String eligibility = rs.getString("eligibilityStatus");

                    dataList.add(new EligibilityModel(studentId,eligibility));
                }

                studentIdEligibility.setCellValueFactory(new PropertyValueFactory<>("studentId"));
                EligibilityStates.setCellValueFactory(new PropertyValueFactory<>("eligibility"));

                eligibilityTable.setItems(dataList);

                rs.close();
                stmt.close();
                conn.close();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void handleHome (ActionEvent event) {
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




