package TechnicalOfficer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.awt.*;
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
    private TextField medicalId;

    @FXML
    private ComboBox<String> getCourseId;

    @FXML
    private ComboBox<String> getLectureType;

    @FXML
    private TextArea getReason;

    @FXML
    private DatePicker getRequestDateId;

    @FXML
    private ComboBox<String> getStatus;

    @FXML
    private TextField getStudentId;

    private String studentID;

    @FXML
    void checkMedical() {
        if(!medicalId.getText().isEmpty()){
            if(Integer.parseInt(medicalId.getText()) > 0) {
                accessDB("medical", "SELECT * FROM medical WHERE Medi_id = ?");
            }else{
                new AttendanceAdd().alert("Please enter the valid ID","Invalid Medical ID");
                medicalId.clear();
                medicalId.requestFocus();
            }
        }else{
            new AttendanceAdd().alert("Please fill the medical ID","Can't Submit");
            medicalId.requestFocus();
        }
    }

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

        getStatus.getItems().addAll("approved","rejected","pending");
        accessData("course", "SELECT courseId FROM courseunit");
//        accessData("lecture","SELECT cType FROM courseunit where courseId ='"+getCourseId.getValue()+"'");
        getLectureType.getItems().addAll("Theory","Practical");
    }

    @FXML
    public void selectCourseId(){
//        getCourseId.setValue(null);
        accessData("lecture","SELECT cType FROM courseunit where courseId ='"+getCourseId.getValue()+"'");
    }

    @FXML
    void getMedical() {
        if(getRequestDate.getValue() != null){
            accessDB("view", "SELECT * FROM medical WHERE Request_date = ?");
        }else{
            new AttendanceAdd().alert("Please fill the date","Can't Submit");
            getRequestDate.requestFocus();
        }
    }

    private void accessDB(String select,String quary){
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try{
            assert connection != null;
            preparedStatement = connection.prepareStatement(quary);
            if(select.equals("view")) {
                preparedStatement.setDate(1, Date.valueOf(getRequestDate.getValue()));
            }else {
                preparedStatement.setInt(1, Integer.parseInt(medicalId.getText()));
            }
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                if(select.equals("view")) {
                    if(results.getString(1).isEmpty()){
                        new AttendanceAdd().alert("Medical date have not found", "Not found");
                        medicalId.requestFocus();
                    }
                    MedicalTable.getItems().add(new AttendanceRow(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6),results.getString(7),results.getString(8)));
                }else{
                    if(results.getString(1).isEmpty()){
                        new AttendanceAdd().alert("Medical ID not found","Not found");
                        medicalId.requestFocus();
                    }
                    getStudentId.setText(results.getString(2));
                    getCourseId.setValue(results.getString(3));
                    getLectureType.setValue(results.getString(4));
                    getReason.setText(results.getString(5));
                    getRequestDateId.setValue(results.getDate(6).toLocalDate());
                    getStatus.setValue(results.getString(7));
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void accessData(String select,String quary){
        int count = 0;
        String[] setData;

        if(select.equals("course")){
            setData = new String[5];
        }else{
            setData = new String[1];
        }
        try {
            Connection connection  = mySqlCon.getConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(quary);

            while (results.next()) {
                setData[count] = results.getString(1);
                count++;
            }
            try {
                if (select.equals("course")) {
                    getCourseId.getItems().addAll(setData);
                } else {
                    getLectureType.getItems().clear();
                    if (setData[0].equals("Both") || setData[0].equals("both")) {
                        getLectureType.getItems().clear();
                        getLectureType.getItems().addAll("Theory", "Practical");
                    } else {
                        getLectureType.getItems().addAll(setData);
                    }
                }
            }catch (Exception e){}
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    @FXML
    private void updateMedical(){
        if(checkFields()){
            String quary = "UPDATE medical SET Me_stu_id = ?, Me_cou_id = ?, Lec_type = ?, Reason = ?, Request_date = ?, Status_ = ? WHERE  Medi_id = ?";
            try {
                Connection connection = mySqlCon.getConnection();
                assert connection != null;
                PreparedStatement preparedStatement = connection.prepareStatement(quary);
                preparedStatement.setString(1, studentID);
                preparedStatement.setString(2, getCourseId.getValue());
                preparedStatement.setString(3, getLectureType.getValue());
                preparedStatement.setString(4,getReason.getText());
                preparedStatement.setDate(5, Date.valueOf(getRequestDateId.getValue().toString()));
                preparedStatement.setString(6, getStatus.getValue());
                preparedStatement.setInt(7, Integer.parseInt(medicalId.getText()));
                preparedStatement.executeUpdate();
                if(preparedStatement.executeUpdate() > 0){
                    new AttendanceAdd().alert("Attendance updated successfully","Success");
                    clear();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void backToPage(){
        new TechnicalMain().accessToAddAttendance();
    }

    public boolean checkFields(){
        if(getCourseId.getItems() == null || getStudentId.getText().isEmpty() || getRequestDateId.getValue() == null || getReason.getText().isEmpty() || getCourseId.getValue() == null || getLectureType.getValue() == null){
            if(getStudentId.getText().isEmpty()){
                getStudentId.requestFocus();
            }else if(getCourseId.getValue() == null){
                getCourseId.requestFocus();
            }else if(getLectureType.getValue() == null){
                getLectureType.requestFocus();
            }else if(getReason.getText().isEmpty()){
                getReason.requestFocus();
            }else if(getRequestDateId.getValue() == null){
                getRequestDateId.requestFocus();
            }else if(getStatus.getValue() == null){
                getStatus.requestFocus();
            }
            new AttendanceAdd().alert("Please fill all the fields","Can't Update");
            return false;
        }else{
            studentID = getStudentId.getText().toUpperCase();
            if(studentID.contains("TG") && studentID.length() == 6 ) {
                return true;
            }else{
                return false;
            }
        }
    }

    private void clear(){
        medicalId.clear();
        getCourseId.setValue(null);
        getLectureType.setValue(null);
        getReason.clear();
        getRequestDateId.setValue(null);
        getStatus.setValue(null);
        getStudentId.clear();
    }
}
