import java.util.ArrayList;
import java.util.LinkedList;

public class UserController 
{
	private ArrayList<User> system;
	public UserController()
	{
		system = new ArrayList<User>();
	}
	public ArrayList<User> getSystem() {
		return system;
	}
	public void addUser(User user)
	{
		system.add(user);
		
	}
	public boolean userExists(String username, String password)
	{
		for(User user: system)
		{
			if(user.getUsername().equals(username) && user.getPassword().equals(password))
			{
				return true;
			}
		}
		return false;
	}
	public User findUser(String username, String password)
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
	public void initCurrentMatched(User user)
	{
		if(system.get(1) != null)
		{
			user.getMatched().add(system.get(1));
		}
		
	}
}
