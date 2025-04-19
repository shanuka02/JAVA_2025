import java.net.URL;
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
}