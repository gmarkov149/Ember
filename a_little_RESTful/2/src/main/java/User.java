import java.util.ArrayList;
import java.util.Scanner;

public class User 
{
	private String username;
	private String password;
	private String email;
	private String name;
	private String dob;
	private String hobbies;
	private boolean[] parsedHobbies;
	private String gender;
	private String location;
	public boolean[] getParsedHobbies() {
		return parsedHobbies;
	}
	private String languages;
	private String profilePicBytes;
	private boolean interestedInMen;
	private boolean interestedInWomen;
	private ArrayList<User> matched;
	private ArrayList<Key_Value_Pair> potential;
	public User()
	{
		matched = new ArrayList<>();
		potential = new ArrayList<>();
	}
	

	


	public ArrayList<Key_Value_Pair> getPotential() {
		return potential;
	}





	public void setPotential(ArrayList<Key_Value_Pair> potential) {
		this.potential = potential;
	}





	public User(String username, String password, String email, String name, String dob, String gender) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		
	}


	public User(String username, String password, String email, String name, String dob, String hobbies,
			String gender, String location, String languages, String profilePicBytes, boolean interestedInMen, boolean interestedInWomen) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.dob = dob;
		this.hobbies = hobbies;
		//this.parsedHobbies = this.parseHobbies();
		this.gender = gender;
		this.location = location;
		this.languages = languages;
		this.profilePicBytes = profilePicBytes;
		this.interestedInMen = interestedInMen;
		this.interestedInWomen = interestedInWomen;
		matched = new ArrayList<>();
		potential = new ArrayList<>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	//public ArrayList<Boolean> getHobbies() {
	//	return hobbies;
	//}


	/*public void setHobbies(String hobbies) {
		Scanner in = new Scanner(hobbies);
		switch(in.nextByte())
		{
		case 't': this.hobbies.add(true);
				  break;
				  
		case 'f': this.hobbies.add(false);
				  break;
		
		}
		
	}
*/

	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getLanguages() {
		return languages;
	}


	public void setLanguages(String languages) {
		this.languages = languages;
	}


	public String getProfilePicBytes() {
		return profilePicBytes;
	}


	public void setProfilePicBytes(String profilePicBytes) {
		this.profilePicBytes = profilePicBytes;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isInterestedInMen() {
		return interestedInMen;
	}

	public void setInterestedInMen(boolean interestedInMen) {
		this.interestedInMen = interestedInMen;
	}

	public boolean isInterestedInWomen() {
		return interestedInWomen;
	}

	public void setInterestedInWomen(boolean interestedInWomen) {
		this.interestedInWomen = interestedInWomen;
	}

	public ArrayList<User> getMatched() {
		return matched;
	}

	public void parseHobbies()
	{
		Scanner tempHobbies = new Scanner(hobbies);
		parsedHobbies = new boolean[12];
		String temp;
		int count = 0;
		while(tempHobbies.hasNext())
		{
			temp = tempHobbies.next();

			if(temp.equals("true"))
			{
				parsedHobbies[count] = true;
			}
			if(temp.equals("false"))
			{
				parsedHobbies[count] = false;
			}
			count++;
		}

	}
	public void removePotential(String username)
	{
		for(int count = 0;count<potential.size(); count++)
		{
			if(potential.get(count).getKey().equals(username))
			{
				potential.remove(count);
				return;
			}
		}
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", name=" + name
				+ ", dob=" + dob + ", hobbies=" + hobbies + ", gender=" + gender + ", location=" + location
				+ ", languages=" + languages + ", profilePicBytes=" + profilePicBytes + ", interestedInMen="
				+ interestedInMen + ", interestedInWomen=" + interestedInWomen + "]";
	}

	

	
	
	

}
