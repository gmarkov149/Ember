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
	private ArrayList<User> potential;

	public User()
	{
		matched = new ArrayList<>();
		potential = new ArrayList<>();
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

	public ArrayList<User> getPotential() {
		return potential;
	}

	public void setPotential(ArrayList<User> potential) {
		this.potential = potential;
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
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", name=" + name
				+ ", dob=" + dob + ", hobbies=" + hobbies + ", gender=" + gender + ", location=" + location
				+ ", languages=" + languages + ", profilePicBytes=" + profilePicBytes + ", interestedInMen="
				+ interestedInMen + ", interestedInWomen=" + interestedInWomen + "]";
	}
}
