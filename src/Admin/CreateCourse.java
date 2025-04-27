package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateCourse {

    @FXML
    private Button AddButton;

    @FXML
    private TextField Assesment;

    @FXML
    private TextField CA;

    @FXML
    private TextField Code;

    @FXML
    private TextField Credit;

    @FXML
    private ToggleGroup GPAvalue;

    @FXML
    private RadioButton GPAV;

    @FXML
    private ComboBox<String> Lecture;

    @FXML
    private TextField Name;

    @FXML
    private RadioButton P;

    @FXML
    private TextField Quize;

    @FXML
    private RadioButton T;

    @FXML
    private RadioButton TP;

    @FXML
    private ToggleGroup Type;

    mySqlCon connection;

    @FXML
    public void initialize() {

        ObservableList<String> list1 = FXCollections.observableArrayList();
        connection = new mySqlCon();
        Connection con = connection.con();

        String query = "SELECT user_id FROM useraccount WHERE roll = 'lecture'";
        try {
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                list1.add(rs.getString("user_id"));

            }
            Lecture.setItems(list1);
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());
        }
    }

        @FXML
    int HnadleAddButton(ActionEvent event) {

        connection = new mySqlCon();
        Connection con = connection.con();


        //show error message when fields are empty
        if(Code.getText().trim().isEmpty() || Name.getText().trim().isEmpty() || Credit.getText().trim().isEmpty()  ||
                Lecture.getSelectionModel().isEmpty() || CA.getText().trim().isEmpty() ||
                Type.getSelectedToggle() == null || GPAvalue.getSelectedToggle() == null){
                JOptionPane.showMessageDialog(null,"Required  fields must be filed","Error",JOptionPane.ERROR_MESSAGE);
                return 1;
        }



        //get data
        String coursecode = Code.getText().trim();
        String name = Name.getText().trim();
        String Lecid = Lecture.getValue();

        int credit;
        int CApersentage;

        try{
          credit = Integer.parseInt(Credit.getText().trim()); //parse to int
            CApersentage = Integer.parseInt(CA.getText().trim());

        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Credit and CA percentage must be valid numbers");
            return 1;
        }

         RadioButton selectedRadio1 = (RadioButton)Type.getSelectedToggle();
         String cType = selectedRadio1.getText();


         RadioButton selectedRadio2 = (RadioButton)GPAvalue.getSelectedToggle();
         String GPAstate = selectedRadio2.getText();




         int quizes = 0;
         int assesment = 0;




         if(!Quize.getText().trim().isEmpty()  ){
             try{
                 quizes = Integer.parseInt(Quize.getText().trim());

             }catch(NumberFormatException e){
                 JOptionPane.showMessageDialog(null,"Number of quiz's  must be a valid numbers","Error",JOptionPane.ERROR_MESSAGE);
                 return  1;
             }
         }


            if(!Assesment.getText().trim().isEmpty()  ){
                try{
                    assesment = Integer.parseInt(Assesment.getText().trim());

                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Number of Assessment's  must be a valid numbers","Error",JOptionPane.ERROR_MESSAGE);
                    return  1;
                }
            }
         //validation for data range
         if(quizes < 0 || quizes > 5){
             JOptionPane.showMessageDialog(null,"Invalid number of quizzes (0-5 only)","Error",JOptionPane.ERROR_MESSAGE);
             return 1;
         }
         if (assesment < 0 || assesment > 5) {
             JOptionPane.showMessageDialog(null,"Invalid number of Assessments (0-5 only)","Error",JOptionPane.ERROR_MESSAGE);
             return 1;
         }
         if(CApersentage < 0 || CApersentage > 50 ){
             JOptionPane.showMessageDialog(null,"CA Percentsge must be between 0 and 50","Error",JOptionPane.ERROR_MESSAGE);
             return 1;
         }

            String query2 = "Insert INTO courseUnit(courseId ,courseName,credit ,cType ,nuOfQuises , nuOfAssesments ,ca_percentage ,lectureIncharge, gpa_state)VALUES (?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement pstm = con.prepareStatement(query2);
                pstm.setString(1,coursecode);
                pstm.setString(2,name);
                pstm.setInt(3, credit);
                pstm.setString(4,cType);
                pstm.setInt(5, quizes);
                pstm.setInt(6, assesment);
                pstm.setInt(7,CApersentage);
                pstm.setString(8,Lecid);
                pstm.setString(9,GPAstate);

                int rowAffected = pstm.executeUpdate();

                if(rowAffected > 0){
                    JOptionPane.showMessageDialog(null,"Course add successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                    Assesment.clear();
                    Quize.clear();
                    CA.clear();
                   Lecture.getSelectionModel().clearSelection();
                    GPAvalue.selectToggle(GPAV);
                    Type.selectToggle(T);
                    Code.clear();
                    Name.clear();
                    Credit.clear();



                }else{
                    JOptionPane.showMessageDialog(null,"Unable to Create","Error",JOptionPane.ERROR_MESSAGE);

                }

            } catch (SQLException e ) {
                JOptionPane.showMessageDialog(null," "+ e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

            }
        return 0;

        }

    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLfiles/ManageCourse.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
