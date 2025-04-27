package StudentPackage;

import Admin.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;

public class StudentMedical {

    @FXML
    private TextField studentNameField;

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField departmentField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button uploadButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button uploadProfilePicButton;

    @FXML
    private Label uploadStatusLabel;

    private File medicalFile;
    private File profilePicFile;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleUploadAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        medicalFile = fileChooser.showOpenDialog(stage);

        if (medicalFile != null) {
            System.out.println("Selected medical file: " + medicalFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleUploadProfilePicAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        Stage stage = (Stage) uploadProfilePicButton.getScene().getWindow();
        profilePicFile = fileChooser.showOpenDialog(stage);

        if (profilePicFile != null) {
            uploadStatusLabel.setText("Profile picture selected: " + profilePicFile.getName());
        } else {
            uploadStatusLabel.setText("No file selected.");
        }
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) {
        String studentName = studentNameField.getText();
        String studentId = studentIdField.getText();
        String department = departmentField.getText();
        java.sql.Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
        java.sql.Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());

        if (medicalFile != null && profilePicFile != null) {
            String medicalFilePath = "/resources/studentRole/Medical_Files/" + medicalFile.getName();
            String profilePicPath = "/resources/studentRole/Profile_Pics/" + profilePicFile.getName();

            uploadMedicalFile(medicalFile, medicalFilePath);
            uploadProfilePicture(profilePicFile, profilePicPath);

            insertMedicalData(studentId, studentName, department, startDate, endDate, medicalFilePath, profilePicPath);
        } else {
            showAlert("Error", "Please ensure all fields are filled and files are uploaded.");
        }
    }

    private void uploadMedicalFile(File file, String filePath) {
    }

    private void uploadProfilePicture(File file, String filePath) {
    }

    private void insertMedicalData(String studentId, String studentName, String department,
                                   java.sql.Date startDate, java.sql.Date endDate,
                                   String medicalFilePath, String profilePicPath) {
        String query = "INSERT INTO StudentMedical (StudentId, StudentName, Department, StartDate, EndDate, MedicalFilePath, ProfilePicPath) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = mySqlCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(studentId));
            statement.setString(2, studentName);
            statement.setString(3, department);
            statement.setDate(4, startDate);
            statement.setDate(5, endDate);
            statement.setString(6, medicalFilePath);
            statement.setString(7, profilePicPath);
            statement.executeUpdate();


            showAlert("Success", "Medical data submitted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to submit medical data.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
