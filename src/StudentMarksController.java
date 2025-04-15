import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableView;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StudentMarksController {
    @FXML
    private TableView<modelCaMark> caMarkTable;
//    @FXML
//    private TableColumn<modelCaMark, Integer> nuOfQuis, nuOfAsse;
    @FXML
    private TableColumn<modelCaMark, String> studentId;
    @FXML
    private TableColumn<modelCaMark, Double> q1Mark, q2Mark, q3Mark, q4Mark, qTotal,
            ass1Mark, ass2Mark, assTotal, midMark, totalCa;
    //hold CA marks data for the tableview
    private ObservableList<modelCaMark> caMarkList = FXCollections.observableArrayList();



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

        //load data from db
        loadCaMarks();

    }

    private void loadCaMarks() {
        Connection conn = dbConnection.getConnection();
        if (conn != null) {
            System.out.println("database connected successfully");

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT cm.studentId, cm.courseId, cm.q1_mark, cm.q2_mark, cm.q3_mark, cm.q4_mark, " +
                        "cm.q_total, cm.assessment_mark1, cm.assessment_mark2, cm.assessment_total, " +
                        "cm.mid_mark, cm.total_ca, cu.nuOfQuises, cu.nuOfAssesments " +
                        "FROM caMarks cm JOIN courseunit cu ON cm.courseId = cu.courseId");


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
                            rs.getInt("nuOfAssesments")
                    );
                    caMarkList.add(mark);
                }
                //set the data list to the tableview
                caMarkTable.setItems(caMarkList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    private void calculateCa() {
        double tempQTotal, tempATotal, tempca = 0;
        int size = caMarkList.size();

        Connection conn = dbConnection.getConnection();

        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();

                for (int i = 0; i < size; i++) {
                    modelCaMark mark = caMarkList.get(i);

                    double q1 = mark.getQ1Mark();
                    double q2 = mark.getQ2Mark();
                    double q3 = mark.getQ3Mark();
                    double q4 = mark.getQ4Mark();
                    double quizTotal = mark.getQTotal();
                    double asses1 = mark.getAss1Mark();
                    double asses2 = mark.getAss2Mark();
                    double assesTotal = mark.getAssTotal();
                    double mid = mark.getMidMark();
                    int nuOfQ = mark.getNuOfQuises();
                    int nuOfAs = mark.getNuOfAssess();

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
                            assesTotal = (asses1 / 100)*20;
                            tempca = assesTotal + ((mid / 100) * 20);
                        } else {
                            tempca = quizTotal + ((mid / 100) * 20);
                        }

                    }

                    mark.setQTotal(quizTotal);
                    mark.setAssTotal(assesTotal);
                    mark.setTotalCa(tempca);

                    String updateQuery = "UPDATE caMarks SET q_total = " + quizTotal +
                            ", assessment_total = " + assesTotal +
                            ", total_ca = " + tempca +
                            " WHERE studentId = '" + mark.getStudentId() + "'";
                    stmt.executeUpdate(updateQuery);
                }
                caMarkTable.refresh();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}