package TechnicalOfficer;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;

import static java.lang.String.valueOf;

public class AttendanceAdd  {
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
//        new TechnicalOfficer.AttendanceAdd().getStudentData();
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
    void addAttendanceBtn() {
        if(checkFields()){
            if(studentId.getText().isEmpty()){
                new AttendanceAdd().alert("Please fill the student ID","Can't submit");
                studentId.requestFocus();
            }else{
                String student = studentId.getText().toUpperCase();
                if(student.contains("TG") && student.length() == 6){
//                    System.out.println("Student ID is TG" + status.getValue());
                    String quary = "INSERT INTO attendance (Att_stu_id, Att_cou_id, Pre_date, Pre_time, Lec_hours, Lec_type, Status_) VALUES (?, ?, ?, ?, ?, ?, ?)";

                    try {
                        Connection connection = mySqlCon.getConnection();
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
                            studentId.clear();
                        }
                    }catch (SQLException e){
                        System.out.println("Error adding attendance " + e.getMessage());
                    }
                }else {
                    new AttendanceAdd().alert("Please enter the valid ID", "Invalid Student ID");
                    studentId.clear();
                }
            }
        }
    }

    @FXML
    void select() {
        lectureType.getItems().clear();
        accessDB("lecture","SELECT cType FROM courseunit where courseId ='"+courseId.getValue()+"'");
    }

    @FXML
    void initialize() {
        assert addAttendance != null : "fx:id=\"addAttendance\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";
        assert courseId != null : "fx:id=\"courseId\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";
        assert lectureHours != null : "fx:id=\"lectureHours\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";
        assert lectureTime != null : "fx:id=\"lectureTime\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";
        assert lectureType != null : "fx:id=\"lectureType\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";
        assert presentDate != null : "fx:id=\"presentDate\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";
        assert setData != null : "fx:id=\"setData\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";
        assert studentId != null : "fx:id=\"studentId\" was not injected: check your FXML file 'TechnicalOfficer.AttendanceAdd.fxml'.";

        status.getItems().addAll("Present", "Absent", "Medical");
        status.setValue("Present");
        accessDB("course", "SELECT courseId FROM courseunit");
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
            Connection connection  = mySqlCon.getConnection();
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

    void alert(String message, String header){
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

    public void backToPage() {
        new TechnicalMain().mainLoader();
    }
}