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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateNotice {
    @FXML
    private Button ChooseFile;

    @FXML
    private TextField Content;

    @FXML
    private TextField Id;

    @FXML
    private RadioButton R1; //When roll is Undergraduate

    @FXML
    private RadioButton R2; //When roll is Technical Officer

    @FXML
    private RadioButton R3; //When roll is Lecture

    @FXML
    private TextArea Title;

    @FXML
    private Button UpdateButton;

    @FXML
    private ToggleGroup roll;

    private  String selectedFilePath;
    mySqlCon connection;

    @FXML
    public void initialize(){


        Id.textProperty().addListener((observable,oldValue,newValue )->{
            //If the new text is not empty, call the function searchByTitle()
            if(!newValue.trim().isEmpty()){
                loadData(newValue.trim());
            }else{
                //if field is empty clear the data
                Title.clear();
                Content.clear();
                roll.selectToggle(R1);

            }
        });

    }

    public int loadData(String ID){
        connection = new mySqlCon();
        Connection con = connection.con();

        //String id = Id.getText().trim();

        String query = "SELECT title ,content, userRoll FROM notice WHERE notice_id = ? ";
        if(ID.isEmpty()){
/*
        JOptionPane.showMessageDialog(null,"please Enter ID");
*/
        return 1;
    }

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,ID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
/*
                JOptionPane.showMessageDialog(null,"No notice Found");
*/




                Title.setText(rs.getString("title"));
                Content.setText(rs.getString("content"));
                String roll = rs.getString("userRoll");

                if (roll.equalsIgnoreCase("Undergraduate")) {
                    //select
                    R1.setSelected(true);
                } else if (roll.equalsIgnoreCase("Technical Officer")) {
                    //select r2
                    R2.setSelected(true);
                } else if (roll.equalsIgnoreCase("Lecture")) {
                    //select r3
                    R3.setSelected(true);
                }
            }
            //how to set value to the radio button
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        return 0;

    }
    @FXML
    void HandleChooseFile(ActionEvent event) {
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
    int HandleUpdate(ActionEvent event) {
        connection = new mySqlCon();
        Connection con = connection.con();

        if(Title.getText().trim().isEmpty() ||Content.getText().trim().isEmpty() || roll.getSelectedToggle() == null ){
            JOptionPane.showMessageDialog(null,"Required  fields must be filed","Error",JOptionPane.ERROR_MESSAGE);
            return 1;
        }

        String title = Title.getText().trim();

        RadioButton selectedRadioButton = (RadioButton)roll.getSelectedToggle();
        String rolls = selectedRadioButton.getText();



        String query = "UPDATE notice SET title = ?,postedDay = ?,content =?,userRoll = ? WHERE notice_id = ?";
        try {


            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,title.toUpperCase());
            pstm.setString(2, String.valueOf(currentDate));
            pstm.setString(3,Content.getText().trim());
            pstm.setString(4,rolls);
            pstm.setString(5,Id.getText().trim());


            int rowAffected = pstm.executeUpdate();

            if(rowAffected > 0){
                JOptionPane.showMessageDialog(null,"Notice Update successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                Id.clear();
                Title.clear();
                Content.clear();
                selectedFilePath = null;
                roll.selectToggle(R1);


            }else{
                JOptionPane.showMessageDialog(null,"Unable to Update","Fail Update",JOptionPane.ERROR_MESSAGE);

            }



        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }


    return 0;

    }


    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageNotice.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
