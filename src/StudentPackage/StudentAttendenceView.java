package StudentPackage;

import Admin.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class StudentAttendenceView {
    @FXML
    private void handleBackButton(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/MainView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        ApplicationDrive.getPrimaryStage().setScene(scene);

    }

    public void ProgramView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/ProgramView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ApplicationDrive.getPrimaryStage().setScene(scene);

    }
    public void NetworkingView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/NetworkingView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ApplicationDrive.getPrimaryStage().setScene(scene);
    }
    public void DatabaseView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/DatabaseView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ApplicationDrive.getPrimaryStage().setScene(scene);
    }
    public void WebDesignView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/WebDesignView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ApplicationDrive.getPrimaryStage().setScene(scene);
    }
    public void OperatingSystemView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/OperatingSystemView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ApplicationDrive.getPrimaryStage().setScene(scene);
    }



}
