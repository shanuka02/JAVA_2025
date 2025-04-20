import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public void checkBtn(ActionEvent event) {
        String name = "Hellow";
        setData.setText(name);
    }

    @FXML
    void submitBtn(ActionEvent event) {
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
        }else{
            System.out.println(presentDate.getValue() + "\t" + courseId.getText() + "\t" + lectureHours.getText() + "\t" + lectureTime.getText() + "\t" + lectureType.getText());
//            lectureTime.setText("");
        }
    }

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

    public void getStudentData(){
        String quary = "SELECT * FROM student";
        Connection connection = null;

        try {
            connection  = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(quary);

            while (results.next()) {
                System.out.println(results.getString(1) + "\t" + results.getString(2)) ;
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