package Lecture;

import Admin.ApplicationDrive;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class updateMarksController {
    @FXML
    private Label updateCLable;
    @FXML
    private TextField EnterTgNumberQ,EnterTgNumberAs,EnterTgNumberEnd,EnterTgNumberMid, updateQ1, updateQ2, updateQ3, updateQ4;
    @FXML
    private Button updateQuizButton;
    @FXML
    private TextField updateAss1, updateAss2;
    @FXML
    private Button updateAssButton;
    @FXML TextField updateMid,updateEndTheory,updateEndpra;
    @FXML Button updateMidButton;
    @FXML Button updateEndButton;

    private String tgNumber, updateCoLable;
    private double q1, q2, q3, q4;
    private double as1,as2;


    public void setCourseLabel(String updateCoLable) {
        updateCLable.setText(updateCoLable);
        this.updateCoLable = updateCoLable;

    }

    @FXML
    public int handleUpdateQuiz(ActionEvent event) {
        Connection conn = mySqlCon.getConnection();

        if(EnterTgNumberQ.getText().trim().isEmpty() || updateQ1.getText().trim().isEmpty() ||  updateQ2.getText().trim().isEmpty() || updateQ3.getText().trim().isEmpty() || updateQ4.getText().trim().isEmpty()  ){
            JOptionPane.showMessageDialog(null," All fields are requireded");
            return 1;
        }

        tgNumber = EnterTgNumberQ.getText();
        Double q1 = parseMark(updateQ1.getText());
        Double q2 = parseMark(updateQ2.getText());
        Double q3 = parseMark(updateQ3.getText());
        Double q4 = parseMark(updateQ4.getText());

        //if (anyInvalidMark(q1, q2, q3, q4)) return;
        if(q1 < 0 || q1 > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }
        if(q2 < 0 || q2 > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }
        if(q3 < 0 || q3 > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }


        if(q4 < 0 || q4 > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }


        if (conn != null) {
            System.out.println("database connected successfully");

            try {

                String updateQuery = "UPDATE caMarks SET q1_mark = ?, q2_mark = ?, q3_mark = ?, q4_mark = ? WHERE studentId = ? AND courseId = ?";
                PreparedStatement stmt = conn.prepareStatement(updateQuery);

                setDoubleOrNull(stmt, 1, q1);
                setDoubleOrNull(stmt, 2, q2);
                setDoubleOrNull(stmt, 3, q3);
                setDoubleOrNull(stmt, 4, q4);
                stmt.setString(5, tgNumber);
                stmt.setString(6, updateCoLable);



                int rowsAffected = stmt.executeUpdate();

                System.out.println("TG Number: " + tgNumber);
                System.out.println("Course ID: " + updateCoLable);

                if (rowsAffected > 0) {
                    System.out.println("Update successful!");
                } else {
                    System.out.println("No rows updated. Check studentId and courseId.");
                }

                stmt.close();
                conn.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return  0;
    }

    private Double parseMark(String text) {
        if (text == null || text.trim().isEmpty() || text.equalsIgnoreCase("absent")) {
            return null;
        }
        return Double.parseDouble(text); // safely parse the number
    }


    private void setDoubleOrNull(PreparedStatement stmt, int index, Double value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, java.sql.Types.DOUBLE);
        } else {
            stmt.setDouble(index, value);
        }
    }

    @FXML
    public int  handleUpdateAses (ActionEvent event) {
        Connection conn = mySqlCon.getConnection();

        if(EnterTgNumberAs.getText().trim().isEmpty() || updateAss1.getText().trim().isEmpty() || updateAss2.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null," All fields are required");
            return 1;
        }
        tgNumber = EnterTgNumberAs.getText();
        Double as1 = parseMark(updateAss1.getText());
        Double as2 = parseMark(updateAss2.getText());

//        if (anyInvalidMark(as1, as2)) return;
        if(as1 < 0 || as1 > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }
        if(as2 < 0 || as2 > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }


        if (conn != null) {
            System.out.println("database connected successfully");

            try {

                String updateQuery = "UPDATE caMarks SET assessment_mark1 = ?, assessment_mark2 = ? WHERE studentId = ? AND courseId = ?";
                PreparedStatement stmt = conn.prepareStatement(updateQuery);

                setDoubleOrNull(stmt, 1, as1);
                setDoubleOrNull(stmt, 2, as2);
                stmt.setString(3, tgNumber);
                stmt.setString(4, updateCoLable);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Update successful!");
                } else {
                    System.out.println("No rows updated. Check studentId and courseId.");
                }

                stmt.close();
                conn.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return 0;
    }

    @FXML
    public int handleUpdateMid() {
        Connection conn = mySqlCon.getConnection();

        if(EnterTgNumberMid.getText().trim().isEmpty() || updateMid.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null," All fields are required");
            return 1;
        }
        tgNumber = EnterTgNumberMid.getText().trim();
        updateCoLable = updateCLable.getText().trim();
        Double midMark = parseMark(updateMid.getText());

//        if (anyInvalidMark(midMark)) return;
        if(midMark < 0 || midMark > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }


        if (conn != null) {
            System.out.println("database connected successfully");

            try {
                    String updateQuery = "UPDATE caMarks SET mid_mark = ? WHERE studentId = ? AND courseId = ?";
                    PreparedStatement stmt = conn.prepareStatement(updateQuery);

                    setDoubleOrNull(stmt, 1, midMark);
                    stmt.setString(2, tgNumber);
                    stmt.setString(3, updateCoLable);

                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Update successful!");
                    } else {
                        System.out.println("No rows updated. Check studentId and courseId.");
                    }

                    stmt.close();
                    conn.close();

                } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    @FXML
    public int  handleUpdateEnd() {
        Connection conn = mySqlCon.getConnection();

        if(EnterTgNumberEnd.getText().trim().isEmpty() || updateEndTheory.getText().trim().isEmpty() || updateEndpra.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null," All fields are required");
            return 1;
        }
        tgNumber = EnterTgNumberEnd.getText().trim();
        updateCoLable = updateCLable.getText().trim();
        Double endMarkT = parseMark(updateEndTheory.getText());
        Double endMarkP = parseMark(updateEndpra.getText());

//        if (anyInvalidMark(endMarkT, endMarkP)) return;
        if(endMarkP < 0 || endMarkP > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }
        if(endMarkT < 0 || endMarkT > 100){
            JOptionPane.showMessageDialog(null," Invalid range of values");
            return 1;
        }



        if (conn != null) {
            System.out.println("database connected successfully");

            try {
                String updateQuery = "UPDATE finalMarks SET finalPractical = ?,finalTheory = ? WHERE studentId = ? AND courseId = ?";
                PreparedStatement stmt = conn.prepareStatement(updateQuery);


                setDoubleOrNull(stmt, 1, endMarkP);
                setDoubleOrNull(stmt, 2, endMarkT);
                stmt.setString(3, tgNumber);
                stmt.setString(4, updateCoLable);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Update successful!");
                } else {
                    System.out.println("No rows updated. Check studentId and courseId.");
                }

                stmt.close();
                conn.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    private boolean anyInvalidMark(Double... marks) {
        for (Double mark : marks) {
            if (mark != null && (mark < 0 || mark > 100)) {
                showAlert("All marks must be between 0 and 100.");
                return true;
            }
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Input Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleHome(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

}
