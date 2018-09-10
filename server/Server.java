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
            System.out.println("Enter 1 : View all users");
            System.out.println("Enter 2 : View all Matches");
            System.out.println("Enter 0 : Quit");

            choice = in.nextInt();
            rs = null;
            statement1 = null;

            // Switch on option
            switch(choice) {
                case 1:
                    try {
                        statement1 = conn.createStatement();
                        statement1.executeUpdate("DROP VIEW IF EXISTS AllUsers");
                        statement1.executeUpdate(
                            "CREATE VIEW AllUsers AS SELECT Users.*, Hobbies.* , Languages.English, Languages.Mandarin, Languages.Malay, Languages.Tamil, Media.ProfilePicBytes, Media.ProfilePicPath " + 
                            "FROM Users, Hobbies, Languages, Media " + 
                            "WHERE Users.ID=Hobbies.UserID AND Users.ID=Languages.UserID AND Users.ID=Media.UserID ");

                        rs = statement1.executeQuery("SELECT * FROM AllUsers");

                        ResultSetMetaData rsMetaData = rs.getMetaData();
                        int numberOfColumns = rsMetaData.getColumnCount();

                        // Column data
                        while(rs.next()){
                            for (int i = 1; i < numberOfColumns + 1; i++) {
                                System.out.printf("%-17s: %s \n", rsMetaData.getColumnName(i), rs.getString(rsMetaData.getColumnName(i)));
                            } 
                            System.out.println();
                        }
                        System.out.println();
     
                    }catch(SQLException e){
                        e.printStackTrace();
                        
                    } break;
                case 2:
                    try {
                        statement1 = conn.createStatement();
                        rs = statement1.executeQuery(
                            "SELECT User.Name AS User, Partner.Name AS Partner " + 
                            "FROM Users AS User, Users AS Partner, Matches " + 
                            "WHERE User.ID=Matches.UserID AND Partner.ID=Matches.MatchID");

                        System.out.println("User" + "\t" + "Match");
                        while (rs.next()){
                            String str = rs.getString("User")+ "\t" +rs.getString("Partner");
                            System.out.println(str);
                        }
                        System.out.println();
                
                    }catch(SQLException e){
                        e.printStackTrace();
                        
                    } break;
                case 0:
                    return;  
            } 
        }
    }
}