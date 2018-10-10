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
	provateConnection conn;

	public UserController()
	{
		system = new ArrayList<User>();

		// Define Data Source
        dataSource = new MysqlDataSource();
        // DB user
        dataSource.setUser("root");
        // DB password
        dataSource.setPassword("0596");
        dataSource.setServerName("localhost");
        // DB name
        dataSource.setDatabaseName("cz3002");
        conn = null;

        // Establish a connection
        try {
            conn = dataSource.getConnection();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}
	// Return all users from database
	public ArrayList<User> getSystem() {
		return system;
	}
	public void addUser(User user)
	{
		system.add(user);

		rs = null;
		statement = null;

		try {
		    statement = conn.createStatement();
		    statement.executeUpdate(
		        "INSERT INTO Users " + 
		        "VALUES " +
		        String.format("(%d,'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')"), 
		        id, user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getDob(), user.getGender(), user.getLocation(), user.getLanguages(), user.getProfilePicBytes(), user.isInterestedInMen(), user.isInterestedInWomen());
		    
		    boolean[] hobbies = user.getParsedHobbies();
		    statement.executeUpdate(
		        "INSERT INTO Hobbies " + 
		        "VALUES " +
		        String.format("(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"), 
		        hobbies[0], hobbies[1], hobbies[2], hobbies[3], hobbies[4], hobbies[5], hobbies[6], hobbies[7], hobbies[8], hobbies[9], hobbies[10], hobbies[11]);
		    continue;
		} catch(SQLException e){ e.printStackTrace(); } break;
		id += 1
	}
	public boolean checkIfAvailable(String username)
	{
		for(User user:system)
		{
			if(user.getUsername().equals(username))
			{
				return false;
			}
		}
		return true;
	}
	public User userExists(String username, String password)
	{
		for(User user: system)
		{
			if(user.getUsername().equals(username) && user.getPassword().equals(password))
			{
				return user;
			}
		}
		return null;
	}
	public User findUser(String username)
	{
		for(User user: system)
		{
			if(user.getUsername().equals(username))
			{
				return user;
			}
		}
		return null;
	}
	public void initCurrentMatched(User user)
	{
		
		if(system.get(1) != null && !system.get(1).equals(user))
		{
			user.getMatched().add(system.get(1));
		}
		else
		{
			user.getMatched().add(system.get(0));
		}
		
		
		
	}
	public void editUser(User toEdit)
	{
		int currentIndex = 0;
		for(User user: system)
		{
			if(user.getUsername().equals(toEdit.getUsername()))
			{
				currentIndex = system.indexOf(user);
				break;
			}
		}
		system.set(currentIndex, toEdit);
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
}
