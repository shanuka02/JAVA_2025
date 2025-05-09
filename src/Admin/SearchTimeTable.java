package Admin;

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

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchTimeTable {

    @FXML
    private TableColumn<TimetableModel, String> Caption;

    @FXML
    private TableColumn<TimetableModel, String> Content;

    @FXML
    private TableColumn<TimetableModel, String> Date;

    @FXML
    private TableColumn<TimetableModel, String> DepName;

    @FXML
    private TableColumn<TimetableModel, String> ID;

    @FXML
    private TableView<TimetableModel> Table1;
    mySqlCon connection;

    @FXML
    public void initialize(){
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Content.setCellValueFactory(new PropertyValueFactory<>("content"));
        Date.setCellValueFactory(new PropertyValueFactory<>("submiteddate"));
        DepName.setCellValueFactory(new PropertyValueFactory<>("depname"));
        Caption  .setCellValueFactory(new PropertyValueFactory<>("caption"));

        loadData();
        addPreviewButton();
    }

    private void loadData() {
        connection = new mySqlCon();
        Connection con = connection.con();

        ObservableList<TimetableModel> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM timeTable";

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                TimetableModel timetable = new TimetableModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)

                );
                data.add(timetable);
            }
            Table1.setItems(data);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }


    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLfiles/ManageTimetable.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void addPreviewButton() {
        Content.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Preview");

            {
                btn.setOnAction(e -> {
                    TimetableModel timetable = getTableView().getItems().get(getIndex());
                    String filePath = timetable.getContent();

                    System.out.println("filepath"+filePath);

                    File file = new File(filePath);

                    if (file.exists()) {
                        try {
                            Desktop.getDesktop().open(file);
                        } catch (IOException ex) {
                           JOptionPane.showMessageDialog(null," "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "File not found.");
                        alert.show();
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }
}
