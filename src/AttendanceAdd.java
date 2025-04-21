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
    private TextField courseId;

    @FXML
    private TextField lectureHours;

    @FXML
    private TextField lectureTime;

    @FXML
    private TextField lectureType;

    @FXML
    private DatePicker presentDate;

    @FXML
    public Label setData;

    @FXML
    private Button addAttendance;

    @FXML
    private ComboBox<String> status;

    @FXML
    private ComboBox<String> nameId;

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
        courseId.clear();
        presentDate.setValue(null);
        lectureTime.clear();
        lectureHours.clear();
        lectureType.clear();
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
        String[] add = {"ICT1233","ICT1223"};
        status.getItems().addAll("Present", "Absent", "Medical");
        status.setValue("Present");
        nameId.getItems().addAll(add);

//
        String quary = "SELECT courseId FROM CourseUnit";
        String name = null;
        Connection connection = null;

        try {
            connection  = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(quary);

            while (results.next()) {
                name = results.getString(1);
                System.out.println(name + "\t" + results.getString(2));
                if(Objects.equals(name, "1")){
                    System.out.println("Found");
                }else {
                    System.out.println("Not Found");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
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
        if(presentDate.getValue() == null || courseId.getText().isEmpty() || lectureHours.getText().isEmpty() || lectureTime.getText().isEmpty() || lectureType.getText().isEmpty()){
            new AttendanceAdd().alert("Please fill all the fields","Can't submit");
            if(courseId.getText().isEmpty()){
                courseId.requestFocus();
//                presentDate.setStyle("-fx-border-color: red");
//                presentDate.setPromptText("Please select a date");
            }else if(presentDate.getValue() == null){
                presentDate.requestFocus();
            }else if(lectureTime.getText().isEmpty()){
                lectureTime.requestFocus();
            }else if(lectureType.getText().isEmpty()){
                lectureType.requestFocus();
            }else if(lectureHours.getText().isEmpty()){
                lectureHours.requestFocus();
            }
            return false;
        }else{
            return true;
        }
    }

    private void getStudentData(){
        String quary = "SELECT * FROM name";
        String name = null;
        Connection connection = null;

        try {
            connection  = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(quary);

            while (results.next()) {
                name = results.getString(1);
                System.out.println(name + "\t" + results.getString(2));
                if(Objects.equals(name, "1")){
                    System.out.println("Found");
                }else {
                    System.out.println("Not Found");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}