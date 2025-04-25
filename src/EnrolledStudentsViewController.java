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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrolledStudentsViewController {
    @FXML private Label CoIdEnrollement;
    @FXML private TableView<modelUserData> tableView;
    @FXML private TableColumn<modelUserData,String> tgEnroll;
    @FXML private TableColumn<modelUserData,String> NameEnroll;

    private ObservableList<modelUserData> dataList = FXCollections.observableArrayList();


    public void setCourseLabel(String clabel) {
        CoIdEnrollement.setText(clabel);
        loadEnrollmentData();
    }

    public void initialize() {
        tgEnroll.setCellValueFactory(new PropertyValueFactory<>("userId"));
        NameEnroll.setCellValueFactory(new PropertyValueFactory<>("userName"));

    }

    public void loadEnrollmentData(){
        String courseId = CoIdEnrollement.getText();  // Get courseId from label
        dataList.clear();

        String query = """
            SELECT u.user_id, u.user_name 
            FROM userAccount u 
            JOIN studentCourse c ON u.user_id = c.studentId 
            WHERE c.courseId = ?
        """;

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1,courseId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("user_id");
                String userName = rs.getString("user_name");

                System.out.println("Loaded: " + userId + " - " + userName);  // Debug print

                dataList.add(new modelUserData(userId, userName));
            }
            tableView.setItems(dataList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
