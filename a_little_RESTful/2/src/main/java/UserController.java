import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
// For DB
import java.sql.*;
import java.util.stream.Stream;

import com.mysql.cj.jdbc.MysqlDataSource;

public class UserController 
{
	private ArrayList<User> system;
	private int id = 1; 
	private ResultSet rs;
	private ResultSet secondRS;
	private ResultSet specialQueryRS;
	private ResultSet tempRS;
	private ResultSet idk;
	private Statement statement;
	private Statement otherStatement;
	private final double DISTANCE_THRESHOLD = 0.05;

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
        	dataSource.setServerTimezone("UTC");
            conn = dataSource.getConnection();
            // save the current id even if the program is restrated
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT ID FROM users ORDER BY ID DESC LIMIT 1");
            rs.next();
            id = rs.getInt(1);
            id++;
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
		} catch(SQLException e){ e.printStackTrace(); return false; }
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

	// Get a users potential matches, this is where matching algoritm will be
	// The user.potential will be updated
	public void updatePotentialMatches(User user) {
		// Remember to ignore matched users
		// 1. Apply filter to an SQL query, interested in Men or Women, Language.
		// 2. Get list filtered usernames and compare to current user for score. Ignore current user.
		// 3. Add remaining users into the SuggestedPartners table with their score for the current user.
		rs = null;
		statement = null;

		// Remember to remove from users potenial list
		try {
		    statement = conn.createStatement();
		    rs = statement.executeQuery(
		        "SELECT Users.Username,Users.Gender,Users.Location " +
		        "FROM Users " +
		        String.format("WHERE Users.Username!='%s' AND Users.Languages='%s'", 
		        	user.getUsername(), user.getLanguages()));

		    // Get filtered users
		    ArrayList<String> filteredUsers = new ArrayList<String>();
		    Double userLat = null, userLon = null;
		    if (user.getLocation() != null && !user.getLocation().isEmpty()) {
                String[] userLatLon = user.getLocation().split(",");
                userLat = Double.parseDouble(userLatLon[0]);
                userLon = Double.parseDouble(userLatLon[1]);
            }
		    while(rs.next()) {
                Double otherUserLat = null, otherUserLon = null;
                String otherLocationStr = rs.getString("Location");
                if (otherLocationStr != null && !otherLocationStr.isEmpty()) {
                    String[] otherUserLatLon = otherLocationStr.split(",");
                    otherUserLat = Double.parseDouble(otherUserLatLon[0]);
                    otherUserLon = Double.parseDouble(otherUserLatLon[1]);
                }
                if (user.getLocation() != null && otherUserLat != null && otherUserLon != null &&
                        euclideanDistance(userLat, userLon, otherUserLat, otherUserLon) > DISTANCE_THRESHOLD) {
                    continue;
                }
		    	if(user.isInterestedInMen()) {
		    		
		    		if((rs.getString("Gender")).equals("Male"))
						filteredUsers.add(rs.getString("Username"));		    		
		    	}

		    	else if(user.isInterestedInWomen()) {

		    		if((rs.getString("Gender")).equals("Female")) {
		    			filteredUsers.add(rs.getString("Username"));
		    		}
		    	}  

		    }
		    // For every filtered user, compare and apply matching
	    	for(String compareTo:filteredUsers) {
	    		tempRS = null;
	    		statement = null;
	    		statement = conn.createStatement();
	    		System.out.println(compareTo);
				System.out.println(user.getUsername());
				tempRS = statement.executeQuery("SELECT COUNT(*) FROM SuggestedPartners WHERE SuggestedPartners.Username = '" + user.getUsername() + "' AND SuggestedPartners.PartnerUsername = '" + compareTo + "'");
				User comparedUser = toUserObject(compareTo);
				tempRS.next();
				if(tempRS.getInt(1)>0)
				{
					continue;
				}
				
				if(comparedUser == user)
				{
					continue;
				}
				
				int score = 0;
				//System.out.println("Here");
				user.parseHobbies();
				comparedUser.parseHobbies();
				for(int i = 0; i < 11; i++) {
					
					if( user.getParsedHobbies()[i] == comparedUser.getParsedHobbies()[i] )
						score++;
					
				}
				
				
				statement.executeUpdate(
				    "INSERT INTO SuggestedPartners " + 
				    "VALUES " + 
				    String.format("('%s', '%s', %d , 'false')", user.getUsername(), comparedUser.getUsername(), score ));

	    	}	

		} catch(SQLException e){ e.printStackTrace(); } 
	}

	private double euclideanDistance(Double x1, Double y1, Double x2, Double y2) {
	    return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
    }

	// Add a new match to the current user (BOTH USERS)
	// The user.matched will be updated
	public void updateMatches(User current, User match) {
		rs = null;
		statement = null;

		// Remember to remove from users potenial list
		try {
		    statement = conn.createStatement();
		    statement.executeUpdate(
		        "INSERT INTO LikedUsers " + 
		        "VALUES " + 
		        String.format("('%s', '%s')", 
		        	current.getUsername(), match.getUsername() ));
		    executeOneStatement(current.getUsername(), match.getUsername());
//		    PreparedStatement stmt = conn.prepareStatement("DELETE " + 
//			        "FROM SuggestedPartners " + 
//			        "WHERE Username = ? AND PartnerUsername =?");
//		    stmt.setString(1, current.getUsername());
//		    stmt.setString(2, match.getUsername());
//		    System.out.println(stmt.execute());
		} catch(SQLException e){ e.printStackTrace(); } 
	}
	private void executeOneStatement(String current, String match)
	{
		rs = null;
		otherStatement = null;

		// Remember to remove from users potenial list
		try {
			otherStatement = conn.createStatement();
		    
		    
		    otherStatement.executeUpdate(
			        "DELETE FROM SuggestedPartners " +
			        "WHERE Username = '" + current + "' AND PartnerUsername ='" + match + "'");

		} catch(SQLException e){ e.printStackTrace(); } 
	}
	// Get chat history of current user and match
	public ArrayList<String> getChat(String current, String match, int startIndex) {
		rs = null;
		statement = null;

		// Format is:
		// "date time sender receiver message"
		// '2015-10-06 13:30:00 Daniel Sean Hello my dude'
		ArrayList<String> messages = new ArrayList<String>();
		
		try {
		    statement = conn.createStatement();
		    rs = statement.executeQuery(
		        "SELECT * " + 
		        "FROM Chat " + 
		        "WHERE Chat.Sender='" + current + "' AND Chat.Receiver='" + match +
		        "' OR Chat.Sender='" + match + "' AND Chat.Receiver='" + current +
		        "' ORDER BY Datestamp ASC, Timestamp ASC" +
				" LIMIT " + startIndex + ", 18446744073709551615");

		    // Add every message to array list
		    while(rs.next()) {
		    	messages.add(String.format("%s|%s|%s|%s|%s", 
		        	rs.getString("Datestamp"), 
		        	rs.getString("Timestamp"),
		        	rs.getString("Sender"),
		        	rs.getString("Receiver"),
		        	rs.getString("Message"))
		    	);
		    }

		    return messages;

		} catch(SQLException e){ e.printStackTrace(); return new ArrayList<>();}
	}

	// Enter new message into DB
	public void sendMessage(String sender, String receiver, String message, String date, String time ) {
		statement = null;
		try {
		    statement = conn.createStatement();
		    statement.executeUpdate(
		        "INSERT INTO Chat " + 
		        "VALUES " + 
		        String.format("('%s', '%s', '%s', '%s', '%s' )", 
		        	sender, receiver, date, time, message ));
		} catch(SQLException e){ e.printStackTrace(); } 
	}

	public ArrayList<User> get10Potential(User current, int start, int end)
	{
		//because  we are using the toUserObject method, and that executes a statement into a result set
		//we need another result set to store the results of the statement below
		idk = null;
		otherStatement = null;
		ArrayList<User> temp = new ArrayList<User>();
		// Remember to remove from users potenial list
		try {
				otherStatement = conn.createStatement();
		    
				idk = otherStatement.executeQuery(
		        "SELECT PartnerUsername " + 
		        "FROM SuggestedPartners " +
		        "WHERE SuggestedPartners.Username ='" + current.getUsername() + "' AND SuggestedPartners.Show = 'false' " +
		    	"ORDER BY Score DESC " +
		    	"LIMIT " + end+1 + " ");
				
		    idk.next();
		    /*for(int i=0;i<start;i++)
		    {
		    	if (!idk.next()) {
		    		return temp;
		    	}
		    	
		    }*/
		    do 
		    {
		    	
		    	temp.add(this.toUserObject(idk.getString("PartnerUsername")));
		    	
		    }
		    while(idk.next() && temp.size() < end);
		    
		    return temp;
		} catch(SQLException e){ e.printStackTrace(); return temp;} 
		finally
		{
			return temp;
		}
	}
	public ArrayList<User> getMatches(User current, int start, int end)
	{
		secondRS = null;
		otherStatement = null;
		ArrayList<User> temp = new ArrayList<User>();
		
		try {
			otherStatement = conn.createStatement();
			secondRS = otherStatement.executeQuery(
		        "SELECT LikesUsername " + 
		        "FROM LikedUsers " +
		        "WHERE LikedUsers.Username ='" + current.getUsername() + "' " +
		    	"LIMIT " + end+1 + " ");
			secondRS.next();
		    for(int i=0;i<start;i++)
		    {
		    	secondRS.next();
		    }
		    do
		    {
		    	if(oneMoreQuery(secondRS.getString("LikesUsername"), current.getUsername()))
		    	temp.add(this.toUserObject(secondRS.getString("LikesUsername")));
		    }
		    while(secondRS.next() && temp.size()<=end);
		    return temp;
		} catch(SQLException e){ e.printStackTrace(); return new ArrayList<User>(); } 
	}
	private boolean oneMoreQuery(String match, String current)
	{
		specialQueryRS = null;
		statement = null;
		
		
		try {
		    statement = conn.createStatement();
		    specialQueryRS = statement.executeQuery(
		        "SELECT LikesUsername " + 
		        "FROM LikedUsers " +
		        "WHERE LikedUsers.Username ='" + match + "' ");
		    specialQueryRS.next();
		    if(specialQueryRS.getString("LikesUsername").equals(current))
		    {
		    	return true;
		    }
		    else
		    {
		    	return false;
		    }
		} catch(SQLException e){ e.printStackTrace(); return false; }
	}
	//manual reset back to empty tables PURELY FOR TESTING
	public void reset()
	{
		statement = null;
		id = 1;
		try {
		    statement = conn.createStatement();
			statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
		    statement.executeUpdate("DROP TABLE IF EXISTS SuggestedPartners");
		    statement.executeUpdate("DROP TABLE IF EXISTS LikedUsers");
		    statement.executeUpdate("DROP TABLE IF EXISTS Hobbies");
		    statement.executeUpdate("DROP TABLE IF EXISTS Users");
            statement.executeUpdate("DROP TABLE IF EXISTS Chat");
		    statement.executeUpdate("CREATE TABLE `Users` (\r\n" + 
		    		"	`ID` 		int NOT NULL,\r\n" + 
		    		"	`Username` 	varchar(50) UNIQUE NOT NULL,\r\n" + 
		    		"	`Password` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`Name` 		varchar(50) NOT NULL,\r\n" + 
		    		"	`Email` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`DOB` 		char(10) NOT NULL,\r\n" + 
		    		"	`Gender` 	varchar(10) NOT NULL,\r\n" + 
		    		"	`Location` 	varchar(50),\r\n" + 
		    		"	`Languages` 		varchar(50) NOT NULL,\r\n" + 
		    		"	`ProfilePicBytes` 	mediumblob,\r\n" + 
		    		"	`InterestedInMen` 	varchar(10) NOT NULL DEFAULT \"false\",\r\n" + 
		    		"	`InterestedInWomen` varchar(10) NOT NULL DEFAULT \"false\",\r\n" + 
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
		    		"	`Username` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`PartnerUsername` varchar(50) NOT NULL,\r\n" + 
		    		"	`Score` int NOT NULL DEFAULT 0,\r\n" +
		    		"	`Show` varchar(50) DEFAULT false,\r\n" +
		    		"	PRIMARY KEY (`Username`, `PartnerUsername`),\r\n" + 
		    		"	FOREIGN KEY (`Username`) REFERENCES Users(`Username`),\r\n" + 
		    		"	FOREIGN KEY (`PartnerUsername`) REFERENCES Users(`Username`)\r\n" + 
		    		")");
		    statement.executeUpdate("CREATE TABLE `LikedUsers` (\r\n" + 
		    		"	`Username` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`LikesUsername` varchar(50) NOT NULL,\r\n" + 
		    		"	PRIMARY KEY (`Username`, `LikesUsername`),\r\n" + 
		    		"	FOREIGN KEY (`Username`) REFERENCES Users(`Username`),\r\n" + 
		    		"	FOREIGN KEY (`LikesUsername`) REFERENCES Users(`Username`)\r\n" + 
		    		")");
		    statement.executeUpdate("CREATE TABLE `Chat` (\r\n" + 
		    		"	`Sender` 	varchar(50) NOT NULL,\r\n" + 
		    		"	`Receiver` varchar(50) NOT NULL,\r\n" +
		    		"	`Datestamp` date NOT NULL DEFAULT '0000-00-00',\r\n" +
		    		"	`Timestamp` time NOT NULL,\r\n" +
		    		"	`Message` varchar(255) NOT NULL,\r\n" +   
		    		"	PRIMARY KEY (`Sender`, `Receiver`, `Datestamp`, `Timestamp`, `Message`),\r\n" + 
		    		"	FOREIGN KEY (`Sender`) REFERENCES Users(`Username`),\r\n" + 
		    		"	FOREIGN KEY (`Receiver`) REFERENCES Users(`Username`)\r\n" + 
		    		")");		   

		} catch(SQLException e){ e.printStackTrace(); }
	}
}
