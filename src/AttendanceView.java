import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class AttendanceView {
    @FXML
    private TextField setCourseId;

    @FXML
    private TextField setLectureHours;

    @FXML
    private TextField setLectureType;

    @FXML
    private TextField setPresentData;

    @FXML
    private TextField setPresentTime;

    @FXML
    private TextField setStatus;

    @FXML
    private TextField setStudentId;

    @FXML
    private TextField attendanceId;

    @FXML
    private ComboBox<String> courseId;

    @FXML
    private ComboBox<String> lectureType;

    @FXML
    private DatePicker preDate;

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
    private TableView<AttendanceRow> table;

    @FXML
    public void submitBtn() throws SQLException {
        if(checkFields()) {
            table.getItems().clear();
            getAttendance();
        }else {
            new AttendanceAdd().alert("Please fill all the fields","Can't submit");
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

        accessDB("course", "SELECT courseId FROM courseunit");
    }

    @FXML
    public void backToPage() throws IOException {
        new TechnicalMain().Go();
    }

    @FXML
    public void getAttendance() throws SQLException {
        String quary = "SELECT * FROM attendance WHERE Att_cou_id = ? AND Pre_date = ? AND Lec_type = ?";
        try {
            Connection connection = DBConnection.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(quary);
            preparedStatement.setString(1, courseId.getValue());
            preparedStatement.setDate(2, Date.valueOf(preDate.getValue()));
            preparedStatement.setString(3, lectureType.getValue());
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                table.getItems().add(new AttendanceRow(results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7),results.getString(8)));
            }
            if(table.getItems().isEmpty()){
                new AttendanceAdd().alert("No attendance found","No attendance found");
                table.getItems().clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setAttendance() throws SQLException {
        String quary = "SELECT * FROM attendance where Att_id = ?";
        try {
            Connection connection = DBConnection.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(quary);
            preparedStatement.setString(1, "1");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void select(){
        lectureType.getItems().clear();
        accessDB("lecture","SELECT cType FROM courseunit where courseId ='"+courseId.getValue()+"'");
    }

    private void accessDB(String select,String quary){
        int count = 0;
        String[] setData;

        if(select.equals("course")){
            setData = new String[5];
        }else{
            setData = new String[1];
        }
        try {
            Connection connection  = DBConnection.getConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(quary);

            while (results.next()) {
                setData[count] = results.getString(1);
                count++;
            }
            if(select.equals("course")){
                courseId.getItems().addAll(setData);
            }else {
                if(setData[0].equals("Both") || setData[0].equals("both")){
                    lectureType.getItems().addAll("Theory","Practical");
                }else{
                    lectureType.getItems().addAll(setData);
                }
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    @FXML
    public void checkAttendance() throws SQLException {
        clear();
        if(!attendanceId.getText().isEmpty()){
            int AttId = Integer.parseInt(attendanceId.getText());
            if(AttId > 0){
                try {
                    Connection connection = DBConnection.getConnection();
                    assert connection != null;
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM attendance WHERE Att_id = ?");
                    preparedStatement.setInt(1, AttId);
                    ResultSet results = preparedStatement.executeQuery();
                    if(results.next()){
//                        attendanceId.setText(results.getString(1));
//                        courseId.setValue(results.getString(2));
//                        lectureType.setValue(results.getString(3));
//                        preDate.setValue(results.getDate(4).toLocalDate());
//                        column1.setText("Student ID");
//                        column2.setText("Course ID");
//                        column3.setText("Lecture Type");
//                        if(results.isBeforeFirst() && results.isLast() && results.getRow() == 1){
//
//                        }
                        if(results.getString(1).isEmpty()){
                            new AttendanceAdd().alert("Attendance ID not found","Can't submit");
                        }
                        setStudentId.setText(results.getString(2));
                        setCourseId.setText(results.getString(3));
                        setPresentData.setText(results.getDate(4).toLocalDate().toString());
                        setPresentTime.setText(results.getTime(5).toString());
                        setLectureHours.setText(results.getString(6));
                        setLectureType.setText(results.getString(7));
                        setStatus.setText(results.getString(8));
                    }
                }catch (SQLException e){

                }
            }else {
                new AttendanceAdd().alert("Please Enter valid ID","Can't submit");
                attendanceId.clear();
                attendanceId.requestFocus();
            }
        }else{
            new AttendanceAdd().alert("Please fill the attendance ID","Can't submit");
            attendanceId.requestFocus();
        }
    }

    private void clear(){
        setCourseId.clear();
        setLectureHours.clear();
        setLectureType.clear();
        setPresentData.clear();
        setPresentTime.clear();
        setStatus.clear();
        setStudentId.clear();
    }

    private boolean checkFields(){
        if(courseId.getValue() == null || preDate.getValue() == null || lectureType.getValue() == null){
            if(courseId.getValue() == null){
                courseId.requestFocus();
            }else if(preDate.getValue() == null){
                preDate.requestFocus();
            }else if(lectureType.getValue() == null){
                lectureType.requestFocus();
            }
            return false;
        }else {
            return true;
        }
    }
}
