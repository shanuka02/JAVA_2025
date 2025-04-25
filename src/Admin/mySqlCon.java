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
}

