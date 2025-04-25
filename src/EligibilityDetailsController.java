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
import javafx.stage.Stage;

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
        Connection conn = dbConnection.getConnection();

        if (conn != null) {
            System.out.println("Database connected successfully");

            try{
                Statement stmt = conn.createStatement();
                String query = "SELECT studentId, eligibility FROM caMarks WHERE courseId = '" + courseId + "'";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    String studentId = rs.getString("studentId");
                    String eligibility = rs.getString("eligibility");

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
    public void handleHome(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
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




