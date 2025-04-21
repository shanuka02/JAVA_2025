public class TimetableModel {

    private String id;
    private String caption;
    private String submiteddate;
    private String content;
    private String depname;

    public TimetableModel(String id, String caption, String submiteddate, String content, String depname) {
        this.id = id;
        this.caption = caption;
        this.submiteddate = submiteddate;
        this.content = content;
        this.depname = depname;
    }

    public TimetableModel(String id, String caption, String submiteddate) {
        this.id = id;
        this.caption = caption;
        this.submiteddate = submiteddate;
        /*this.content = null;
        this.depname = null;*/
    }

    public String getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getSubmiteddate() {
        return submiteddate;
    }

    public String getContent() {
        return content;
    }

    public String getDepname() {
        return depname;
    }
}
