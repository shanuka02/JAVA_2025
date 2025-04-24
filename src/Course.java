import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
            downloadMaterial(selectedSubject);
        } else {
            materialField.setText("Please select a course first");
        }
    }

    private void downloadMaterial(String selectedSubject) {
        try {
            String subjectCode = selectedSubject.split(" - ")[0];
            String fileUrl =" /*url*/ "+ subjectCode + ".pdf";
            String fileName = subjectCode + "_material.pdf";

            HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
            connection.setRequestMethod("GET");


            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                materialField.setText("Download failed: Server returned code " + responseCode);
                return;
            }

            InputStream inputStream = connection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(fileName);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
            materialField.setText("Downloaded: " + fileName);
        } catch (IOException e) {
            materialField.setText("Download failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}