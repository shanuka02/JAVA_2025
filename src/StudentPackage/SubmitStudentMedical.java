package StudentPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubmitStudentMedical {

    @FXML
    private TextField studentName;

    @FXML
    private TextField tgNumber;

    @FXML
    private TextField courseCode;

    @FXML
    private TextField medicalDate;

    @FXML
    private ImageView medicalImageView;

    private File selectedImageFile;

    @FXML
    void backtopage(ActionEvent event) {
        TechmisApp back = new TechmisApp();
        back.start(TechmisApp.getPrimaryStage());
    }

    @FXML
    private void handleImageUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            selectedImageFile = file;
            Image image = new Image(file.toURI().toString());
            medicalImageView.setImage(image);
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String name = studentName.getText();
        String tg = tgNumber.getText();
        String course = courseCode.getText();
        String date = medicalDate.getText();

        if (selectedImageFile == null) {
            System.out.println("No image selected");
            return;
        }

        if (!isValidCourseCode(course)) {
            System.out.println("Invalid course code");
            return;
        }

        try {
            Connection connection = DBConnection.getConnection();
            String sql = "INSERT INTO student_medical (student_name, tg_number, course_code, medical_date, medical_image_path) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, tg);
            statement.setString(3, course);
            statement.setString(4, date);
            statement.setString(5, selectedImageFile.getAbsolutePath());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medical record submitted successfully!");
            } else {
                System.out.println("Failed to submit the record.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidCourseCode(String courseCode) {
        String[] validCourseCodes = {"ICT2113", "ICT2122", "ICT2133", "ICT2142", "ICT2152"};

        for (String code : validCourseCodes) {
            if (code.equals(courseCode)) {
                return true;
            }
        }
        return false;
    }
}
