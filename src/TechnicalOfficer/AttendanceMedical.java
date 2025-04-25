package TechnicalOfficer;

import java.io.IOException;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class AttendanceMedical {
    public void setAttendance(){
        String quary1 = "SELECT Medi_id, Me_stu_id, Me_cou_id, Lec_type, Request_date FROM Medical WHERE Status_ = 'approved'";
        String quary2 = "SELECT Att_id, Att_stu_id, Att_cou_id, Lec_type, Pre_date FROM attendance WHERE status_ = 'Absent' AND Att_medi_id IS NULL";

        Connection connection = DBConnection.getConnection();
        try {
            assert connection != null;  //medical table
            Statement statement1 = connection.createStatement();
            ResultSet results1 = statement1.executeQuery(quary1);

            Statement statement2 = connection.createStatement();
            ResultSet results2 = statement2.executeQuery(quary2);
            while (results1.next()) {
                while (results2.next()){
                    if (results2.getString(2).equals(results1.getString(2)) && results2.getString(3).equals(results1.getString(3)) && results2.getString(4).equals(results1.getString(4)) && results2.getString(5).equals(results1.getString(5))){
                        saveAttendance(parseInt(results1.getString(1)), parseInt(results2.getString(1)), connection);
                        break;
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {

        }
    }

    private void saveAttendance(int medical_id, int attendance_id, Connection connection) throws SQLException {
        String quary = "UPDATE attendance SET Status_ = ?, Att_medi_id = ? WHERE Att_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(quary);
            preparedStatement.setString(1,"Medical");
            preparedStatement.setInt(2, medical_id);
            preparedStatement.setInt(3, attendance_id);
            int result= preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Attendance updated");
            }
        }catch (SQLException e){
            System.out.println("Error updating attendance " + e.getMessage());
        }
    }

    public void backToPage() throws IOException {
        new TechnicalMain().Go();
    }
}
