package Lecture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;


public class  LectureController extends BaseController{


    @FXML private ImageView image;
    public Button viewGpaButton;
    private TextField[] courseFields;

    @FXML private Button manageCourseButton;
    @FXML private Button studentMarksButton;

    @FXML private TextField CourseName1,CourseName2,CourseName3,CourseName4,CourseName5;
    private String[] names;

    @FXML
    private Label name;

    @FXML
    private  Label dep;

    @FXML
    private Button timeTableButton;


    @FXML
    public void initialize() {
        loadProfileData();
        courseFields = new TextField[]{CourseName1, CourseName2, CourseName3, CourseName4, CourseName5};
    }


    public void handleManageCourseButton(ActionEvent actionEvent) {
        Connection conn = mySqlCon.getConnection();

        if(conn != null){
            System.out.println("database connected successfully");

            try {

                String query = "SELECT * FROM lecture_course WHERE lecId = ?";
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.setString(1,Session.getUserId());
                ResultSet rs  = pstm.executeQuery();



                String [] courseNames = new String[5];
                int index =0;
                while (rs.next() && index < courseFields.length){

                        String name = rs.getString("courseId");
                        System.out.println("course name "+ name);
                        courseNames[index] = name;

                    index++;
                }
                rs.close();
//                stmt.close();
                conn.close();

                //load managecourse.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manageCourses.fxml"));
                //Parent root = null;
                try {
                    Parent root = loader.load();

                    ManageCoursesController controller = loader.getController();
                    controller.setCourseNames(courseNames);
                    Stage stage = (Stage) manageCourseButton.getScene().getWindow(); // get current stage
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void handleAddMarks(ActionEvent event){
        Connection conn = mySqlCon.getConnection();

        if(conn != null){
            System.out.println("database connected successfully");

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT courseId from courseUnit");

                String [] courseNames = new String[5];
                int index =0;
                while (rs.next() && index < courseFields.length){

                    String name = rs.getString("courseId");
                    System.out.println("course name "+ name);
                    courseNames[index] = name;

                    index++;
                }
                rs.close();
                stmt.close();
                conn.close();

                //load managecourse.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courseSelect.fxml"));
                //Parent root = null;
                try {
                    Parent root = loader.load();

                    courseSelectController controller = loader.getController();
                    controller.setCourseNames(courseNames);
                    Stage stage = (Stage) studentMarksButton.getScene().getWindow(); // get current stage
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void handleEligibility(){
        Connection conn = mySqlCon.getConnection();

        if(conn != null){
            System.out.println("database connected successfully");

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT courseId from courseUnit");

                String [] courseNames = new String[5];
                int index =0;
                while (rs.next() && index < courseFields.length){

                    String name = rs.getString("courseId");
                    System.out.println("course name "+ name);
                    courseNames[index] = name;

                    index++;
                }
                rs.close();
                stmt.close();
                conn.close();

                //load managecourse.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courseSelectForEligibility.fxml"));
                //Parent root = null;
                try {
                    Parent root = loader.load();

                    CourseSelectEligibilityController controller = loader.getController();
                    controller.setCourseNames(courseNames);
                    Stage stage = (Stage) studentMarksButton.getScene().getWindow(); // get current stage
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void handleViewGpa(ActionEvent event) {
        Connection conn = mySqlCon.getConnection();

        if(conn != null){
            System.out.println("database connected successfully");
                //load managecourse.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/viewGpa.fxml"));
                //Parent root = null;
                try {
                    Parent root = loader.load();

                    viewGpaController controller = loader.getController();
                    Stage stage = (Stage) studentMarksButton.getScene().getWindow(); // get current stage
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        }

    public void handleAttendence(ActionEvent event) {
        Connection conn = mySqlCon.getConnection();

        if (conn != null) {
            System.out.println("database connected successfully");

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT courseId from courseUnit");

                String[] courseNames = new String[5];
                int index = 0;
                while (rs.next() && index < courseFields.length) {

                    String name = rs.getString("courseId");
                    System.out.println("course name " + name);
                    courseNames[index] = name;

                    index++;
                }
                rs.close();
                stmt.close();
                conn.close();

                //load managecourse.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/manageCourseAtt.fxml"));
                //Parent root = null;
                try {
                    Parent root = loader.load();

                    manageCoursesForAttendence controller = loader.getController();
                    controller.setCourseNames(courseNames);
                    Stage stage = (Stage) studentMarksButton.getScene().getWindow(); // get current stage
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void handleEditDetails(ActionEvent event) {
        handleEditprofile();

            }

    public void handleNoticeButton(ActionEvent event) {
        //load managecourse.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/viewNotices.fxml"));
        //Parent root = null;
        try {
            Parent root = loader.load();
            Stage stage = (Stage) studentMarksButton.getScene().getWindow(); // get current stage
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public void handleViewMedical(ActionEvent event) {
        Connection conn = mySqlCon.getConnection();

        if (conn != null) {
            System.out.println("database connected successfully");

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT courseId from courseUnit");

                String[] courseNames = new String[5];
                int index = 0;
                while (rs.next() && index < courseFields.length) {

                    String name = rs.getString("courseId");
                    System.out.println("course name " + name);
                    courseNames[index] = name;

                    index++;
                }
                rs.close();
                stmt.close();
                conn.close();

                //load managecourse.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/manageCourseMedi.fxml"));
                //Parent root = null;
                try {
                    Parent root = loader.load();

                    manageCourseMediController controller = loader.getController();
                    controller.setCourseNames(courseNames);
                    Stage stage = (Stage) studentMarksButton.getScene().getWindow();
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public void loadProfileData(){
        Connection con = mySqlCon.getConnection();


        String userID = Session.getUserId();

        String query = "SELECT * FROM  userAccount WHERE user_id = ?";
        try {

            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1,Session.getUserId());
            ResultSet rs = pstm.executeQuery();


            if (rs.next()) {
/*
                JOptionPane.showMessageDialog(null,"No notice Found");
*/



//if we didnt put condition null pointer execption generate, becouse db data is null
                name.setText(rs.getString(2));


                dep.setText(rs.getString(7));

                String profilePicpath =  rs.getString(9);

                if(!profilePicpath.isEmpty()){
                    File file = new File(profilePicpath);

                    if(file.exists()){
                        try {
                            Image profileImage = new Image(file.toURL().toString());
                            if(image != null)image.setImage(profileImage);
                        } catch (MalformedURLException e) {
                            System.out.println("Error: "+e.getMessage());
                        }
                    }
                }else{
                    File file = new File("images/user.png");


                    if(file.exists()){
                        try {
                            Image profileImage = new Image(file.toURL().toString());
                            if(image != null)image.setImage(profileImage);
                        } catch (MalformedURLException e) {
                            System.out.println("Error: "+e.getMessage());
                        }
                    }

                }
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

        }

    }


    @FXML
    private void handleTimeTable(ActionEvent event) {
        String depName = dep.getText();

        if (depName == null || depName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Department name is missing.").show();
            return;
        }

        String query = "SELECT content FROM timetable WHERE depName = ?";

        try {
            Connection con = mySqlCon.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, depName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String filePath = rs.getString("content");

                if (filePath == null || filePath.isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Timetable file path is missing.").show();
                    return;
                }

                File file = new File(filePath);

                if (file.exists()) {
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Cannot open the timetable file.").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "File not found:\n" + file.getAbsolutePath()).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "No timetable found for department: " + depName).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }

        File selectedFile = chooseFile();
        if (selectedFile != null) {
            saveFilePathToDatabase(selectedFile, depName);
        }
    }

    private void saveFilePathToDatabase(File file, String depName) {
        String originalPath = file.getAbsolutePath();
        String correctedPath = originalPath.replace("\\", "/");

        String query = "INSERT INTO timetable (depName, content) VALUES (?, ?)";

        try {
            Connection con = mySqlCon.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, depName);
            ps.setString(2, correctedPath); // save the corrected path
            ps.executeUpdate();

            System.out.println("Timetable saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }

    private File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        return fileChooser.showOpenDialog(null);
    }



}




