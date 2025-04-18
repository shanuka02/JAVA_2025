import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class uploadLectureMaterialController {
    @FXML private Label coLecId;
    @FXML
    private TextField TitleForLecMaterial;
    @FXML
    private TextArea FilePathLecMaterial;
    @FXML
    private Button UploadButton;
    @FXML
    private TableView<modelLectureMaterials> ViewLecMaterialTableView;
    @FXML
    private TableColumn<modelLectureMaterials, Integer> number;

    @FXML
    private TableColumn<modelLectureMaterials, String> DateUpload;

    @FXML
    private TableColumn<modelLectureMaterials, String> TitleLecMaterial;

    @FXML
    private TableColumn<modelLectureMaterials, Void> DeleteButtonColumn;

    private ObservableList<modelLectureMaterials> materials = FXCollections.observableArrayList();

private String corseIdLec;



    public void setCourseLabel(String clabel) {
        coLecId.setText(clabel);
       corseIdLec = clabel;
        loadLecMaterial();

    }

    @FXML public void initialize(){



        number.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLectureMaterialId()).asObject());
        DateUpload.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        TitleLecMaterial.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        DeleteButtonColumn.setCellFactory(param->new TableCell<>(){
            private final Button deleteBtn = new Button("Delete");
            {
                deleteBtn.setOnAction(event -> {
                    modelLectureMaterials material = getTableView().getItems().get(getIndex());
                    deleteMaterial(material.getLectureMaterialId());
                    materials.remove(material);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
        ViewLecMaterialTableView.setItems(materials);

    }

    @FXML
    private void handleUpload() {
        Connection conn = dbConnection.getConnection();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select Lecture Material");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Word Documents", "*.docx"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(UploadButton.getScene().getWindow());

        if (selectedFile != null) {
            FilePathLecMaterial.setText(selectedFile.getAbsolutePath());

            String title = TitleForLecMaterial.getText();
            String filepath = selectedFile.getAbsolutePath();

            if (conn != null) {
                System.out.println("database connected successfully");

                try {

                    String insertQuery = "INSERT INTO LectureMaterials (TitleLecMaterial, Filepath, CourseId) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(insertQuery);

                    stmt.setString(1, title);
                    stmt.setString(2, filepath);
                    stmt.setString(3, corseIdLec);
                    stmt.executeUpdate();

                    showAlert(Alert.AlertType.INFORMATION, "Lecture Material Uploaded Successfully.");
                    TitleForLecMaterial.clear();
                    FilePathLecMaterial.clear();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadLecMaterial() {
        Connection conn = dbConnection.getConnection();

        if (conn != null) {
            System.out.println("database connected successfully");

            try {

                String query = "SELECT LectureMaterialId, TitleLecMaterial, Date, Filepath FROM LectureMaterials WHERE CourseId = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, corseIdLec);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("LectureMaterialId");
                    String title = rs.getString("TitleLecMaterial");
                    String date = rs.getString("Date");
                    String filePath = rs.getString("Filepath");

                    materials.add(new modelLectureMaterials(id,date,title,filePath));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


    }

    private void deleteMaterial(int id) {
        try (Connection conn = dbConnection.getConnection()) {
            String query = "DELETE FROM LectureMaterials WHERE LectureMaterialId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}