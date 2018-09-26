import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class UserController 
{
	private ArrayList<User> system;
	public UserController()
	{
		system = new ArrayList<User>();
		//Daniel, here you need to add every User that exists in the database to system
		//somehow you also need to save every user whenever the app is exited
		//be careful of the users that already exist in the database
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
	public void getPotentialMatches(User user)
	{
		int matchScore = 0;
		ArrayList<Key_Value_Pair> temp = new ArrayList<Key_Value_Pair>();
		for(int i=0;i<system.size();i++)
		{
			temp.add(i, new Key_Value_Pair());
			temp.get(i).setKey(system.get(i));
			
		}
		for(int j=0;j<system.size();j++)
		{
			for(int i=0;i<10;i++)
			{
				if((user.getParsedHobbies())[i] = (system.get(j).getParsedHobbies())[i])
				{
					matchScore++;
				}
			}
			temp.get(j).setValue(matchScore);
		}
		Collections.sort(temp, new Comparator<Key_Value_Pair>() 
				{
					@Override
					public int compare(Key_Value_Pair n1, Key_Value_Pair n2)
					{
						if(n1.getValue() > n2.getValue())
						{
							return 1;
						}
						if(n1.getValue() < n2.getValue())
						{
							return -1;
						}
						return 0;
					}
				});
		user.setPotential(temp);
	}
}
