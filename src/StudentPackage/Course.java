package StudentPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Desktop;

public class Course {

    @FXML
    private ComboBox<String> drop1;

    @FXML
    private TextField materialField;

    @FXML
    private Button backButton;

    @FXML
    private Button downloadButton;

    private final ObservableList<String> subjects = FXCollections.observableArrayList(
            "ICT001 - Programming",
            "ICT002 - Networking",
            "ICT003 - Database",
            "ICT004 - Web Design",
            "ICT005 - Operating Systems"
    );

    @FXML
    public void initialize() {
        drop1.setItems(subjects);
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        TechmisApp back = new TechmisApp();
        back.start(TechmisApp.getPrimaryStage());
    }

    @FXML
    private void handleDownloadButton(ActionEvent event) {
        String selectedSubject = drop1.getValue();
        if (selectedSubject != null && !selectedSubject.isEmpty()) {
            downloadMaterialFromDatabase(selectedSubject);
        } else {
            materialField.setText("Please select a course first");
        }
    }

    private void downloadMaterialFromDatabase(String selectedSubject) {
        try {
            String subjectCode = selectedSubject.split(" - ")[0];
            String filePath = getFilePathFromDatabase(subjectCode);

            if (filePath != null && !filePath.trim().isEmpty()) {
                File file = new File(filePath);
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                    materialField.setText("Opening file: " + filePath);
                } else {
                    materialField.setText("File not found: " + filePath);
                }
            } else {
                materialField.setText("File path is empty or invalid");
            }
        } catch (SQLException | IOException e) {
            materialField.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getFilePathFromDatabase(String subjectCode) throws SQLException {
        String filePath = null;
        String query = "SELECT content FROM notice WHERE title = 2"; // Assuming the 'notice' table stores the material for the course

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, subjectCode);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                filePath = rs.getString("content"); // Assuming 'content' is the column with the file path
            }
        }
        return filePath;
    }
}
