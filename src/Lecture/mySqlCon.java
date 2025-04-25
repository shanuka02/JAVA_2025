package Lecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mySqlCon {
    Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/fot";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";





    public  Connection con() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //register the import driver

            //java sql class- Connection (java database connectivity jdbc)
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fot", "root", "1234"); // connection establish with mysql


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
            return null;
        }
    }


}
