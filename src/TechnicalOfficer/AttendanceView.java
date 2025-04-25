package TechnicalOfficer;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class AttendanceView {
    @FXML
    private Button updateBtn;

    @FXML
    private ComboBox<String> setCourseId;

    @FXML
    private TextField setLectureHours;

    @FXML
    private ComboBox<String> setLectureType;

    @FXML
    private DatePicker setPresentData;

    @FXML
    private TextField setPresentTime;

    @FXML
    private ComboBox<String> setStatus;

    @FXML
    private TextField setStudentId;

    @FXML
    private TextField attendanceId;

    @FXML
    private ComboBox<String> courseId;

    @FXML
    private ComboBox<String> lectureType;

    @FXML
    private DatePicker preDate;

    @FXML
    private TableColumn<AttendanceRow, String> column1;

    @FXML
    private TableColumn<AttendanceRow, String> column2;

    @FXML
    private TableColumn<AttendanceRow, String> column3;

    @FXML
    private TableColumn<AttendanceRow, String> column4;

    @FXML
    private TableColumn<AttendanceRow, String> column5;

    @FXML
    private TableColumn<AttendanceRow, String> column6;

    @FXML
    private TableColumn<AttendanceRow, String> column7;

    @FXML
    private TableView<AttendanceRow> table;

    private int AttendanceId;

    @FXML
    public void submitBtn() throws SQLException {
        if(checkFields()) {
            table.getItems().clear();
            getAttendance();
        }else {
            new AttendanceAdd().alert("Please fill all the fields","Can't submit");
        }
    }

    @FXML
    public void initialize() {
        column1.setCellValueFactory(cellData -> cellData.getValue().col1Property());
        column2.setCellValueFactory(cellData -> cellData.getValue().col2Property());
        column3.setCellValueFactory(cellData -> cellData.getValue().col3Property());
        column4.setCellValueFactory(cellData -> cellData.getValue().col4Property());
        column5.setCellValueFactory(cellData -> cellData.getValue().col5Property());
        column6.setCellValueFactory(cellData -> cellData.getValue().col6Property());
        column7.setCellValueFactory(cellData -> cellData.getValue().col7Property());

        accessDB("course", "SELECT courseId FROM courseunit");
    }

    @FXML
    public void backToPage() {
        new TechnicalMain().mainLoader();
    }

    @FXML
    public void getAttendance() {
        String quary = "SELECT * FROM attendance WHERE Att_cou_id = ? AND Pre_date = ? AND Lec_type = ?";
        try {
            Connection connection = mySqlCon.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(quary);
            preparedStatement.setString(1, courseId.getValue());
            preparedStatement.setDate(2, Date.valueOf(preDate.getValue()));
            preparedStatement.setString(3, lectureType.getValue());
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                table.getItems().add(new AttendanceRow(results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7),results.getString(8)));
            }
            if(table.getItems().isEmpty()){
                new AttendanceAdd().alert("No attendance found","No attendance found");
                table.getItems().clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void select(){
        lectureType.getItems().clear();
        accessDB("lecture","SELECT cType FROM courseunit where courseId ='"+courseId.getValue()+"'");
    }

    private void accessDB(String select,String quary){
        int count = 0;
        String[] setData;

        if(select.equals("course") || select.equals("setCourseId")){
            setData = new String[5];
        }else{
            setData = new String[1];
        }
        try {
            Connection connection  = mySqlCon.getConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(quary);

            while (results.next()) {
                setData[count] = results.getString(1);
                count++;
            }
            if(select.equals("course")) {
                courseId.getItems().addAll(setData);
            }else if(select.equals("setCourseId")) {
                setCourseId.getItems().addAll(setData);
            }else if(select.equals("setLecture")) {
                if(setData[0].equals("Both") || setData[0].equals("both")){
                    setLectureType.getItems().addAll("Theory","Practical");
                }else{
                    setLectureType.getItems().addAll(setData);
                }
            }else {
                if(setData[0].equals("Both") || setData[0].equals("both")){
                    lectureType.getItems().addAll("Theory","Practical");
                }else{
                    lectureType.getItems().addAll(setData);
                }
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    @FXML
    public void checkAttendance() {
        clear();
        if(!attendanceId.getText().isEmpty()){
            AttendanceId = Integer.parseInt(attendanceId.getText());
            if(AttendanceId > 0){
                setStatus.getItems().addAll("Absent","Present","Medical");
                accessDB("setCourseId", "SELECT courseId FROM courseunit");
                try {
                    Connection connection = mySqlCon.getConnection();
                    assert connection != null;
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM attendance WHERE Att_id = ?");
                    preparedStatement.setInt(1, AttendanceId);
                    ResultSet results = preparedStatement.executeQuery();
                    if(results.next()){
                        if(results.getString(1).isEmpty()){
                            new AttendanceAdd().alert("Attendance ID not found","Can't submit");
                        }
                        setStudentId.setText(results.getString(2));
                        setCourseId.setValue(results.getString(3));
                        setPresentData.setValue(results.getDate(4).toLocalDate());
                        setPresentTime.setText(results.getTime(5).toString());
                        setLectureHours.setText(results.getString(6));
                        setLectureType.setValue(results.getString(7));
                        setStatus.setValue(results.getString(8));
                        accessDB("setLecture","SELECT cType FROM courseunit where courseId ='"+setCourseId.getValue()+"'");
                    }
                }catch (SQLException e){

                }
            }else {
                new AttendanceAdd().alert("Please Enter valid ID","Can't Submit");
                attendanceId.clear();
                attendanceId.requestFocus();
            }
        }else{
            new AttendanceAdd().alert("Please fill the attendance ID","Can't Submit");
            attendanceId.requestFocus();
            clear();
        }
    }

    @FXML
    public void updateAttendance() {
        System.out.println(setCourseId.getValue());
        if(checkUpdateFields()){
//            updateBtn.setDisable(true);
            new AttendanceAdd().alert("Please fill all the fields","Can't Update");
        }else {
//            updateBtn.setDisable(false);
            String quary = "UPDATE attendance SET Att_stu_id = ?, Att_cou_id = ?, Pre_date = ?, Pre_time = ?, Lec_hours = ?, Lec_type = ?, Status_ = ? WHERE Att_id = ?";
            try {
                Connection connection = mySqlCon.getConnection();
                assert connection != null;
                PreparedStatement preparedStatement = connection.prepareStatement(quary);
                preparedStatement.setString(1, setStudentId.getText());
                preparedStatement.setString(2, setCourseId.getValue());
                preparedStatement.setDate(3, Date.valueOf(setPresentData.getValue()));
                preparedStatement.setTime(4, Time.valueOf(setPresentTime.getText()));
                preparedStatement.setInt(5, Integer.parseInt(setLectureHours.getText()));
                preparedStatement.setString(6, setLectureType.getValue());
                preparedStatement.setString(7, setStatus.getValue());
                preparedStatement.setInt(8, AttendanceId);
                preparedStatement.executeUpdate();
                if(preparedStatement.executeUpdate() > 0){
                    new AttendanceAdd().alert("Attendance updated successfully","Success");
                    clear();
//                    updateBtn.setDisable(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void clear(){
        setCourseId.setValue(null);
        setCourseId.getItems().clear();
        setLectureHours.clear();
        setLectureType.setValue(null);
        setLectureType.getItems().clear();
        setPresentData.setValue(null);
        setPresentTime.clear();
        setStatus.setValue(null);
        setStatus.getItems().clear();
        setStudentId.clear();
    }

    private boolean checkFields(){
        if(courseId.getValue() == null || preDate.getValue() == null || lectureType.getValue() == null){
            if(courseId.getValue() == null){
                courseId.requestFocus();
            }else if(preDate.getValue() == null){
                preDate.requestFocus();
            }else if(lectureType.getValue() == null){
                lectureType.requestFocus();
            }
            return false;
        }else {
            return true;
        }
    }

    private boolean checkUpdateFields(){
        if(setCourseId.getValue() == null || setStudentId.getText().isEmpty() || setPresentData.getValue() == null || setPresentTime.getText().isEmpty() || setLectureHours.getText().isEmpty() || setLectureType.getValue() == null || setStatus.getValue() == null){
            if(setStudentId.getText().isEmpty()){
                setStudentId.requestFocus();
            }else if(setCourseId.getValue() == null ) {
                setCourseId.requestFocus();
            }else if(setLectureType.getValue() == null) {
                setLectureType.requestFocus();
            }else if(setLectureHours.getText().isEmpty()) {
                setLectureHours.requestFocus();
            }else if(setPresentData.getValue() == null){
                setPresentData.requestFocus();
            }else if(setPresentTime.getText().isEmpty()){
                setPresentTime.requestFocus();
            }else if(setStatus.getValue() == null){
                setStatus.requestFocus();
            }
            return true;
        }else{
            return false;
        }
    }
}
