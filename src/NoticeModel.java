public class NoticeModel {

    private int id;
    private String title;
    private String date;
    private String roll;
    private String content;

    public NoticeModel(int id, String title, String date, String roll, String content) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.roll = roll;
        this.content = content;
    }

    public NoticeModel(int id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.roll = null;
        this.content = null;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getRoll() {
        return roll;
    }

    public String getContent() {
        return content;
    }
}
