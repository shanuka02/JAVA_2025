import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class UserAccountModel {
    private final StringProperty user_id;
    private final StringProperty user_name;
    private final StringProperty email;
    private final StringProperty roll;
    private final StringProperty phoneNumber;
    private final StringProperty address;
    private final StringProperty depName;
    private final StringProperty password;

    public UserAccountModel(String user_id, String user_name, String email, String roll,
                            String phoneNumber, String address, String depName, String password) {
        this.user_id = new SimpleStringProperty(user_id);
        this.user_name = new SimpleStringProperty(user_name);
        this.email = new SimpleStringProperty(email);
        this.roll = new SimpleStringProperty(roll);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.address = new SimpleStringProperty(address);
        this.depName = new SimpleStringProperty(depName);
        this.password = new SimpleStringProperty(password);
    }

    public StringProperty user_idProperty() {
        return user_id;
    }

    public StringProperty user_nameProperty() {
        return user_name;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty rollProperty() {
        return roll;
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty depNameProperty() {
        return depName;
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
