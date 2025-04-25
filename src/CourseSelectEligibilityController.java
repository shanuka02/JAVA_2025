import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;




    public class CourseSelectEligibilityController {

        @FXML
        private TextField CourseName1, CourseName2, CourseName3, CourseName4, CourseName5;
        @FXML private TextField[] fields;

        @FXML
        public void initialize(){
            fields = new TextField[]{CourseName1,CourseName2,CourseName3,CourseName4,CourseName5};
        }


        public void setCourseNames(String[] names){
            TextField[] fields = new TextField[]{CourseName1, CourseName2, CourseName3, CourseName4, CourseName5};
            for (int i = 0; i < names.length && i < fields.length; i++) {
                if (fields[i] != null) {
                    fields[i].setText(names[i]);
                }
            }
        }


        public void handleCourseButton(ActionEvent event){
            Button clickedButton = (Button) event.getSource();
            String buttonId = clickedButton.getId();
            int index = 0;

            switch (buttonId){
                case "course1Button":
                    index = 0;
                    break;

                case "course2Button":
                    index = 1;
                    break;

                case "course3Button":
                    index = 2;
                    break;

                case "course4Button":
                    index = 3;
                    break;
                case "course5Button":
                    index = 4;
                    break;
            }

            String updateCoLable = fields[index].getText();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/EligibilityDetails.fxml"));
                Parent root = loader.load();

                EligibilityDetailsController controller = loader.getController();
                controller.setCourseLabel(updateCoLable);

                Stage stage  = (Stage) clickedButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void handleHome(ActionEvent event) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                ApplicationDrive.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }




