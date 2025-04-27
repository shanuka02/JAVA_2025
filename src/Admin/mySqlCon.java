package Admin;

import java.sql.*;

public class mySqlCon {

    Connection con;



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
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/fot";
        String user = "root";
        String password = "1234";

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return null;
    }
}

