import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class AttendanceAdd {

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
    private Button submitId;

    @FXML
    public void checkBtn() {
//        String name = "Hellow";
//        setData.setText(name);
//        new AttendanceAdd().getStudentData();
//        close.setVisible(false);
        submitId.setDisable(true);
    }

    @FXML
    void clearBtn() {
        courseId.setText("");
        presentDate.setValue(null);
        lectureTime.setText("");
        lectureHours.setText("");
        lectureType.setText("");
    }

    @FXML
    void submitBtn() {
        if(checkFields()){
//            access to the sql database;
            System.out.println(presentDate.getValue());
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
        assert courseId != null : "fx:id=\"courseId\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureHours != null : "fx:id=\"lectureHours\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureTime != null : "fx:id=\"lectureTime\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureType != null : "fx:id=\"lectureType\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert presentDate != null : "fx:id=\"presentDate\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";

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