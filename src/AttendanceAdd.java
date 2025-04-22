import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class AttendanceAdd implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> courseId;

    @FXML
    private TextField lectureHours;

    @FXML
    private TextField lectureTime;

    @FXML
    private ComboBox<String> lectureType;

    @FXML
    private DatePicker presentDate;

    @FXML
    public Label setData;

    @FXML
    private Button addAttendance;

    @FXML
    private ComboBox<String> status;

    @FXML
    private TextField studentId;

//    @FXML
//    public void checkBtn() {
//        String name = "Hellow";
//        setData.setText(name);
//        new AttendanceAdd().getStudentData();
//       close.setVisible(false);
//        submitId.setDisable(true);
//    }

    @FXML
    void clearBtn() {
        courseId.setValue(null);
        presentDate.setValue(null);
        lectureTime.clear();
        lectureType.setValue(null);
        lectureHours.clear();
        studentId.clear();
    }

    @FXML
    void submitBtn() {
        if(checkFields()){
            System.out.println(presentDate.getValue());
        }
    }

    @FXML
    void addAttendanceBtn() throws SQLException {
        if(checkFields()){
            if(studentId.getText().isEmpty()){
                new AttendanceAdd().alert("Please fill the student ID","Can't submit");
                studentId.requestFocus();
            }else{
                String student = studentId.getText().toUpperCase();
                if(student.contains("TG") && student.length() == 6){
                    System.out.println("Student ID is TG" + status.getValue());
//                    set the data for database
                    String quary = "INSERT INTO attendance (Att_stu_id, Att_cou_id, Pre_date, Pre_time, Lec_hours, Lec_type, Status_) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    Connection connection = DBConnection.getConnection();
                    assert connection != null;
                    PreparedStatement preparedStatement = connection.prepareStatement(quary);
                    preparedStatement.setString(1, student);
                    preparedStatement.setString(2, courseId.getValue());
                    preparedStatement.setDate(3, Date.valueOf(presentDate.getValue()));
                    preparedStatement.setTime(4, Time.valueOf(lectureTime.getText()));
                    preparedStatement.setInt(5, Integer.parseInt(lectureHours.getText()));
                    preparedStatement.setString(6, lectureType.getValue());
                    preparedStatement.setString(7, status.getValue());

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        new AttendanceAdd().alert("Attendance added successfully", "Success");
                    }
                }else {
                    new AttendanceAdd().alert("Please enter the valid ID", "Invalid Student ID");
                    studentId.clear();
                }
            }
        }
    }

    @FXML
    public void select() {
        lectureType.getItems().clear();
        accessDB("lecture","SELECT cType FROM courseunit where courseId ='"+courseId.getValue()+"'");
    }

    @FXML
    void initialize() {
        assert addAttendance != null : "fx:id=\"addAttendance\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert courseId != null : "fx:id=\"courseId\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureHours != null : "fx:id=\"lectureHours\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureTime != null : "fx:id=\"lectureTime\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureType != null : "fx:id=\"lectureType\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert presentDate != null : "fx:id=\"presentDate\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert setData != null : "fx:id=\"setData\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert studentId != null : "fx:id=\"studentId\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.getItems().addAll("Present", "Absent", "Medical");
        status.setValue("Present");
        accessDB("course", "SELECT courseId FROM courseunit");
    }

    private void accessDB(String select,String quary){
        int count = 0;
        String[] setData;

        if(select.equals("course")){
            setData = new String[20];
        }else{
            setData = new String[2];
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
                lectureType.getItems().addAll(setData);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private void alert(String message, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean checkFields(){
        if(presentDate.getValue() == null || courseId.getValue() == null || lectureHours.getText().isEmpty() || lectureTime.getText().isEmpty() || lectureType.getItems().isEmpty()){
            new AttendanceAdd().alert("Please fill all the fields","Can't submit");
            if(courseId.getValue() == null){
                courseId.requestFocus();
//                presentDate.setStyle("-fx-border-color: red");
//                presentDate.setPromptText("Please select a date");
            }else if(presentDate.getValue() == null){
                presentDate.requestFocus();
            }else if(lectureTime.getText().isEmpty()){
                lectureTime.requestFocus();
            }else if(lectureType.getValue() == null){
                lectureType.requestFocus();
            }else if(lectureHours.getText().isEmpty()){
                lectureHours.requestFocus();
            }
            return false;
        }else{
            return true;
        }
    }
}