package Admin;

public class Session {
    private static String password;
    private static String UserId;
    private  static String rollVal;

    public  static void setSession(String pass,String ID,String roll){
        password  = pass;
        UserId =ID;
        rollVal = roll;
    }

    public static String getUserId(){
        return UserId;
    }
    public static String getPassword(){
        return password;
    }
    public static String getRollVal(){
        return rollVal;
    }
    public static void clearSession(){
        password = null;
        UserId= null;
    }
}
