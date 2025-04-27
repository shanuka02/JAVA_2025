package TechnicalOfficer;

import Admin.ApplicationDrive;
import Admin.mySqlCon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class AttendanceMedical {
    @FXML
    private ComboBox<String> getCourseId;

    @FXML
    private ComboBox<String> getLectureType;

    @FXML
    private ComboBox<String> getStatus;

    @FXML
    private TextField  getStudentId;

    @FXML
    private DatePicker getRequestDateId;

    @FXML
    private TextArea getResonId;

    @FXML
    void addMedical() {
        if(checkFields()){
            if(getStudentId.getText().isEmpty()){
                new AttendanceAdd().alert("Please fill the student ID","Can't submit");
                getStudentId.requestFocus();
            }else{
                String student = getStudentId.getText().toUpperCase();
                if(student.contains("TG") && student.length() == 6){
                    String quary = "INSERT INTO medical (Me_stu_id, Me_cou_id, Lec_type, Reason, Request_date, Status_, Submitted_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    Date sqlDate = Date.valueOf(LocalDate.now());

                    try {
                        Connection connection = mySqlCon.getConnection();
                        assert connection != null;
                        PreparedStatement preparedStatement = connection.prepareStatement(quary);
                        preparedStatement.setString(1, student);
                        preparedStatement.setString(2, getCourseId.getValue());
                        preparedStatement.setString(3, getLectureType.getValue());
                        preparedStatement.setString(4, getResonId.getText());
                        preparedStatement.setDate(5, Date.valueOf(getRequestDateId.getValue()));
                        preparedStatement.setString(6, getStatus.getValue());
                        preparedStatement.setDate(7,sqlDate);

                        int rowsInserted = preparedStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            new AttendanceAdd().alert("Attendance added successfully", "Success");
                        }
                    }catch (SQLException e){
                        System.out.println("Error adding attendance " + e.getMessage());
                    }
                }else {
                    new AttendanceAdd().alert("Please enter the valid ID", "Invalid Student ID");
                    getStudentId.clear();
                    getStudentId.requestFocus();
                }
            }
        }
    }

    @FXML
    public void initialize() {
        getStatus.getItems().addAll("Approved","Rejected","Pending");
        accessDB("course", "SELECT courseId FROM courseunit");
    }

    @FXML
    public void editMedical(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/MedicalEdit.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAttendance(){
        String quary1 = "SELECT Medi_id, Me_stu_id, Me_cou_id, Lec_type, Request_date FROM Medical WHERE Status_ = 'approved'";
        String quary2 = "SELECT Att_id, Att_stu_id, Att_cou_id, Lec_type, Pre_date FROM attendance WHERE status_ = 'Absent' AND Att_medi_id IS NULL";

        Connection connection = mySqlCon.getConnection();
        try {
            assert connection != null;
            Statement statement1 = connection.createStatement();
            ResultSet results1 = statement1.executeQuery(quary1);

            Statement statement2 = connection.createStatement();
            ResultSet results2 = statement2.executeQuery(quary2);
            while (results1.next()) {
                while (results2.next()){
                    if (results2.getString(2).equals(results1.getString(2)) && results2.getString(3).equals(results1.getString(3)) && results2.getString(4).equals(results1.getString(4)) && results2.getString(5).equals(results1.getString(5))){
                        saveAttendance(parseInt(results1.getString(1)), parseInt(results2.getString(1)), connection);
                        break;
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error updating attendance " + e.getMessage());
        }
    }

    private void saveAttendance(int medical_id, int attendance_id, Connection connection) throws SQLException {
        String quary = "UPDATE attendance SET Status_ = ?, Att_medi_id = ? WHERE Att_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(quary);
            preparedStatement.setString(1,"Medical");
            preparedStatement.setInt(2, medical_id);
            preparedStatement.setInt(3, attendance_id);
            int result= preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Attendance updated");
            }
        }catch (SQLException e){
            System.out.println("Error updating attendance " + e.getMessage());
        }
    }

    public void selectCourseId(){
        accessDB("lecture","SELECT cType FROM courseunit where courseId ='"+getCourseId.getValue()+"'");
    }

    private void accessDB(String select,String quary){
        int count = 0;
        String[] setData;

        if(select.equals("course")){
            setData = new String[7];
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
            getLectureType.getItems().clear();
            if(select.equals("course")){
                getCourseId.getItems().addAll(setData);
            }else {
                if(setData[0].equals("Both") || setData[0].equals("both")){
                    getLectureType.getItems().addAll("Theory","Practical");
                }else{
                    getLectureType.getItems().addAll(setData);
                }
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private boolean checkFields(){
        if(getStudentId.getText().isEmpty() || getRequestDateId.getValue() == null || getResonId.getText().isEmpty() || getCourseId.getValue() == null || getLectureType.getValue() == null){
            new AttendanceAdd().alert("Please fill all the fields","Can't submit");
            if(getStudentId.getText().isEmpty()){
                getStudentId.requestFocus();
            }else if(getCourseId.getValue() == null){
                getCourseId.requestFocus();
            }else if(getLectureType.getValue() == null){
                getLectureType.requestFocus();
            }else if(getStatus.getValue() == null){
                getStatus.requestFocus();
            }else if(getRequestDateId.getValue() == null){
                getRequestDateId.requestFocus();
            }else if(getResonId.getText().isEmpty()){
                getResonId.requestFocus();
            }else if(getResonId.getText() == null){
                getResonId.requestFocus();
            }
            return false;
        }else{
            return true;
        }
    }

    public void backToPage() {
        new TechnicalMain().mainLoader();
    }
}
