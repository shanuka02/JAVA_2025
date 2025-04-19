public class UserAccountModel {

    private String user_id;
    private String user_name;
    private String email;
    private String roll;
    private String phoneNumber;
    private String address;
    private String depName;
    private String password;

    public UserAccountModel(String user_id, String user_name, String email, String roll,
                            String phoneNumber, String address, String depName, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.email = email;
        this.roll = roll;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.depName = depName;
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getEmail() {
        return email;
    }

    public String getRoll() {
        return roll;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getDepName() {
        return depName;
    }

    public String getPassword() {
        return password;
    }
}
