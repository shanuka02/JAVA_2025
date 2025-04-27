package Lecture;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;


public class StudentMarksController {
    @FXML
    public Label courseIdLabel;
    @FXML
    private TableView<modelCaMark> caMarkTable;

    @FXML
    private TableColumn<modelCaMark, String> studentId;
    @FXML
    private TableColumn<modelCaMark, Double> q1Mark, q2Mark, q3Mark, q4Mark, qTotal,
            ass1Mark, ass2Mark, assTotal, midMark, totalCa;
    //hold CA marks data for the tableview
    private ObservableList<modelCaMark> caMarkList = FXCollections.observableArrayList();

    @FXML
    private TableView<modelFinalMarks> finalMarksTable;
    @FXML
    private TableColumn<modelFinalMarks, String> studentIdFinal;
    @FXML
    private TableColumn<modelFinalMarks, Double> endMark;
    @FXML
    private TableColumn<modelFinalMarks, String> result;
    @FXML
    private TableColumn<modelFinalMarks, String> grade;
    @FXML
    private Button calculateResultButton;
    private ObservableList<modelFinalMarks> finalMarksList = FXCollections.observableArrayList();

    @FXML private Button idSearchButton;
    @FXML private TextField searchId;

    public double quizTotal, assesTotal, tempca = 0;


    public void setCourseLabel(String clabel) {
        courseIdLabel.setText(clabel);
        loadCaMarks();
        calculateAndLoadFinalMarks();

    }

    @FXML
    public void initialize() {
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
//        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        q1Mark.setCellValueFactory(new PropertyValueFactory<>("q1Mark"));
        q2Mark.setCellValueFactory(new PropertyValueFactory<>("q2Mark"));
        q3Mark.setCellValueFactory(new PropertyValueFactory<>("q3Mark"));
        q4Mark.setCellValueFactory(new PropertyValueFactory<>("q4Mark"));
        qTotal.setCellValueFactory(new PropertyValueFactory<>("qTotal"));
        ass1Mark.setCellValueFactory(new PropertyValueFactory<>("ass1Mark"));
        ass2Mark.setCellValueFactory(new PropertyValueFactory<>("ass2Mark"));
        assTotal.setCellValueFactory(new PropertyValueFactory<>("assTotal"));
        midMark.setCellValueFactory(new PropertyValueFactory<>("midMark"));
        totalCa.setCellValueFactory(new PropertyValueFactory<>("totalCa"));

        studentIdFinal.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        endMark.setCellValueFactory(new PropertyValueFactory<>("endMark"));
//        result.setCellValueFactory(new PropertyValueFactory<>("result"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        calculateResultButton.setOnAction(e -> calculateAndLoadFinalMarks());

    }

