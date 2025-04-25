package Lecture;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class modelNotice {
    private final SimpleIntegerProperty noticeId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty postedDay;
    private final SimpleStringProperty content;

    public modelNotice(int noticeId, String title, String postedDay, String content) {
        this.noticeId = new SimpleIntegerProperty(noticeId);
        this.title = new SimpleStringProperty(title);
        this.postedDay = new SimpleStringProperty(postedDay);
        this.content = new SimpleStringProperty(content);
    }

    public int getNoticeId() { return noticeId.get(); }
    public String getTitle() { return title.get(); }
    public String getPostedDay() { return postedDay.get(); }
    public String getContent() { return content.get(); }
}
