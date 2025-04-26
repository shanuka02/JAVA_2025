package TechnicalOfficer;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;

public class MedicalEdit {
    @FXML
    private TableView<AttendanceRow> MedicalTable;

    @FXML
    private TableColumn<AttendanceRow, String> column1;

    @FXML
    private TableColumn<AttendanceRow, String> column2;

    @FXML
    private TableColumn<AttendanceRow, String> column3;

    @FXML
    private TableColumn<AttendanceRow, String> column4;

    @FXML
    private TableColumn<AttendanceRow, String> column5;

    @FXML
    private TableColumn<AttendanceRow, String> column6;

    @FXML
    private TableColumn<AttendanceRow, String> column7;

    @FXML
    private TableColumn<AttendanceRow, String> column8;

    @FXML
    private DatePicker getRequestDate;

    @FXML
    public void initialize() {
        column1.setCellValueFactory(cellData -> cellData.getValue().col1Property());
        column2.setCellValueFactory(cellData -> cellData.getValue().col2Property());
        column3.setCellValueFactory(cellData -> cellData.getValue().col3Property());
        column4.setCellValueFactory(cellData -> cellData.getValue().col4Property());
        column5.setCellValueFactory(cellData -> cellData.getValue().col5Property());
        column6.setCellValueFactory(cellData -> cellData.getValue().col6Property());
        column7.setCellValueFactory(cellData -> cellData.getValue().col7Property());
        column8.setCellValueFactory(cellData -> cellData.getValue().col8Property());
    }

    @FXML
    void getMedical() {
        if(getRequestDate.getValue() != null){
            String quary = "SELECT * FROM medical WHERE Request_date = ?";
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = null;
            try{
                preparedStatement = connection.prepareStatement(quary);
                preparedStatement.setDate(1, Date.valueOf(getRequestDate.getValue()));
                ResultSet results = preparedStatement.executeQuery();
                while (results.next()) {
                    MedicalTable.getItems().add(new AttendanceRow(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6),results.getString(7),results.getString(8)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            new AttendanceAdd().alert("Please fill the date","Can't Submit");
            getRequestDate.requestFocus();
        }
    }
}
