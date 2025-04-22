import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

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
        presentDate.setValue(null);
        lectureTime.clear();
        lectureHours.clear();
        studentId.clear();
    }

    @FXML
    void submitBtn() {
        if(checkFields()){
//            access to the sql database;
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
                    System.out.println("Student ID is TG" + status.getValue());
//                    code here
                }else {
                    new AttendanceAdd().alert("Please enter the valid ID", "Invalid Student ID");
                    studentId.clear();
                }
            }
        }
    }

    @FXML
    public void select() {
        accessDB("lecture");
    }

//    @FXML
//    public void test() {
//        VBox box = new VBox();
//        box.setSpacing(10); // spacing between fields
//
//        for (int i = 0; i < 5; i++) {
//            TextField textField = new TextField();
//            textField.setPromptText("Enter your " + (i + 1) + " data");
//            box.getChildren().add(textField);
//        }
//
//        items.setContent(box); // Set VBox as the content of the TitledPane
//        System.out.println("Test completed.");
//    }


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
        accessDB("course");
    }

    private void accessDB(String select){
        int count = 0;
        String quary;
        String[] setData;

        if(select.equals("course")){
            setData = new String[20];
            quary = "SELECT courseId FROM courseunit";
        }else{
            setData = new String[2]; //checks course ID
            quary = "SELECT cType FROM courseunit where courseId = + courseId.getValue() ";
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
        if(presentDate.getValue() == null || courseId.getItems().isEmpty() || lectureHours.getText().isEmpty() || lectureTime.getText().isEmpty() || lectureType.getItems().isEmpty()){
            new AttendanceAdd().alert("Please fill all the fields","Can't submit");
            if(courseId.getItems().isEmpty()){
                courseId.requestFocus();
//                presentDate.setStyle("-fx-border-color: red");
//                presentDate.setPromptText("Please select a date");
            }else if(presentDate.getValue() == null){
                presentDate.requestFocus();
            }else if(lectureTime.getText().isEmpty()){
                lectureTime.requestFocus();
            }else if(lectureType.getItems().isEmpty()){
                lectureType.requestFocus();
            }else if(lectureHours.getText().isEmpty()){
                lectureHours.requestFocus();
            }
            return false;
        }else{
            return true;
        }
    }

//    private void getStudentData(){
//        String quary = "SELECT * FROM name";
//        String name = null;
//        Connection connection = null;
//
//        try {
//            connection  = DBConnection.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet results = statement.executeQuery(quary);
//
//            while (results.next()) {
//                name = results.getString(1);
//                System.out.println(name + "\t" + results.getString(2));
//                if(Objects.equals(name, "1")){
//                    System.out.println("Found");
//                }else {
//                    System.out.println("Not Found");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL Error: " + e.getMessage());
//        } finally {
//            try {
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Error closing connection: " + e.getMessage());
//            }
//        }
//    }
}