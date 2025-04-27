package StudentPackage;
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
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProfileController extends BaseController {

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

  //  private String userId;

    public void initialize() {
       // userId = 1;
        loadUserProfile();

  }


    private void loadUserProfile() {
        String query = "SELECT user_name, depName, profilePic FROM useraccount WHERE user_id = 'TG1389' AND roll = 'Undergraduate'";

        try (Connection conn = DBConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("user_name");
                    String department = rs.getString("depName");
                    String imagePath = rs.getString("profilePic");

                    nameLabel.setText(name);
                    departmentLabel.setText(department);

                    if (imagePath != null && !imagePath.isEmpty()) {
                        Image image = new Image("file:" + imagePath);
                        profileImage.setImage(image);
                        System.out.println("Profile Image Loaded Successfully from " + imagePath);
                    } else {
                        System.out.println("Default Image Loaded");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading user profile: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        System.out.println("Logout button clicked");

    }

    @FXML
    private void handleEditProfile(ActionEvent event) throws IOException {
            handleEditprofile();

    }

    @FXML
    private void handleNotifications(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/Notice.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }

    @FXML
    private void handleCourses(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/Course.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);

    }

    @FXML
    void handleAttendance() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/StudentAttendenceView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);


    }

    @FXML
    private void handleNotice(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/Notice.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);

    }

    @FXML
    private void handleTimeTable() {
        String query = "SELECT content FROM notice WHERE notice_id = 1000";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String contentPath = rs.getString("content");

                if (contentPath != null && !contentPath.isEmpty()) {
                    loadTimeTable(contentPath);
                } else {
                    System.out.println("No Content Found");
                }
            } else {
                System.out.println("No Content Found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading timetable list: " + e.getMessage());
        }
    }

    private void loadTimeTable(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading timetable list: " + e.getMessage());
            }
        } else {
            System.out.println("No File Found");
        }
    }


    @FXML
    private void handlemedical(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/SubmitStudentMedical.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);

    }

    @FXML
    private void handleGrade(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/StudentGrades.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }


    @Override
    public void loadProfileData() {

    }
}