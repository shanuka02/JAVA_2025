import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateTimetable {
    @FXML
    private Button ADDbutton;

    @FXML
    private RadioButton BST;

    @FXML
    private TextArea Caption;

    @FXML
    private Button ChooseButtton;

    @FXML
    private TextField Content;

    @FXML
    private ToggleGroup Dep;

    @FXML
    private RadioButton ET;

    @FXML
    private RadioButton ICT;

    @FXML
    private TextField ID;



    String selectedFilePath;
    mySqlCon connection;

    @FXML
    void HandleChooseButton(ActionEvent event) {
        FileChooser fileChooser= new FileChooser();

        fileChooser.setTitle("Select File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files","*"),
                new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg","*.gif"),
                new FileChooser.ExtensionFilter("PDF FILES","*.pdf")
        );

        Button sourceButton =(Button) event.getSource();
        Stage stage =(Stage) sourceButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!= null){
            selectedFilePath = selectedFile.getAbsolutePath();
            Content.setText(selectedFilePath);

        }


    }

    @FXML
    int handleAdd(ActionEvent event) {

        connection = new mySqlCon();
        Connection con = connection.con();

        if(ID.getText().trim().isEmpty() || Caption.getText().trim().isEmpty() || selectedFilePath == null || Dep.getSelectedToggle() == null ){
            JOptionPane.showMessageDialog(null,"All Field are Required","Error",JOptionPane.ERROR_MESSAGE);
            return 1;

        }


        String id = ID.getText().trim();
        String caption = Caption.getText().trim();

        RadioButton selectedRadioButton = (RadioButton)Dep.getSelectedToggle();
        String deps = selectedRadioButton.getText();


        String query = "INSERT INTO timeTable(timeTable_id,caption,submittedDate, content,depName) VALUES (?,?,?,?,?)";

        try {
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,id);
            pstm.setString(2,caption.toUpperCase());
            pstm.setString(3,String.valueOf(currentDate));
            pstm.setString(4,selectedFilePath);
            pstm.setString(5,deps);
            int rowAffected = pstm.executeUpdate();

            if(rowAffected > 0){
                JOptionPane.showMessageDialog(null,"Timetable add successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                ID.clear();
                Content.clear();
                Caption.clear();
                selectedFilePath = null;
                Dep.selectToggle(ICT);


            }else{
                JOptionPane.showMessageDialog(null,"Unable to Add","Error",JOptionPane.ERROR_MESSAGE);

            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }

        return 0;

    }

    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageTimetable.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
