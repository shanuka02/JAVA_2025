import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
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

    @FXML
    private ToggleGroup dep;

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

        String id = ID.getText().trim();
        String caption = Caption.getText().trim();

        RadioButton selectedRadioButton = (RadioButton)dep.getSelectedToggle();
        String deps = selectedRadioButton.getText();

        if(id.isEmpty() || caption.isEmpty() || selectedFilePath.isEmpty() ){
            JOptionPane.showMessageDialog(null,"All Field are Required","Error",JOptionPane.INFORMATION_MESSAGE);
            return 1;

        }

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
                JOptionPane.showMessageDialog(null,"Notice add successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                ID.clear();
                Content.clear();
                Caption.clear();
                selectedFilePath = null;
                dep.selectToggle(null);


            }else{
                JOptionPane.showMessageDialog(null,"Unable to Update","Fail Update",JOptionPane.ERROR_MESSAGE);

            }
        } catch (SQLException e) {
            System.out.println("Error");
        }

        return 0;

    }

    public void BackbuttonHandle(ActionEvent actionEvent) {
    }
}
