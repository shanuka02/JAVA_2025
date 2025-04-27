package StudentPackage;

import  Admin.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course extends BaseController {

    @FXML
    private TableView<modelCourse> courseTable;

    @FXML
    private TableColumn<modelCourse, Integer> materialId;

    @FXML
    private TableColumn<modelCourse, String> materialTitle;

    @FXML
    private TableColumn<modelCourse, String> materialUploadDate;

    @FXML
    private TableColumn<modelCourse, String> courseId;

    @FXML
    private TableColumn<modelCourse, Void> previewButton;

    ObservableList<modelCourse> courses = FXCollections.observableArrayList();

    public void initialize() {
        materialId.setCellValueFactory(new PropertyValueFactory<>("lectureMaterialId"));
        materialTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        materialUploadDate.setCellValueFactory(new PropertyValueFactory<>("uploadDate"));
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        addPreviewButton();
        loadCourses();
    }

    private void loadCourses() {
        String query = "SELECT * FROM lectureMaterials";
        try {
            Connection connection = mySqlCon.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("LectureMaterialId");
                String title = rs.getString("Title");
                String date = rs.getTimestamp("UploadDate").toString();
                String filePath = rs.getString("FilePath");
                String course = rs.getString("CourseId");

                String fixedPath = fixFilePath(filePath);

                modelCourse courseMaterial = new modelCourse(id, title, date, fixedPath, course);
                courses.add(courseMaterial);
            }

            courseTable.setItems(courses);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String fixFilePath(String path) {
        String basePath = "/resources/studentRole/Lecture_Materials";
        if (!path.startsWith("")) {
            path = basePath + path;
        }
        return path;
    }

    private void addPreviewButton() {
        previewButton.setCellFactory(col -> new TableCell<modelCourse, Void>() {
            private final Button btn = new Button("Preview");

            {
                btn.setOnAction(event -> {
                    modelCourse material = getTableView().getItems().get(getIndex());
                    String filePath = material.getFilePath();

                    if (filePath != null && !filePath.trim().isEmpty()) {
                        File file = new File(filePath);
                        if (file.exists()) {
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            System.out.println("File not found: " + filePath);
                        }
                    } else {
                        System.out.println("Invalid file path.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    @FXML
    private void backtopage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("studentRole/view/MainView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (Exception e) {
            System.out.println("error: "+e.getMessage());
        }

    }
}
