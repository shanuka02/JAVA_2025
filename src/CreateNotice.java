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
import java.sql.SQLException;


public class CreateNotice {
    @FXML
    private Button ChooseFileButton;

    @FXML
    private TextField Content;

    @FXML
    private Button CreateButton;

    @FXML
    private TextArea Noticetitle;

    @FXML
    private RadioButton RadioLecture;

    @FXML
    private RadioButton RadioTechnical;

    @FXML
    private RadioButton RadioUndergraduate;

    @FXML
    private ToggleGroup roll;

    private String selectedFilePath;

    mySqlCon connection;

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
    int HandleCreateNotice(ActionEvent event) {
        connection = new mySqlCon();
        Connection con = connection.con();

        if(Noticetitle.getText().trim().isEmpty() ||selectedFilePath == null || roll.getSelectedToggle() == null ){
            JOptionPane.showMessageDialog(null,"Required  fields must be filed","Error",JOptionPane.ERROR_MESSAGE);
            return 1;
        }

        String title = Noticetitle.getText().trim();

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
                Noticetitle.clear();
                Content.clear();
                selectedFilePath = null;
                roll.selectToggle(RadioUndergraduate);


            }else{
                JOptionPane.showMessageDialog(null,"Unable to Add" ,"Error",JOptionPane.ERROR_MESSAGE);

            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());;
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
