package Lecture;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;


import java.io.IOException;

public class MainPanelController{
    @FXML
    private  BorderPane mainPanel1;

    @FXML
    private Button log;


    public void handleLogClick(javafx.event.ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/loginform.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}