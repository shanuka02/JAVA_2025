package TechnicalOfficer;

import javafx.beans.property.SimpleStringProperty;

public class AttendanceRow {
    private final SimpleStringProperty col1;
    private final SimpleStringProperty col2;
    private final SimpleStringProperty col3;
    private final SimpleStringProperty col4;
    private final SimpleStringProperty col5;
    private final SimpleStringProperty col6;
    private final SimpleStringProperty col7;

    public AttendanceRow(String c1, String c2, String c3, String c4, String c5, String c6, String c7) {
        this.col1 = new SimpleStringProperty(c1);
        this.col2 = new SimpleStringProperty(c2);
        this.col3 = new SimpleStringProperty(c3);
        this.col4 = new SimpleStringProperty(c4);
        this.col5 = new SimpleStringProperty(c5);
        this.col6 = new SimpleStringProperty(c6);
        this.col7 = new SimpleStringProperty(c7);
    }

    public SimpleStringProperty col1Property() { return col1; }
    public SimpleStringProperty col2Property() { return col2; }
    public SimpleStringProperty col3Property() { return col3; }
    public SimpleStringProperty col4Property() { return col4; }
    public SimpleStringProperty col5Property() { return col5; }
    public SimpleStringProperty col6Property() { return col6; }
    public SimpleStringProperty col7Property() { return col7; }
}
