import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NoticeModel {
private final SimpleIntegerProperty id;
private final SimpleStringProperty title;
private final SimpleStringProperty date;
private final SimpleStringProperty roll;
private final SimpleStringProperty content;

NoticeModel(Integer id ,String title, String date ,String roll,String content){

    this.id = new SimpleIntegerProperty(id);
    this.title = new SimpleStringProperty(title);
    this.date = new SimpleStringProperty(date);
    this.roll = new SimpleStringProperty(roll);
    this.content = new SimpleStringProperty(content);


}

    public int getId() {
        return id.get();
    }

    public String getTitle() {
        return title.get();
    }



    public String getDate() {
        return date.get();
    }

    public String getRoll() {
        return roll.get();
    }

    public String getContent() {
        return content.get();
    }


}