    @FXML
    private void calculateCa() {
        double tempQTotal, tempATotal;
        int size = caMarkList.size();

        Connection conn = mySqlCon.getConnection();

        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();

                for (int i = 0; i < size; i++) {
                    modelCaMark mark = caMarkList.get(i);

                    double q1 = mark.getQ1Mark();
                    double q2 = mark.getQ2Mark();
                    double q3 = mark.getQ3Mark();
                    double q4 = mark.getQ4Mark();
                    quizTotal = mark.getQTotal();
                    double asses1 = mark.getAss1Mark();
                    double asses2 = mark.getAss2Mark();
                    assesTotal = mark.getAssTotal();
                    double mid = mark.getMidMark();
                    int nuOfQ = mark.getNuOfQuises();
                    int nuOfAs = mark.getNuOfAssess();
                    int caPercentage = mark.getCaPercentage();

                    double minQ = Math.min(Math.min(q1, q2), Math.min(q3, q4));

                    tempQTotal = (q1 + q2 + q3 + q4) - minQ;

                    if (nuOfQ == 4) {
                        quizTotal = (tempQTotal / 30) * (100.0 / 10);
                        if (nuOfAs == 2) {
                            assesTotal = ((asses1 + asses2) / 200) * 20;
                            tempca = quizTotal + assesTotal + ((mid / 100) * 20);

                        } else if (nuOfAs == 1) {
                            assesTotal = asses1 / 10;
                            tempca = quizTotal + assesTotal + ((mid / 100) * 20);
                        } else {
                            tempca = quizTotal + ((mid / 100) * 20);
                        }
                    } else if (nuOfQ == 3) {
                        quizTotal = (tempQTotal / 20) * (100.0 / 10);
                        if (nuOfAs == 2) {
                            assesTotal = ((asses1 + asses2) / 200) * 20;
                            tempca = quizTotal + assesTotal + ((mid / 100) * 20);

                        } else if (nuOfAs == 1) {
                            assesTotal = asses1 / 10;
                            tempca = quizTotal + assesTotal + ((mid / 100) * 20);
                        } else {
                            tempca = quizTotal + ((mid / 100) * 20);
                        }
                    } else if (nuOfQ == 0) {
                        if (nuOfAs == 2) {
                            assesTotal = ((asses1 + asses2) / 200) * 20;
                            tempca = assesTotal + ((mid / 100) * 20);

                        } else if (nuOfAs == 1) {
                            assesTotal = (asses1 / 100) * 20;
                            tempca = assesTotal + ((mid / 100) * 20);
                        } else {
                            tempca = quizTotal + ((mid / 100) * 20);
                        }

                    }

                    double eligibilityStatus = ((tempca * 100) / caPercentage);
                    if (eligibilityStatus >= 50) {
                        mark.setEligibility("Eligi");
                    } else {
                        mark.setEligibility("not");
                    }

                    mark.setQTotal(quizTotal);
                    mark.setAssTotal(assesTotal);
                    mark.setTotalCa(tempca);

                    String updateQuery = "UPDATE caMarks SET q_total = ?, assessment_total = ?, total_ca = ?, eligibility = ? WHERE studentId = ?";
                    PreparedStatement ps = conn.prepareStatement(updateQuery);
                    ps.setDouble(1, quizTotal);
                    ps.setDouble(2, assesTotal);
                    ps.setDouble(3, tempca);
                    ps.setString(4, mark.getEligibility());
                    ps.setString(5, mark.getStudentId());
                    ps.executeUpdate();

                }


                caMarkTable.refresh();
                calculateAndLoadFinalMarks();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadCaMarks() {
        Connection conn = mySqlCon.getConnection();
        if (conn != null) {
            System.out.println("database connected successfully");


            String sql = """
        SELECT cm.studentId,
               cm.courseId,
               cm.q1_mark,
               cm.q2_mark,
               cm.q3_mark,
               cm.q4_mark,
               cm.q_total,
               cm.assessment_mark1,
               cm.assessment_mark2,
               cm.assessment_total,
               cm.mid_mark,
               cm.total_ca,
               cu.nuOfQuises,
               cu.nuOfAssesments,
               cu.ca_percentage,
               cm.eligibility
        FROM caMarks cm
        JOIN courseunit cu ON cm.courseId = cu.courseId
        WHERE cm.courseId = ?
    """;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, courseIdLabel.getText());
                ResultSet rs = ps.executeQuery();

                caMarkList.clear(); //  CLEAR old data


                while (rs.next()) {
                    modelCaMark mark = new modelCaMark(

                            rs.getString("studentId"),
                            rs.getDouble("q1_mark"),
                            rs.getDouble("q2_mark"),
                            rs.getDouble("q3_mark"),
                            rs.getDouble("q4_mark"),
                            rs.getDouble("q_total"),
                            rs.getDouble("assessment_mark1"),
                            rs.getDouble("assessment_mark2"),
                            rs.getDouble("assessment_total"),
                            rs.getDouble("mid_mark"),
                            rs.getDouble("total_ca"),
                            rs.getInt("nuOfQuises"),
                            rs.getInt("nuOfAssesments"),
                            rs.getInt("ca_percentage"),
                            rs.getString("eligibility")
                    );

                    caMarkList.add(mark);
                }
                //set the data list to the tableview
                caMarkTable.setItems(caMarkList);
                caMarkTable.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    private void calculateAndLoadFinalMarks() {
        String courseId = courseIdLabel.getText();

        Connection conn = mySqlCon.getConnection();
        finalMarksList.clear();
        if (conn != null) {
            System.out.println("database connected successfully");
//                Statement stmt = conn.createStatement();
                String selectsql = """
                            SELECT cm.studentId, cm.courseId, cm.total_ca, fm.finaltheory, fm.finalpractical
                            FROM caMarks cm
                            JOIN finalMarks fm 
                              ON cm.studentId = fm.studentId 
                             AND cm.courseId  = fm.courseId
                            WHERE cm.courseId = ?
                        """;
                String updateSql = """
                            UPDATE finalMarks SET
                              finalTotal = ?,
                              grade      = ?
                            WHERE studentId = ? AND courseId = ?
                        """;


                try (
                        PreparedStatement psSel = conn.prepareStatement(selectsql);
                        PreparedStatement psUp = conn.prepareStatement(updateSql)
                ) {
                    psSel.setString(1, courseId);
                    ResultSet rs = psSel.executeQuery();

                    while (rs.next()) {
                        String sid = rs.getString("studentId");
                        double caTot = rs.getDouble("total_ca");
                        double fTh = rs.getDouble("finaltheory");
                        double fPr = rs.getDouble("finalpractical");

                        double endM;
                        String gr;

                        endM = caTot+fPr+fTh;

                        String grade;
//                        if (endM >= 70) {
//                            gr = "A";
//                        } else if (endM >= 60) {
//                            gr = "B";
//                        } else if (endM >= 50) {
//                            gr= "C";
//                        } else if (endM >= 40) {
//                            gr = "D";
//                        } else {
//                            gr = "F";
//                        }
                        if (endM >= 90) {
                            gr = "A+";
                        } else if (endM >= 85) {
                            gr = "A";
                        } else if (endM >= 80) {
                            gr = "A-";
                        } else if (endM >= 75) {
                            gr = "B+";
                        } else if (endM >= 70) {
                            gr = "B";
                        } else if (endM >= 65) {
                            gr = "B-";
                        } else if (endM >= 60) {
                            gr = "C+";
                        } else if (endM >= 55) {
                            gr = "C";
                        } else if (endM >= 50) {
                            gr = "C-";
                        } else if (endM >= 45) {
                            gr = "D+";
                        } else if (endM >= 40) {
                            gr = "D";
                        } else {
                            gr = "E";
                        }


                        psUp.setDouble(1, endM);
                        psUp.setString(2, gr);
                        psUp.setString(3, sid);
                        psUp.setString(4, courseId);
                        psUp.executeUpdate();

                        finalMarksList.add(new modelFinalMarks(sid, endM, gr));

                    }

                    finalMarksTable.setItems(finalMarksList);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }


        }


        @FXML
        private void search(){
            String searchTg = searchId.getText().trim().toLowerCase();

            if(searchTg.isEmpty()){
                caMarkTable.setItems(caMarkList);
                finalMarksTable.setItems(finalMarksList);
                return;
            }

            ObservableList<modelCaMark> filteredCaList = FXCollections.observableArrayList();
            ObservableList<modelFinalMarks> filteredFinalList = FXCollections.observableArrayList();

            for(modelCaMark mark: caMarkList){
                if(mark.getStudentId().toLowerCase().contains(searchTg)){
                    filteredCaList.add(mark);
                }
            }

            for (modelFinalMarks mark : finalMarksList) {
                if (mark.getStudentId().toLowerCase().contains(searchTg)) {
                    filteredFinalList.add(mark);
                }
            }

            caMarkTable.setItems(filteredCaList);
            finalMarksTable.setItems(filteredFinalList);
        }

    public void  handleHome(ActionEvent event) {
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

