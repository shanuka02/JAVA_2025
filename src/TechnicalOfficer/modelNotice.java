package TechnicalOfficer;

public class modelNotice {
    private int noticeId;
    private String title;
    private String postedDay;
    private String content;

    public modelNotice(int noticeId, String title, String postedDay, String content) {
        this.noticeId = noticeId;
        this.title = title;
        this.postedDay = postedDay;
        this.content = content;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostedDay() {
        return postedDay;
    }

    public void setPostedDay(String postedDay) {
        this.postedDay = postedDay;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
