import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LectureController {

     private TextField[] courseFields;

    @FXML private Button manageCourseButton;
    @FXML private Button studentMarksButton;

    @FXML private TextField CourseName1,CourseName2,CourseName3,CourseName4,CourseName5;
    private String[] names;


    @FXML
    public void initialize() {
        courseFields = new TextField[]{CourseName1, CourseName2, CourseName3, CourseName4, CourseName5};
    }


    public void handleManageCourseButton(ActionEvent actionEvent) {
        Connection conn = dbConnection.getConnection();

        if(conn != null){
            System.out.println("database connected successfully");

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT courseName from courseUnit");

                String [] courseNames = new String[5];
                int index =0;
                while (rs.next() && index < courseFields.length){

                        String name = rs.getString("courseName");
                        System.out.println("course name "+ name);
                        courseNames[index] = name;

                    index++;
                }
                rs.close();
                stmt.close();
                conn.close();

                //load managecourse.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manageCourses.fxml"));
                //Parent root = null;
                try {
                    Parent root = loader.load();

                    ManageCoursesController controller = loader.getController();
                    controller.setCourseNames(courseNames);
                    Stage stage = (Stage) manageCourseButton.getScene().getWindow(); // get current stage
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void handleAddMarks(ActionEvent event){
        Connection conn = dbConnection.getConnection();

        if(conn != null){
            System.out.println("database connected successfully");

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT courseId from courseUnit");

                String [] courseNames = new String[5];
                int index =0;
                while (rs.next() && index < courseFields.length){

                    String name = rs.getString("courseId");
                    System.out.println("course name "+ name);
                    courseNames[index] = name;

                    index++;
                }
                rs.close();
                stmt.close();
                conn.close();

                //load managecourse.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courseSelect.fxml"));
                //Parent root = null;
                try {
                    Parent root = loader.load();

                    courseSelectController controller = loader.getController();
                    controller.setCourseNames(courseNames);
                    Stage stage = (Stage) studentMarksButton.getScene().getWindow(); // get current stage
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}