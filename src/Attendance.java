import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Attendance {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField lable1;

    @FXML
    private TextField lable2;

    @FXML
    void submitBtn() {
        new Attendance().getData();
        if(lable1.getText().isEmpty() || lable2.getText().isEmpty()){
            new Attendance().alert("hellow","hii");
//            System.out.println("Please fill all the fields");
        }else {
            System.out.println("Fill all the fields" + lable1.getText() + lable2.getText());
        }
    }

    @FXML
    void initialize() {
        assert lable1 != null : "fx:id=\"lable1\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
        assert lable2 != null : "fx:id=\"lable2\" was not injected: check your FXML file 'AttendanceAdd.fxml'.";
    }

    private void alert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Fill all the fields");
        alert.setContentText(message);
        alert.showAndWait();
    }

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