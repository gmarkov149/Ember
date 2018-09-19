import java.util.ArrayList;


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
}
