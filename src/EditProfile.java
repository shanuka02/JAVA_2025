import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import java.awt.*;
import java.lang.classfile.Label;

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
    }
