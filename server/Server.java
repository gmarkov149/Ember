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
        boolean printAllProfile;
        boolean printSingleProfile;
        int userID;
        // Menu
        while(true) {
            System.out.println("Choose an option");
            System.out.println("Enter 1 : View all user profiles");
            System.out.println("Enter 2 : View all Matches");
            System.out.println("Enter 3 : View a users profile");
            System.out.println("Enter 4 : Edit a users profile");
            System.out.println("Enter 5 : Create new user");
            System.out.println("Enter 6 : Like a user");
            System.out.println("Enter 7 : View a users suggested matches");
            System.out.println("Enter 8 : View a users likes");
            System.out.println("Enter 9 : View a users matches");
            System.out.println("Enter 10 : View a users chat");
            System.out.println("Enter 11 : Send a message to another user");
            System.out.println("Enter 12 : Simulate Ember");
            System.out.println("Enter 0 : Quit");

            choice = in.nextInt();
            rs = null;
            statement1 = null;
            printAllProfile = false;
            printSingleProfile = false;

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
                        printAllProfile = true;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // View all matches
                case 2:
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT Matches.UserID, Matches.MatchID, Matches.ChatPath " + 
                            "FROM Users AS User, Users AS Partner, Matches " + 
                            "WHERE User.ID=Matches.UserID AND Partner.ID=Matches.MatchID");
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // View a users profile
                case 3:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT Users.*, Hobbies.* , Languages.English, Languages.Mandarin, Languages.Malay, Languages.Tamil " + 
                            "FROM Users, Hobbies, Languages " + 
                            "WHERE Users.ID=Hobbies.UserID AND Users.ID=Languages.UserID AND Users.ID=" + userID);
                        printSingleProfile = true;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // Edit a users profile
                case 4:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    System.out.print("Enter users new location: ");
                    String userLocation = in.next();
                    try {
                        statement1 = conn.createStatement();
                        statement1.executeUpdate(
                            "UPDATE Users " + 
                            "SET Location='" + userLocation + "' " + 
                            "WHERE Users.ID=" + userID);
                        continue;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // Create a new user
                case 5:
                    // System.out.print("Enter users ID: ");
                    // userID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        statement1.executeUpdate(
                            "INSERT INTO Users " + 
                            "VALUES " +
                            "(-1,'ldelago','Lance Delago', 'ldelago@gmail.com', '08/18/1998', 'Male', 'Dallas', 900, '/fake/path/pic.jpg', '0', '1')");
                        statement1.executeUpdate(
                            "INSERT INTO Hobbies " + 
                            "VALUES " +
                            "(-1, '0', '1', '0', '0', '1', '1', '1', '0', '0', '0', '1')");
                        statement1.executeUpdate(
                            "INSERT INTO Languages " + 
                            "VALUES " +
                            "(-1, '1', '0', '0', '1')");
                        continue;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // Like a user
                case 6:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    System.out.print("User likes (Enter users ID): ");
                    int likeID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        statement1.executeUpdate(
                            "INSERT INTO LikedUsers " + 
                            "VALUES " + 
                            String.format("(%d, %d)", userID, likeID ));
                        continue;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // View a users suggested matches
                case 7:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT * " + 
                            "FROM SuggestedPartners " + 
                            "WHERE SuggestedPartners.UserID=" + userID);
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // View a users likes
                case 8:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT * " + 
                            "FROM LikedUsers " + 
                            "WHERE LikedUsers.UserID=" + userID);
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // View a users matches
                case 9:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT * " + 
                            "FROM LikedUsers " + 
                            "WHERE LikedUsers.UserID=" + userID + " AND LikedUsers.LikesID IN " + 
                                "(SELECT UserID " + 
                                "FROM LikedUsers " +
                                "WHERE LikedUsers.LikesID=" + userID + ")");
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // View a users chat
                case 10:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    System.out.print("Enter users match ID: ");
                    int matchID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT * " + 
                            "FROM Chat " + 
                            "WHERE Chat.SenderID=" + userID + " AND Chat.ReceiverID=" + matchID + " OR Chat.SenderID=" + matchID + " AND Chat.ReceiverID=" + userID + " " + 
                            "ORDER BY Datestamp ASC, Timestamp ASC");
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // Send a message to another user
                case 11:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    System.out.print("Enter users match ID: ");
                    matchID = in.nextInt();
                    System.out.print("Enter message: ");
                    String message = in.next();
                    try {
                        statement1 = conn.createStatement();
                        statement1.executeUpdate(
                            "INSERT INTO Chat " + 
                            "VALUES " + 
                            String.format("(%d, %d, '2018-10-06', '12:00:00', '%s')", userID, matchID, message));
                        continue;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // Simulate Ember
                case 12:
                    System.out.print("Enter users ID: ");
                    userID = in.nextInt();
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT Users.*, Hobbies.* , Languages.English, Languages.Mandarin, Languages.Malay, Languages.Tamil " + 
                            "FROM Users, Hobbies, Languages " + 
                            "WHERE Users.ID=Hobbies.UserID AND Users.ID=Languages.UserID AND Users.ID=" + userID);
                        printSingleProfile = true;
                    } catch(SQLException e){ e.printStackTrace(); } break;
                // Exit
                case 0:
                    return;  
            } 

            // Display result set
            try {
                // Get metadata of query
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int numberOfColumns = rsMetaData.getColumnCount();

                if(printAllProfile) {
                    while(rs.next()){
                        for (int i = 1; i < numberOfColumns + 1; i++) {
                            System.out.printf("%-17s: %s \n", rsMetaData.getColumnName(i), rs.getString(rsMetaData.getColumnName(i)));
                        } 
                        System.out.println();
                    }
                }

                else if(printSingleProfile) {
                    rs.next();
                    for (int i = 1; i < numberOfColumns + 1; i++) {
                        System.out.printf("%-17s: %s \n", rsMetaData.getColumnName(i), rs.getString(rsMetaData.getColumnName(i)));
                    } 
                }

                else {
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
                }
                System.out.println();
            } catch(SQLException e){ e.printStackTrace(); }
        }
    }

    /*
    Matching algorithm
    Input: A user's ID and the database connection
    Output: A list of user IDs that are suggested to the input user
    */
    public int[] getSuggestedMatches(int userID, Connection conn) {
        int[] suggestions = {1,1,1,1,1};
        
        return suggestions;
    }
}