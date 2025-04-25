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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class viewGpaController {
    @FXML
    private TableView<GpaRecord> gpaTable;

    @FXML
    private TableColumn<GpaRecord, String> stuIdGpa;

    @FXML
    private TableColumn<GpaRecord, Double> cgpa;

    @FXML
    private TableColumn<GpaRecord, Double> sgpa;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML public  void initialize() throws SQLException {
        stuIdGpa.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        cgpa.setCellValueFactory(new PropertyValueFactory<>("cgpa"));
        sgpa.setCellValueFactory(new PropertyValueFactory<>("sgpa"));

        loadGpaData("");

        searchButton.setOnAction(e -> {
            String tg = searchField.getText().trim();
            try {
                loadGpaData(tg);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void loadGpaData(String tgFilter) throws SQLException {
        ObservableList<GpaRecord> records = FXCollections.observableArrayList();

        String sql = """
                SELECT 
                    fm.studentId,
                    ROUND(SUM(cu.credit * CASE
                        WHEN fm.grade IS NOT NULL THEN
                            CASE fm.grade
                                WHEN 'A+' THEN 4.0
                                WHEN 'A'  THEN 4.0
                                WHEN 'A-' THEN 3.7
                                WHEN 'B+' THEN 3.3
                                WHEN 'B'  THEN 3.0
                                WHEN 'B-' THEN 2.7
                                WHEN 'C+' THEN 2.3
                                WHEN 'C'  THEN 2.0
                                WHEN 'C-' THEN 1.7
                                WHEN 'D+' THEN 1.3
                                WHEN 'D'  THEN 1.0
                                WHEN 'E'  THEN 0.0
                                ELSE 0.0
                            END
                        ELSE 0
                    END) / NULLIF(SUM(cu.credit), 0), 2) AS sgpa,

                    ROUND(SUM(
                        CASE 
                            WHEN cu.gpa_state = 'yes' THEN cu.credit * 
                                CASE fm.grade
                                    WHEN 'A+' THEN 4.0
                                    WHEN 'A'  THEN 4.0
                                    WHEN 'A-' THEN 3.7
                                    WHEN 'B+' THEN 3.3
                                    WHEN 'B'  THEN 3.0
                                    WHEN 'B-' THEN 2.7
                                    WHEN 'C+' THEN 2.3
                                    WHEN 'C'  THEN 2.0
                                    WHEN 'C-' THEN 1.7
                                    WHEN 'D+' THEN 1.3
                                    WHEN 'D'  THEN 1.0
                                    WHEN 'E'  THEN 0.0
                                    ELSE 0.0
                                END
                            ELSE 0
                        END
                    ) / NULLIF(SUM(CASE WHEN cu.gpa_state = 'yes' THEN cu.credit ELSE 0 END), 0), 2) AS cgpa

                FROM finalMarks fm
                JOIN courseUnit cu ON fm.courseId = cu.courseId
                WHERE fm.grade IS NOT NULL
                """ + (tgFilter.isEmpty() ? "" : " AND fm.studentId = ?") + """
                GROUP BY fm.studentId;
                """;

        try (
                Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){

            if (!tgFilter.isEmpty()) {
                stmt.setString(1, tgFilter);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String studentId = rs.getString("studentId");
                double sgpaValue = rs.getDouble("sgpa");
                double cgpaValue = rs.getDouble("cgpa");

                records.add(new GpaRecord(studentId, cgpaValue, sgpaValue));
            }

            gpaTable.setItems(records);

        } catch (Exception e) {
            e.printStackTrace();
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

