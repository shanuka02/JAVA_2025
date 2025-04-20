import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.lang.classfile.Label;
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
    public void checkData(ActionEvent event) {
        String name = "Hellow";
//        setData.setText();
    }

    @FXML
    void setValues(ActionEvent event) {

    }

//    @FXML
//    void submitBtn() {
//        new AttendanceAdd().getData();
//        if(lable1.getText().isEmpty() || lable2.getText().isEmpty()){
//            new AttendanceAdd().alert("hellow","hii");
//        }else {
//            System.out.println("Fill all the fields" + lable1.getText() + lable2.getText());
//        }
//    }

    @FXML
    void initialize() {
        assert courseId != null : "fx:id=\"courseId\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureHours != null : "fx:id=\"lectureHours\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureTime != null : "fx:id=\"lectureTime\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lectureType != null : "fx:id=\"lectureType\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert presentDate != null : "fx:id=\"presentDate\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";

    }

//    private void alert(String title, String message){
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText("Fill all the fields");
//        alert.setContentText(message);
//        alert.showAndWait();
//    }

    public void getData(){
        String quary = "SELECT * FROM name";
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