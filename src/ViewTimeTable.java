import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewTimeTable {

    @FXML
    private TableColumn<TimetableModel, String> Caption;

    @FXML
    private TableColumn<TimetableModel, String> Content;

    @FXML
    private TableColumn<TimetableModel, String> Date;

    @FXML
    private TableColumn<TimetableModel, String> DepName;

    @FXML
    private TableColumn<TimetableModel, String> ID;

    @FXML
    private TableView<TimetableModel> Table1;

    @FXML
    public void initialize(){
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Content.setCellValueFactory(new PropertyValueFactory<>("caption"));
        Date.setCellValueFactory(new PropertyValueFactory<>("submiteddate;"));
        DepName.setCellValueFactory(new PropertyValueFactory<>("content"));
        Caption  .setCellValueFactory(new PropertyValueFactory<>("depname"));

        loadData();
    }

    priv connection = new mySqlCon();
    Connection con = connection.con();

    ObservableList<NoticeModel> data = FXCollections.observableArrayList();

    String query = "SELECT * FROM notice";

        try {
        PreparedStatement pstm = con.prepareStatement(query);
        ResultSet rs = pstm.executeQuery();

        while(rs.next()){
            NoticeModel notice = new NoticeModel(
                    rs.getInt("notice_id"),
                    rs.getString("title"),
                    rs.getString("postedDay"),
                    rs.getString("content"),
                    rs.getString("userRoll")

            );
            data.add(notice);
        }
        Table1.setItems(data);
    } catch (
    SQLException e) {
        throw new RuntimeException(e);
    }ate void loadData() {

    }


}
