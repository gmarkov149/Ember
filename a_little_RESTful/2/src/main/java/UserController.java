import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
// For DB
import java.sql.*;
import java.util.Scanner;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class UserController 
{
	private ArrayList<User> system;
	private int id = 1; 
	private ResultSet rs;
	private Statement statement;

	// Define Data Source
	private MysqlDataSource dataSource;
	private Connection conn;

	public UserController()
	{
		system = new ArrayList<User>();

		// Define Data Source
        dataSource = new MysqlDataSource();
        // DB user
        dataSource.setUser("root");
        // DB password
        dataSource.setPassword("CZ3002");
        dataSource.setServerName("localhost");
        // DB name
        dataSource.setDatabaseName("cz3002");
        conn = null;

        // Establish a connection
        try {
            conn = dataSource.getConnection();
            // save the current id even if the program is restrated
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT ID FROM users ORDER BY ID DESC LIMIT 1");
            rs.next();
            id = rs.getInt(1);
            id++;
            System.out.println(id);
        }
        catch(Exception e) {
            e.printStackTrace();
            id = 1;
        }
	}

	// Return all users from database
	public ArrayList<User> getSystem() {
		return system;
	}

	// Converts a user from the database to a User object to be passed to front-end
	public User toUserObject(String username) {
		try {
		    statement = conn.createStatement();
		    rs = statement.executeQuery(
		        "SELECT Users.*, Hobbies.* " + 
		        "FROM Users, Hobbies " + 
		        "WHERE Users.Username='" + username + "' AND Users.ID=Hobbies.UserID");

		    // Get user details
		    rs.next();

		    // Convert hobbies to correctly formatted hobby string 
		    String hobbies = String.format("%s %s %s %s %s %s %s %s %s %s %s", 
		    	rs.getString("Fitness"), 
		    	rs.getString("Music"), 
		    	rs.getString("Dancing"), 
		     	rs.getString("Reading"), 
		     	rs.getString("Walking"), 
		     	rs.getString("Traveling"), 
		     	rs.getString("Eating"), 
		     	rs.getString("Crafts"), 
		     	rs.getString("Fishing"), 
		     	rs.getString("Hiking"), 
		     	rs.getString("Animals")
		     );

		    // Create user object from database
		    User userObject = new User(
		    	rs.getString("Username"), 
		    	rs.getString("Password"), 
		    	rs.getString("Email"), 
		    	rs.getString("Name"), 
		    	rs.getString("DOB"), 
		    	hobbies, 
		    	rs.getString("Gender"), 
		    	rs.getString("Location"), 
		    	rs.getString("Languages"), 
		    	rs.getString("ProfilePicBytes"),
		    	Boolean.parseBoolean(rs.getString("InterestedInMen")), 
		    	Boolean.parseBoolean(rs.getString("InterestedInWomen"))
		    );
		
		    return userObject;

		} catch(SQLException e){ e.printStackTrace();
								//added return null; to make it force return something if needed
								return null;
								} 
	}

	// Add a user to the database
	public void addUser(User user)
	{
		statement = null;

		try {
			System.out.println("Here!");
		    statement = conn.createStatement();
		    statement.executeUpdate(
		        "INSERT INTO users " + 
		        "VALUES " +
		        String.format("(%d,'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
		        	id, 
		        	user.getUsername(), 
		        	user.getPassword(), 
		        	user.getName(), 
		        	user.getEmail(), 
		        	user.getDob(), 
		        	user.getGender(), 
		        	user.getLocation(), 
		        	user.getLanguages(), 
		        	user.getProfilePicBytes(), 
		        	user.isInterestedInMen(), 
		        	user.isInterestedInWomen())
		        );
		    
		    boolean[] hobbies = user.getParsedHobbies();
		    statement.executeUpdate(
		        "INSERT INTO Hobbies " + 
		        "VALUES " +
		        String.format("(%d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", 
		        	id, 
		        	hobbies[0], 
		        	hobbies[1], 
		        	hobbies[2], 
		        	hobbies[3], 
		        	hobbies[4], 
		        	hobbies[5], 
		        	hobbies[6], 
		        	hobbies[7], 
		        	hobbies[8], 
		        	hobbies[9], 
		        	hobbies[10])
		        );
		} catch(SQLException e){ e.printStackTrace(); }
		// Increment ID for next user added. What if the backend is restarted? Will have to get last id.
		id += 1;
	}

	// Check if a username is available 
	public boolean checkIfAvailable(String username) {
		rs = null;
		statement = null;

		try {
			// Get table of usernames in DB
		    statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    rs = statement.executeQuery(
		        "SELECT Users.Username " + 
				"FROM Users " + 
				"WHERE Users.Username='" + username + "'");
		

		// ResultSet is empty, Username is available
		if (!rs.first()) {
		    return true;
		}
		return false;
		//moved the following try catch line to encapsulate the exception
		} catch(SQLException e){ e.printStackTrace();
								return false;
								}
	}

	// Check and return an existing user
	public User userExists(String username, String password) {
		rs = null;
		statement = null;

		try {
			// Get table of usernames and passwords in DB
		    statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    rs = statement.executeQuery(
		        "SELECT Users.Username, Users.Password " + 
				"FROM Users " +
				"WHERE Users.Username='" + username + "' AND Users.Password='" + password + "'");
		

		// ResultSet is empty, user DNE
		if (!rs.first()) {
		    return null;
		}
		// Return user object, convert from DB result
		return toUserObject(username);
		//moved the following try catch line to encapsulate the exception
		} catch(SQLException e){ e.printStackTrace();
		return null;} 
	}

	// Find and return a user object from the database
	public User findUser(String username) {
		rs = null;
		statement = null;

		try {
			// Get table of usernames in DB
		    statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    rs = statement.executeQuery(
		        "SELECT Users.Username " + 
				"FROM Users " + 
				"WHERE Users.Username='" + username + "'");
		

		// ResultSet is empty, user DNE
		if (!rs.first()) {
		    return null;
		}
		// Return user object, convert from DB result
		return toUserObject(username);
		//moved the following try catch line to encapsulate the exception
		} catch(SQLException e){ e.printStackTrace();
		return null;}
	}

	// Update user by replacing its details with the new details. NO MORE SWAPPING INDECIES
	public void editUser(User toEdit) {
		rs = null;
		statement = null;

		try {
		    statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		    // First update User table
		    statement.executeUpdate(
		        "UPDATE Users " + 
		        "SET " + 
		        String.format("Username='%s', Password='%s', Name='%s', Email='%s', DOB='%s', Gender='%s', Location='%s', Languages='%s', ProfilePicBytes='%s', InterestedInMen='%s', InterestedInWomen='%s' ", 
		        	toEdit.getUsername(), 
		        	toEdit.getPassword(), 
		        	toEdit.getName(), 
		        	toEdit.getEmail(), 
		        	toEdit.getDob(), 
		        	toEdit.getGender(), 
		        	toEdit.getLocation(), 
		        	toEdit.getLanguages(), 
		        	toEdit.getProfilePicBytes(), 
		        	toEdit.isInterestedInMen(), 
		        	toEdit.isInterestedInWomen()) + 
		        "WHERE Users.Username='" + toEdit.getUsername() + "'");

		    // Get UserID
		    rs = statement.executeQuery(
		        "SELECT Users.ID " + 
				"FROM Users " + 
				"WHERE Users.Username='" + toEdit.getUsername() + "'");
		    rs.next();

		    // Update Hobbies table
		    boolean[] hobbies = toEdit.getParsedHobbies();
		    statement.executeUpdate(
		        "UPDATE Hobbies " + 
		        "SET " +
		        String.format("Fitness='%s', Music='%s', Dancing='%s', Reading='%s', Walking='%s', Traveling='%s', Eating='%s', Crafts='%s', Fishing='%s', Hiking='%s', Animals='%s' ", 
		        	hobbies[0], 
		        	hobbies[1], 
		        	hobbies[2], 
		        	hobbies[3], 
		        	hobbies[4], 
		        	hobbies[5], 
		        	hobbies[6], 
		        	hobbies[7], 
		        	hobbies[8], 
		        	hobbies[9], 
		        	hobbies[10]) + 
		    	"WHERE Hobbies.UserID=" + rs.getString("ID"));

		} catch(SQLException e){ e.printStackTrace(); }
	}

	public void getPotentialMatches(User user)
	{
		int matchScore = 0;
		ArrayList<Key_Value_Pair> temp = new ArrayList<Key_Value_Pair>();
		for(int i=0;i<system.size();i++)
		{
			temp.add(i, new Key_Value_Pair());
			temp.get(i).setKey(system.get(i).getUsername());
			
		}
		for(int j=0;j<system.size();j++)
		{
			for(int i=0;i<12;i++)
			{
				if((user.getParsedHobbies())[i] == (system.get(j).getParsedHobbies())[i])
				{
					matchScore++;
				}
			}
			temp.get(j).setValue(matchScore);
			system.get(j).getPotential().add(new Key_Value_Pair(user.getUsername(), matchScore));
			matchScore = 0;
		}
		user.setPotential(temp);
	}
	public void updatePotential(User current)
	{
		int newMatchScore = 0;
		ArrayList<Key_Value_Pair> temp = new ArrayList<Key_Value_Pair>();
		for(User updatee:system)
		{
			if(updatee != current)
			{
				for(int i=0;i<12;i++)
				{
					if((current.getParsedHobbies())[i] == (updatee.getParsedHobbies())[i])
					{
						newMatchScore++;
					}
				}
				temp = updatee.getPotential();
				for(int count = 0; count<temp.size(); count++)
				{
					if(temp.get(count).getKey().equals(current.getUsername()))
					{
						temp.get(count).setValue(newMatchScore);
					}
				}
				newMatchScore = 0;
			}
		}
	}
	//manual reset back to empty tables PURELY FOR TESTING
	public void reset()
	{
		statement = null;
		id = 1;
		try {
		    statement = conn.createStatement();
		    statement.executeUpdate("DROP TABLE IF EXISTS SuggestedPartners");
		    statement.executeUpdate("DROP TABLE IF EXISTS LikedUsers");
		    statement.executeUpdate("DROP TABLE IF EXISTS Hobbies");
		    statement.executeUpdate("DROP TABLE IF EXISTS Users");
		    statement.executeUpdate("CREATE TABLE `Users` (\r\n" + 
		    		"	`ID` 		int NOT NULL,\r\n" + 
		    		"	`Username` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`Password` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`Name` 		varchar(50) NOT NULL,\r\n" + 
		    		"	`Email` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`DOB` 		char(10) NOT NULL,\r\n" + 
		    		"	`Gender` 	varchar(10) NOT NULL,\r\n" + 
		    		"	`Location` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`Languages` 		varchar(50) NOT NULL,\r\n" + 
		    		"	`ProfilePicBytes` 	blob NOT NULL,\r\n" + 
		    		"	`InterestedInMen` 	varchar(10) NOT NULL DEFAULT \"0\",\r\n" + 
		    		"	`InterestedInWomen` varchar(10) NOT NULL DEFAULT \"0\",\r\n" + 
		    		"	PRIMARY KEY (`ID`)\r\n" + 
		    		") ");
		    statement.executeUpdate("CREATE TABLE `Hobbies` (\r\n" + 
		    		"	`UserID` 	int NOT NULL,\r\n" + 
		    		"	`Fitness` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Music` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Dancing` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Reading` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Walking` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Traveling` varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Eating` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Crafts` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Fishing` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Hiking` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	`Animals` 	varchar(5) NOT NULL DEFAULT 'false',\r\n" + 
		    		"	PRIMARY KEY (`UserID`),\r\n" + 
		    		"	FOREIGN KEY (`UserID`) REFERENCES Users(`ID`)\r\n" + 
		    		")");        
		    statement.executeUpdate("CREATE TABLE `SuggestedPartners` (\r\n" + 
		    		"	`UserID` 	int NOT NULL,\r\n" + 
		    		"	`PartnerID` int NOT NULL,\r\n" + 
		    		"	PRIMARY KEY (`UserID`, `PartnerID`),\r\n" + 
		    		"	FOREIGN KEY (`UserID`) REFERENCES Users(`ID`),\r\n" + 
		    		"	FOREIGN KEY (`PartnerID`) REFERENCES Users(`ID`)\r\n" + 
		    		")");
		    statement.executeUpdate("CREATE TABLE `LikedUsers` (\r\n" + 
		    		"	`UserID` 	int NOT NULL,\r\n" + 
		    		"	`LikesID` 	int NOT NULL,\r\n" + 
		    		"	PRIMARY KEY (`UserID`, `LikesID`),\r\n" + 
		    		"	FOREIGN KEY (`UserID`) REFERENCES Users(`ID`),\r\n" + 
		    		"	FOREIGN KEY (`LikesID`) REFERENCES Users(`ID`)\r\n" + 
		    		")");
		    
			
		} catch(SQLException e){ e.printStackTrace(); }
	}
}
