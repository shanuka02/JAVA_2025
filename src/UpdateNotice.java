import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public void initializer(){
        loadData();

    }

    public int loadData(){
        connection = new mySqlCon();
        Connection con = connection.con();

        String id = Id.getText().trim();

        String query = "SELECT notice_id,title ,content, userRoll FROM notice WHERE notice_id = ? ";
        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null,"please Enter ID");
            return 1;
        }

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,id);
            ResultSet rs = pstm.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null,"No notice Found");
                return 1;
            }


            Title.setText(rs.getString("title"));
            Content.setText(rs.getString("content"));
            String roll = rs.getString("userRoll");

            if(roll.equalsIgnoreCase("undergraduate")){
                //select
                R1.setSelected(true);
            }else if(roll.equalsIgnoreCase("Technical Officer")){
                //select r2
                R2.setSelected(true);
            }else if(roll.equalsIgnoreCase("Lecture")){
                //select r3
                R3.setSelected(true);
            }

            //how to set value to the radio button
        } catch (SQLException e) {
            System.out.println("Error"+ e.getMessage());
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
    void HandleUpdate(ActionEvent event) {
        connection = new mySqlCon();
        Connection con = connection.con();

        String title = Title.getText().trim();

        RadioButton selectedRadioButton = (RadioButton)roll.getSelectedToggle();
        String rolls = selectedRadioButton.getText();


        String query = "INSERT INTO notice(title,postedDay,content,userRoll ) VALUES (?,?,?,?)";
        try {
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,title.toUpperCase());
            pstm.setString(2, String.valueOf(currentDate));
            pstm.setString(3,selectedFilePath);
            pstm.setString(4,rolls);


            int rowAffected = pstm.executeUpdate();

            if(rowAffected > 0){
                JOptionPane.showMessageDialog(null,"Notice add successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                Id.clear();
                Title.clear();
                Content.clear();
                selectedFilePath = null;


            }else{
                JOptionPane.showMessageDialog(null,"Unable to Update","Fail Update",JOptionPane.ERROR_MESSAGE);

            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }




    }

}
