package TechnicalOfficer;

import Admin.ApplicationDrive;
import Admin.mySqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
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

    @FXML
    public void initialize() {
        noticeNumber.setCellValueFactory(new PropertyValueFactory<>("noticeId"));
        noticeDate.setCellValueFactory(new PropertyValueFactory<>("postedDay"));
        noticeTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        addPreviewButton();

        loadNotices();
    }

    private void loadNotices() {
        String query = "SELECT * FROM notice WHERE userRoll = 'Technical Officer' ";
        try {
            Connection con = mySqlCon.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                notices.add(new modelNotice(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getDate("postedDay").toString(),
                        rs.getString("content")
                ));
            }

            noticeTable.setItems(notices);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addPreviewButton() {
        previewButton.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Preview");

            {
                btn.setOnAction(e -> {
                    modelNotice notice = getTableView().getItems().get(getIndex());
                    String filePath = ((modelNotice) notice).getContent();
                    File file = new File(filePath);

                    if (file.exists()) {
                        try  {
                            Desktop.getDesktop().open(file);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "File not found.");
                        alert.show();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    public void backToPage(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("technicalResource/fxml/TechnicalOfficer.fxml"));
        Parent root = null;
        try{
           root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Error_code: x0000e1.2 " + e.getMessage());
        }
    }
}