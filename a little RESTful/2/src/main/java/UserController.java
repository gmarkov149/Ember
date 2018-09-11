import java.util.LinkedList;

public class UserController 
{
	private LinkedList<User> system;
	public UserController()
	{
		system = new LinkedList<User>();
	}
	public LinkedList<User> getSystem() {
		return system;
	}
	public void addUser(User user)
	{
		system.add(user);
		
	}

}
