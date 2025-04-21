import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCourse {

    @FXML
    private TableColumn<CourseModel, Integer> Assesment;

    @FXML
    private TableColumn<CourseModel, Integer> Assesment1;

    @FXML
    private TableColumn<CourseModel, Integer> Assesment11;

    @FXML
    private TableColumn<CourseModel, Integer> CA;

    @FXML
    private TableColumn<CourseModel, Integer> CA1;

    @FXML
    private TableColumn<CourseModel, Integer> CA11;

    @FXML
    private TableColumn<CourseModel, String> Code;

    @FXML
    private TableColumn<CourseModel, String> Code1;

    @FXML
    private TableColumn<CourseModel, String> Code11;

    @FXML
    private TableColumn<CourseModel, Integer> Credit;

    @FXML
    private TableColumn<CourseModel, Integer> Credit1;

    @FXML
    private TableColumn<CourseModel, Integer> Credit11;

    @FXML
    private TableColumn<CourseModel, String> GPA;

    @FXML
    private TableColumn<CourseModel, String> GPA1;

    @FXML
    private TableColumn<CourseModel, String> GPA11;

    @FXML
    private TableColumn<CourseModel, String> Lecture;

    @FXML
    private TableColumn<CourseModel, String> Lecture1;

    @FXML
    private TableColumn<CourseModel, String> Lecture11;

    @FXML
    private TableColumn<CourseModel, String> Name;

    @FXML
    private TableColumn<CourseModel, String> Name1;

    @FXML
    private TableColumn<CourseModel, String> Name11;

    @FXML
    private TableColumn<CourseModel, Integer> Quizes;

    @FXML
    private TableColumn<CourseModel, Integer> Quizes1;

    @FXML
    private TableColumn<CourseModel, Integer> Quizes11;

    @FXML
    private TableColumn<CourseModel, String> Type;

    @FXML
    private TableColumn<CourseModel, String> Type1;

    @FXML
    private TableColumn<CourseModel, String> Type11;
    @FXML
    private Button Search;

    @FXML
    private TableView<CourseModel> Table1;

    @FXML
    private TableView<CourseModel> Table2;

    @FXML
    private TableView<CourseModel> Table3;

    @FXML
    private TextField TextField1;

    @FXML
    private TextField TextField2;
    mySqlCon connection;

    @FXML
    public void initialize(){
       Code.setCellValueFactory(new PropertyValueFactory<>("courseId"));
       Name.setCellValueFactory(new PropertyValueFactory<>("courseName"));
       Credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
       CA.setCellValueFactory(new PropertyValueFactory<>("caPercentage"));
       Quizes.setCellValueFactory(new PropertyValueFactory<>("nuOfQuises"));
       Assesment.setCellValueFactory(new PropertyValueFactory<>("nuOfAssesments"));
       Lecture.setCellValueFactory(new PropertyValueFactory<>("lectureIncharge"));
       GPA.setCellValueFactory(new PropertyValueFactory<>("gpaState"));
       Type.setCellValueFactory(new PropertyValueFactory<>("cType"));

        Code1.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        Name1.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        Credit1.setCellValueFactory(new PropertyValueFactory<>("credit"));
        CA1.setCellValueFactory(new PropertyValueFactory<>("caPercentage"));
        Quizes1.setCellValueFactory(new PropertyValueFactory<>("nuOfQuises"));
        Assesment1.setCellValueFactory(new PropertyValueFactory<>("nuOfAssesments"));
        Lecture1.setCellValueFactory(new PropertyValueFactory<>("lectureIncharge"));
        GPA1.setCellValueFactory(new PropertyValueFactory<>("gpaState"));
        Type1.setCellValueFactory(new PropertyValueFactory<>("cType"));

        Code11.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        Name11.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        Credit11.setCellValueFactory(new PropertyValueFactory<>("credit"));
        CA11.setCellValueFactory(new PropertyValueFactory<>("caPercentage"));
        Quizes11.setCellValueFactory(new PropertyValueFactory<>("nuOfQuises"));
        Assesment11.setCellValueFactory(new PropertyValueFactory<>("nuOfAssesments"));
        Lecture11.setCellValueFactory(new PropertyValueFactory<>("lectureIncharge"));
        GPA11.setCellValueFactory(new PropertyValueFactory<>("gpaState"));
        Type.setCellValueFactory(new PropertyValueFactory<>("cType"));

    loadData();

       TextField2.textProperty().addListener((observable,oldValue,newValue )->{
            //If the new text is not empty, call the function searchByTitle()
            if(!newValue.trim().isEmpty()){
                searchByCourseName(newValue.trim());
            }else{
                //if table is empty clear the table
                loadData();
                //Table2.getItems().clear();
            }
        });

    }


    private void searchByCourseName(String Coursename) {

        connection = new mySqlCon();
        Connection con = connection.con();
        ObservableList<CourseModel> data = FXCollections.observableArrayList();


        String code = TextField2.getText().trim();

        String query = "SELECT * FROM courseUnit WHERE courseId   LIKE ?";

        try {
            PreparedStatement pstm =  con.prepareStatement(query);
            pstm.setString(1,Coursename + "%");
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
            Table3.setItems(data);


        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
        }


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
            Table3.setItems(data);
        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
        }


    }


    @FXML
    public void HandleSearchButton(ActionEvent actionEvent) {
        connection = new mySqlCon();
        Connection con = connection.con();
        ObservableList<CourseModel> data = FXCollections.observableArrayList();


        String code = TextField1.getText().trim();

        String query = "select * from courseUnit WHERE courseId = ?";

        try {
            PreparedStatement pstm =  con.prepareStatement(query);
            pstm.setString(1,code);
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
            Table2.setItems(data);

           

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

        }


    }
}

