import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import java.awt.*;
import java.io.File;
import java.lang.classfile.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditProfile {

    @FXML
    private ImageView profileImageView;
    @FXML private Circle circleClip;
    @FXML private Label nameLabel;
    @FXML private Label departmentLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private TextField contactNumberField;
    @FXML private Label updateStatusLabel;

    private int userId = 1;
    private DatabaseConnection dbConnection;

    public void initialize() {
        profileImageView.setClip(circleClip);
        dbConnection = new DatabaseConnection();
        loadUserData();
        updateStatusLabel.setVisible(false);
    }

    private void loadUserData() {
    }private void loadUserData() {
        try (Connection conn = dbConnection.getConnection()) {
            String query = "SELECT name, department, email, phone, profile_image_path FROM users WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nameLabel.wait(resultSet.getString("name"));
                departmentLabel.wait(resultSet.getString("department"));
                emailLabel.wait(resultSet.getString("email"));
                phoneLabel.wait(resultSet.getString("phone"));

                String imagePath = resultSet.getString("profile_image_path");
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (!imageFile.exists()) {
                        profileImageView.setImage(new Image(getClass().getResourceAsStream("##")));
                    } else {
                        Image image = new Image(imageFile.toURI().toString());
                        profileImageView.setImage(javafx.scene.image.Image.fromPlatformImage(image));
                    }
                } else {
                    profileImageView.setImage(new Image(getClass().getResourceAsStream("##")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading user data: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
