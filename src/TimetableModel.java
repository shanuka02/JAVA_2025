import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TimetableModel {
    private final StringProperty id;
    private final StringProperty caption;
    private final StringProperty submiteddate;
    private final StringProperty content;
    private final StringProperty depname;

    public TimetableModel(String id, String caption, String submiteddate, String content, String depname) {
        this.id = new SimpleStringProperty(id);
        this.caption =  new SimpleStringProperty(caption);
        this.submiteddate =  new SimpleStringProperty (submiteddate);
        this.content =  new SimpleStringProperty (content);
        this.depname =  new SimpleStringProperty (depname);
    }

    public TimetableModel(String id, String caption, String submiteddate) {
        this.id = new SimpleStringProperty(id);
        this.caption =  new SimpleStringProperty(caption);
        this.submiteddate =  new SimpleStringProperty (submiteddate);
        this.content = null;
        this.depname = null;


    }

    public String getId() {
        return id.get();
    }



    public String getCaption() {
        return caption.get();
    }


    public String getSubmiteddate() {
        return submiteddate.get();
    }


    public String getContent() {
        return content.get();
    }



    public String getDepname() {
        return depname.get();
    }


}
