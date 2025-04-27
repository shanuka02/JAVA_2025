import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAttController {
    @FXML
    public TableView <modelAttendence>attendanceTable;


    @FXML
    private TableColumn<modelAttendence, Integer> AllAttendence;

    @FXML
    private TableColumn<modelAttendence, Integer> AttPercentage;

    @FXML
    private TableColumn<modelAttendence, String> AttType;

    @FXML
    private Label CoIdAtt;

    @FXML
    private TextField EnterTgforAtt;

    @FXML
    private TableColumn<modelAttendence, String> NameAtt;

    @FXML
    private TableColumn<modelAttendence, Integer> PresentAttendence;

    @FXML
    private RadioButton medicalButton;

    @FXML
    private TableColumn<modelAttendence, String> tgAttendence;


    public void setCourseLabel(String updateCoLable) {
        CoIdAtt.setText(updateCoLable);
    }

    @FXML
    private void initialize(){
        LoadAttenData();
        medicalButton.setOnAction(e -> LoadAttenData());
    }
//

        private void LoadAttenData() {
            ObservableList<modelAttendence> data = FXCollections.observableArrayList();

            String tgFilter = EnterTgforAtt.getText().trim();
            boolean isMedical = medicalButton.isSelected();

            // If TG Filter is empty, match everything
            if ( tgFilter.isEmpty()) {
                tgFilter = "%";
            } else {
                tgFilter = "%" + tgFilter + "%";
            }

            // Query based on the medical status selection
            String query;
            if (isMedical) {
                query = """
                         SELECT a.Att_stu_id, u.user_name, a.Att_cou_id, a.Lec_type,
                           SUM(CASE WHEN a.Status_ = 'Present' or a.Status_ = 'Medical' THEN 1 ELSE 0 END) AS present,
                           COUNT(*) AS total
                    FROM attendance a
                    LEFT JOIN userAccount u ON u.user_id = a.Att_stu_id
                    WHERE a.Att_stu_id LIKE ?
                    GROUP BY a.Att_stu_id, u.user_name, a.Att_cou_id, a.Lec_type
                    """;
            } else {
                query = """
                    SELECT a.Att_stu_id, u.user_name, a.Att_cou_id, a.Lec_type,
                           SUM(CASE WHEN a.Status_ = 'Present' THEN 1 ELSE 0 END) AS present,
                           COUNT(*) AS total
                    FROM attendance a
                    LEFT JOIN userAccount u ON u.user_id = a.Att_stu_id
                    WHERE a.Att_stu_id LIKE ?
                    GROUP BY a.Att_stu_id, u.user_name, a.Att_cou_id, a.Lec_type
                    """;
            }

            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, tgFilter);
                ResultSet rs = stmt.executeQuery();

                // Fetch results and populate the data list
                while (rs.next()) {
                    String tg = rs.getString("Att_stu_id");
                    String name = rs.getString("user_name");
                    String type = rs.getString("Lec_type");
                    int present = rs.getInt("present");
                    int total = rs.getInt("total");
                    double percentage = total == 0 ? 0 : (present * 100.0) / total;

                    System.out.println("TG: " + tg + ", Name: " + name + ", Present: " + present);

                    // Add data to the observable list
                    data.add(new modelAttendence(tg, name, type, present, total, percentage));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Set up the cell value factories
            tgAttendence.setCellValueFactory(new PropertyValueFactory<>("tg"));
            NameAtt.setCellValueFactory(new PropertyValueFactory<>("name"));
            AttType.setCellValueFactory(new PropertyValueFactory<>("attType"));
            PresentAttendence.setCellValueFactory(new PropertyValueFactory<>("present"));
            AllAttendence.setCellValueFactory(new PropertyValueFactory<>("total"));
            AttPercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));

            // Bind the observable list to the TableView
            attendanceTable.setItems(data);
        }

        public void handleSearch(ActionEvent event) {
            LoadAttenData();
        }

        public void handleHome(ActionEvent event) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                ApplicationDrive.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                System.out.println("Error loading home page: " + e.getMessage());
            }
        }

}
