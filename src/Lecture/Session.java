package Lecture;

public class Session {
    public static String getUserId;
    private static String password;
    private static String UserId;

    public   static void setSession(String pass,String ID){
        password  = pass;
        UserId =ID;
    }

    public static String getUserId(){
        return UserId;
    }
    public static String getPassword(){
        return password;
    }
    public static void clearSession(){
        password = null;
        UserId= null;
    }
}
