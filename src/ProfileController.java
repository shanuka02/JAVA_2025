import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileController {

    @FXML
    private ImageView profileImage;


    @FXML
    private ComboBox<String> drop1;

    @FXML
    private Label nameLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button notificationButton;

    private int userId;

    public void initialize() {
        userId = 1;
        loadUserProfile();

    }


    private void loadUserProfile() {
        String query = "SELECT name, department, profile_image FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String department = rs.getString("department");
                    byte[] imageData = rs.getBytes("profile_image");

                    nameLabel.setText(name);
                    departmentLabel.setText(department);

                    if (imageData != null) {
                        Image image = new Image(new ByteArrayInputStream(imageData));
                        profileImage.setImage(image);
                    } else {

                        profileImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading user profile: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDashboard(ActionEvent event) {
        System.out.println("Dashboard button clicked");

    }

    @FXML
    private void handleLogout(ActionEvent event) {
        System.out.println("Logout button clicked");

    }

    @FXML
    private void handleFAQ(ActionEvent event) {
        System.out.println("FAQ button clicked");

    }

    @FXML
    private void handleAboutUs(ActionEvent event) {
        System.out.println("About Us button clicked");

    }

    @FXML
    private void handleEditProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Editprofile.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);

    }

    @FXML
    private void handleNotifications(ActionEvent event) {
        System.out.println("Notifications button clicked");

    }

    @FXML
    private void handleCourses(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Course.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);

    }

    @FXML
    void handleAttendance() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/StudentAttendenceView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);


    }

    @FXML
    private void handleNotice(ActionEvent event) {
        System.out.println("Notice button clicked");

    }

    @FXML
    private void handleTimeTable() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TimeTable.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }

    @FXML
    private void handleUser(ActionEvent event) {
        System.out.println("User button clicked");

    }

    @FXML
    private void handleGrade(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/StudentGrades.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }


}