import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateTimeTable {

    @FXML
    private RadioButton BST;

    @FXML
    private TextArea Caption;

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
    private Button UpdateButton;

    @FXML
    private Button chooseFileButton;

    mySqlCon connection;

    String selectedFilePath;


    @FXML
    public void initialize(){

        ID.textProperty().addListener((observable,oldValue,newValue )->{
            //If the new text is not empty, call the function searchByTitle()
            if(!newValue.trim().isEmpty()){
                loadData(newValue.trim());
            }else{
                //if field is empty clear the data

                Caption.clear();
                Content.clear();
                Dep.selectToggle(ICT);

            }
        });

    }

    public int loadData(String TimetableID){
        connection = new mySqlCon();
        Connection con = connection.con();

        String id = ID.getText().trim();

        String query = "SELECT  * FROM  timetable  WHERE timeTable_id  = ? ";

        if(id.isEmpty()){
            //JOptionPane.showMessageDialog(null,"please Enter ID");
            return 1;
        }

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,TimetableID);
            ResultSet rs = pstm.executeQuery();

            /*if (!rs.next()) {
                JOptionPane.showMessageDialog(null,"No notice Found");
                return 1;
            }*/

            if(rs.next()){
                Caption.setText(rs.getString("caption"));
                Content.setText(rs.getString("content"));
                String dep = rs.getString("depName");

                if(dep.equalsIgnoreCase("ICT")){
                    //select
                    ICT.setSelected(true);
                }else if(dep.equalsIgnoreCase("BST")){
                    //select r2
                    BST.setSelected(true);
                }else if(dep.equalsIgnoreCase("ET")){
                    //select r3
                    ET.setSelected(true);
                }

            }else{
                Caption.clear();
                Content.clear();
                Dep.selectToggle(ICT);

            }

            //how to set value to the radio button
        } catch (SQLException e) {
            System.out.println("Error"+ e.getMessage());
        }
        return 0;

    }


    @FXML
    int HandleUpdate(ActionEvent event) {
        connection = new mySqlCon();
        Connection con = connection.con();


        if(ID.getText().trim().isEmpty() || Caption.getText().trim().isEmpty() || selectedFilePath == null || Dep.getSelectedToggle() == null ){
            JOptionPane.showMessageDialog(null,"All Field are Required","Error",JOptionPane.ERROR_MESSAGE);
            return 1;

        }


        String id = ID.getText().trim();
        String caption = Caption.getText().trim();
        RadioButton selectedRadioButton = (RadioButton)Dep.getSelectedToggle();
        String dep = selectedRadioButton.getText();


        String query = "UPDATE timeTable SET  caption = ?,submittedDate = ?,content =?, depName =? WHERE  timeTable_id = ?";
        try {
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,caption.toUpperCase());
            pstm.setString(2, String.valueOf(currentDate));
            pstm.setString(3,selectedFilePath);
            pstm.setString(4,dep);
            pstm.setString(5,ID.getText().trim());


            int rowAffected = pstm.executeUpdate();

            if(rowAffected > 0){
                JOptionPane.showMessageDialog(null,"Timetable add successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                ID.clear();
                Caption.clear();
                Content.clear();
                selectedFilePath = null;
                Dep.selectToggle(ICT);


            }else{
                JOptionPane.showMessageDialog(null,"Unable to Update","Error",JOptionPane.ERROR_MESSAGE);

            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  0;

    }

    @FXML
    void HandlechooseFile(ActionEvent event) {
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

    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageTimetable.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
