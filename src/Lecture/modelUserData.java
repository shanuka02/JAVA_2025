package Lecture;

public class modelUserData {
    private String userId;
    private String userName;

    public modelUserData(String userId, String userName) {
        this.userId = userId;
         this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
