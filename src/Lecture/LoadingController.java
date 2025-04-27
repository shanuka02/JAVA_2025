package Lecture;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LoadingController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    @FXML
    private ImageView loadingImage;

    private double progress = 0;

    public void initialize() {
        // Set initial progress
        progressBar.setProgress(0);
        progressLabel.setText("0%");

        // Create a Timeline to simulate loading progress
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    // Initially, no progress
                    progress = 0;
                }),
                new KeyFrame(Duration.seconds(1), e -> {
                    // Update progress and label
                    progress += 0.1;
                    progressBar.setProgress(progress);
                    progressLabel.setText(String.format("%.0f%%", progress * 100));
                }),
                new KeyFrame(Duration.seconds(10), e -> {
                    // Finalize loading process (e.g., open main application)
                    progressBar.setProgress(1);
                    progressLabel.setText("100%");
                    // You can now load the main application screen or window
                    // You can also transition to the main screen here if needed
                    // Example: loadMainScreen();
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE); // Infinite cycle
        timeline.play();
    }

    // You can add a method to load the main screen here when loading is complete
    // private void loadMainScreen() {
    //     // Load the main screen (e.g., using FXMLLoader)
    // }
}
