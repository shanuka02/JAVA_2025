import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDbConnector{
    //url for my db
    private String url = "jdbc:mysql://localhost:3307/fot";
    //username
    private String user = "root";
    //password
    private String password = "1234";

    //connection db
    private Connection mycon = null;

    //1.register the driver
    private void registerMyConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("successfuly ");
        } catch (ClassNotFoundException e) {
            System.out.println("Error in registering the drive class..."+ e.getMessage());

        }
    }

    //2.create and returning the connection
    public Connection getMyConnection(){
        //register the driver
        registerMyConnection();

        try{
            mycon = DriverManager.getConnection(url,user,password);
            System.out.println("successfully in creating the connection");
        }catch (SQLException e){
            System.out.println("Error in getting connection..."+e.getMessage());
        }
        return myCon;
    }



}