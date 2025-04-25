import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class StudentAttendenceView {
    @FXML
    private void handleBackButton(ActionEvent event) {
        TechmisApp back = new TechmisApp();
        back.start(TechmisApp.getPrimaryStage());
    }

    public void ProgramView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/ProgramView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }
    public void NetworkingView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/NetworkingView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }
    public void DatabaseView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/DatabaseView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }
    public void WebDesignView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/WebDesignView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }
    public void OperatingSystemView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/OperatingSystemView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TechmisApp.getPrimaryStage().setScene(scene);
    }



}
