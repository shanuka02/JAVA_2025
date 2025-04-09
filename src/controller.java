import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;

public class controller {
    // UI Components
    public BorderPane firstBorderPane;
    public Circle profilePic;
    public ImageView editProfileIcon;
    public ImageView noticesIcon;
    public ImageView manageCourseIcon;
    public ImageView viewStudentIcon;
    public ImageView StudentMarksIcon;
    public ImageView studentAttendenceIcon;
    public ImageView studentMedicalIcon;
    public ImageView studentEligibilityIcon;

    @FXML private StackPane contentPane;
    @FXML private Pane mainContentPane;

    // Buttons
    @FXML private Button noticeButton;
    @FXML private Button editProfileButton;
    @FXML private Button manageCourseButton;
    @FXML private Button studentEligibilityButton;
    @FXML private Button studentMarksButton;
    @FXML private Button studentMedicalButton;
    @FXML private Button studentAttendenceButton;
    @FXML private Button viewStudentButton;
    @FXML private Button backButton;

    // Labels
    @FXML private Label lectureNameLabel;
    @FXML private Label departmentNameLabel;

    // Course Fields
    @FXML private TextField CourseName1;
    @FXML private TextField CourseName2;
    @FXML private TextField CourseName3;
    @FXML private TextField CourseName4;
    @FXML private TextField CourseName5;

    private TextField[] fields;

    @FXML
    public void initialize() {
        // Initialize course fields array
        fields = new TextField[]{CourseName1, CourseName2, CourseName3, CourseName4, CourseName5};


            System.out.println("Checking FXML injection:");
            System.out.println("contentPane: " + contentPane);
            System.out.println("mainContentPane: " + mainContentPane);
            System.out.println("backButton: " + backButton);
            // ... check other important fields


        // Check if backButton exists before using it
        if (backButton != null) {
            backButton.setVisible(false);
        } else {
            System.err.println("Warning: backButton is null - check FXML file");
        }
        // Load course names from database
        loadCourseNames();
    }

    private void loadCourseNames() {
        try {
            Connection conn = dbConnection.getConnection();
            String query = "SELECT courseName FROM courseunit";
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int index = 0;
            while (rs.next() && index < fields.length) {
                if (fields[index] != null) {
                    fields[index].setText(rs.getString("courseName"));
                }
                index++;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Set default values if database fails
            for (int i = 0; i < fields.length; i++) {
                if (fields[i] != null) {
                    fields[i].setText("Course " + (i + 1));
                }
            }
        }
    }

    @FXML
    private void handleMangeCourseButton(javafx.event.ActionEvent actionEvent) {
        try {
            // Load the manage courses view
            Parent manageCoursesView = FXMLLoader.load(getClass().getResource("manageCourses.fxml"));

            // Replace current content with the new view
            contentPane.getChildren().setAll(manageCoursesView);

            // Show back button
            backButton.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() {
        // Return to main content
        contentPane.getChildren().setAll(mainContentPane);

        // Hide back button
        backButton.setVisible(false);
    }

    // Add similar methods for other buttons
    @FXML
    private void handleViewStudentButton() {
        loadView("viewStudents.fxml");
    }

    @FXML
    private void handleStudentMarksButton() {
        loadView("studentMarks.fxml");
    }

    // Generic method to load views
    private void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentPane.getChildren().setAll(view);
            backButton.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}