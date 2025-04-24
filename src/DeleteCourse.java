import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteCourse {
    @FXML
    private TableColumn<CourseModel, String> Code;

    @FXML
    private TableColumn<CourseModel, Integer> Credit;

    @FXML
    private Button Delete;

    @FXML
    private TableColumn<CourseModel, String> Lecture;

    @FXML
    private TableColumn<CourseModel, String> Name;

    @FXML
    private TableView<CourseModel> Table1;

    @FXML
    private TableColumn<CourseModel, String> Type;

    @FXML
    private TextField TextField2;

    mySqlCon connection;

    @FXML
    public void initialize(){
        Code.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        Name.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        Credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        Lecture.setCellValueFactory(new PropertyValueFactory<>("lectureIncharge"));
        Type.setCellValueFactory(new PropertyValueFactory<>("cType"));

        loadData();

        TextField2.textProperty().addListener((observable,oldValue,newValue )->{
            //If the new text is not empty, call the function searchByTitle()
            if(!newValue.trim().isEmpty()){
                searchByCourseID(newValue.trim());
            }else{
                //if table is empty clear the table
                loadData();
                //Table1.getItems().clear();
            }
        });

    }

    @FXML
    void HandledeleteButton(ActionEvent event) {
        CourseModel selectCourse = Table1.getSelectionModel().getSelectedItem();

        if(selectCourse != null){
            String id = selectCourse.getCourseId();


            connection = new mySqlCon();
            Connection con = connection.con();

            String query = "DELETE FROM courseUnit WHERE  courseId = ?";
            String query2 = "DELETE FROM student_course WHERE  courseId = ?";


            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1,id);
                int rowDeleted = pstm.executeUpdate();

                PreparedStatement pstm2 = con.prepareStatement(query2);
                pstm2.setString(1,id);
                int rowDeleted2 =pstm2.executeUpdate();


                if( rowDeleted2 > 0 || rowDeleted > 0){
                    JOptionPane.showMessageDialog(null,"Course Deleted Successfully ","Success",JOptionPane.INFORMATION_MESSAGE);

                }else{
                    JOptionPane.showMessageDialog(null,"Course Deleted Unsuccessfully ");

                }


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }


        }
        loadData();


    }


    public void loadData(){
        connection = new mySqlCon();
        Connection con = connection.con();

        String query = "select * from courseUnit";
        ObservableList<CourseModel> data = FXCollections.observableArrayList();

        try {
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                CourseModel course = new CourseModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getString(9)
                );
                data.add(course);
            }
            Table1.setItems(data);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }


    }

    private void searchByCourseID(String CourseID) {

        connection = new mySqlCon();
        Connection con = connection.con();
        ObservableList<CourseModel> data = FXCollections.observableArrayList();


        String code = TextField2.getText().trim();

        String query = "SELECT * FROM courseUnit WHERE courseId   LIKE ?";

        try {
            PreparedStatement pstm =  con.prepareStatement(query);
            pstm.setString(1,CourseID + "%");
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                CourseModel course = new CourseModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(8)

                );
                data.add(course);
            }
            Table1.setItems(data);


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }


    }

    @FXML
    public void BackbuttonHandle(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("\\FXMLfiles\\ManageCourse.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ApplicationDrive.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
