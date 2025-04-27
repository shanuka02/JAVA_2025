package StudentPackage;

import Admin.*;
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

public class Notice {

    @FXML
    private TableView<modelNotice> noticeTable;

    @FXML
    private TableColumn<modelNotice, Integer> noticeNumber;

    @FXML
    private TableColumn<modelNotice, String> noticeDate;

    @FXML
    private TableColumn<modelNotice, String> noticeTitle;

    @FXML
    private TableColumn<modelNotice, Void> previewButton;

    ObservableList<modelNotice> notices = FXCollections.observableArrayList();

    public void initialize() {
        noticeNumber.setCellValueFactory(new PropertyValueFactory<>("noticeId"));
        noticeDate.setCellValueFactory(new PropertyValueFactory<>("postedDay"));
        noticeTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        addPreviewButton();
        loadNotices();
    }

    private void loadNotices() {
        String query = "SELECT * FROM notice WHERE userRoll = 'Undergraduate'";
        try {
            Connection connection = mySqlCon.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("notice_id");
                String title = rs.getString("title");
                String date = rs.getDate("postedDay").toString();
                String contentPath = rs.getString("content");

                String fixedPath = fixFilePath(contentPath);

                modelNotice notice = new modelNotice(id, title, date, fixedPath);
                notices.add(notice);
            }

            noticeTable.setItems(notices);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String fixFilePath(String path) {
        String basePath = "/resources/studentRole/Notice_Materials";
        if (!path.startsWith("")) {
            path = basePath + path;
        }
        return path;
    }

    private void addPreviewButton() {
        previewButton.setCellFactory(col -> new TableCell<modelNotice, Void>() {
            private final Button btn = new Button("Preview");

            {
                btn.setOnAction(event -> {
                    modelNotice notice = getTableView().getItems().get(getIndex());
                    String filePath = notice.getContent();


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
