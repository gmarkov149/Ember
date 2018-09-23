// Daniel Delago

import java.sql.*;
import java.util.Scanner;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Server {
    
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
        Scanner in = new Scanner(System.in);
        ResultSet rs;
        Statement statement1;
        int choice;
        
        // Menu
        while(true) {
            System.out.println("Choose an option");
            System.out.println("Enter 1 : View all user profiles");
            System.out.println("Enter 2 : View all Matches");
            System.out.println("Enter 3 : View a users profile");
            System.out.println("Enter 4 : Edit a users profile");
            System.out.println("Enter 5 : View a users suggested matches");
            System.out.println("Enter 6 : View a users likes");
            System.out.println("Enter 7 : View a users matches");
            System.out.println("Enter 8 : View a users matched profile");
            System.out.println("Enter 9 : View a users chat");
            System.out.println("Enter 10 : Send a message to another user");
            System.out.println("Enter 11 : Simulate Ember");
            System.out.println("Enter 0 : Quit");

            choice = in.nextInt();
            rs = null;
            statement1 = null;

            // Query on option
            switch(choice) {
                // View all user profiles
                case 1:
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT Users.*, Hobbies.* , Languages.English, Languages.Mandarin, Languages.Malay, Languages.Tamil " + 
                            "FROM Users, Hobbies, Languages " + 
                            "WHERE Users.ID=Hobbies.UserID AND Users.ID=Languages.UserID");

                        // Get metadata of query
                        ResultSetMetaData rsMetaData = rs.getMetaData();
                        int numberOfColumns = rsMetaData.getColumnCount();

                        // For each row
                        while(rs.next()){
                            for (int i = 1; i < numberOfColumns + 1; i++) {
                                System.out.printf("%-17s: %s \n", rsMetaData.getColumnName(i), rs.getString(rsMetaData.getColumnName(i)));
                            } 
                            System.out.println();
                        }
                        System.out.println();
                        continue;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // View all matches
                case 2:
                    try {
                        statement1 = conn.createStatement();
                        statement1.executeUpdate("DROP VIEW IF EXISTS AllMatches");
                        statement1.executeUpdate(
                            "CREATE VIEW AllMatches AS SELECT Matches.UserID, Matches.MatchID, Matches.ChatPath " + 
                            "FROM Users AS User, Users AS Partner, Matches " + 
                            "WHERE User.ID=Matches.UserID AND Partner.ID=Matches.MatchID");

                        rs = statement1.executeQuery("SELECT * FROM AllMatches");
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // View a users profile
                case 3:
                    System.out.print("Enter users ID: ");
                    int userID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT Users.*, Hobbies.* , Languages.English, Languages.Mandarin, Languages.Malay, Languages.Tamil " + 
                            "FROM Users, Hobbies, Languages " + 
                            "WHERE Users.ID=Hobbies.UserID AND Users.ID=Languages.UserID AND Users.ID=" + userID);

                        // Get metadata of query
                        ResultSetMetaData rsMetaData = rs.getMetaData();
                        int numberOfColumns = rsMetaData.getColumnCount();

                        rs.next();
                        for (int i = 1; i < numberOfColumns + 1; i++) {
                            System.out.printf("%-17s: %s \n", rsMetaData.getColumnName(i), rs.getString(rsMetaData.getColumnName(i)));
                        } 
                        System.out.println();
                        continue;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                case 0:
                    return;  
            } 

            try {

                // Get metadata of query
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int numberOfColumns = rsMetaData.getColumnCount();

                // Print column names
                for (int i = 1; i < numberOfColumns + 1; i++) {
                    System.out.printf("%-17s ", rsMetaData.getColumnName(i));
                } 
                System.out.println();

                // For each row
                while(rs.next()){
                    for (int i = 1; i < numberOfColumns + 1; i++) {
                        System.out.printf("%-17s ", rs.getString(rsMetaData.getColumnName(i)));
                    } 
                    System.out.println();
                }
                System.out.println();
            } catch(SQLException e){ e.printStackTrace(); }
        }
    }
}