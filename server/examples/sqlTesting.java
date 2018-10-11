// Daniel Delago

import java.sql.*;
import java.util.Scanner;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class sqlTesting {
    
    public static void main(String[] args) {

        // Define Data Source
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("0596");
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("cz3002");
        Connection conn = null;

        // Establish a connection
        try {
            conn = dataSource.getConnection();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // Handle user input and queries
        ResultSet rs = null;
        Statement statement = null;
        
        try {
            // Get table of usernames and passwords in DB
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(
                "SELECT Users.Username " + 
                "FROM Users " + 
                "WHERE Users.Username='ddelago'");
            

            if (!rs.first()) {
                System.out.println("ResultSet is empty");
            }
            else {
                System.out.println("Not empty, username exists");
                System.out.println(rs.getString("Username"));
            }

        } catch(SQLException e){ e.printStackTrace(); }
    }
}