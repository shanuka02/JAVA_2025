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

    public String getTitle() {
        return title;
    }

    public String getPostedDay() {
        return postedDay;
    }

    public String getContent() {
        return content;
    }
}
