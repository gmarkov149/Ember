import java.util.ArrayList;

public class User 
{
	private String username;
	private String password;
	private String email;
	private String name;
	private String dob;
	private ArrayList<Boolean> hobbies;
	private String gender;
	private String location;
	private String languages;
	private String profilePicBytes;
	private boolean interestedInMen;
	private boolean interestedInWomen;
	
	
	public User()
	{
		hobbies = new ArrayList<>();
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


	public User(String username, String password, String email, String name, String dob, ArrayList<Boolean> hobbies,
			String gender, String location, String languages, String profilePicBytes) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.dob = dob;
		this.hobbies = hobbies;
		this.gender = gender;
		this.location = location;
		this.languages = languages;
		this.profilePicBytes = profilePicBytes;
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


	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", name=" + name
				+ ", dob=" + dob + ", hobbies=" + hobbies + ", gender=" + gender + ", location=" + location
				+ ", languages=" + languages + ", profilePicBytes=" + profilePicBytes + ", interestedInMen="
				+ interestedInMen + ", interestedInWomen=" + interestedInWomen + "]";
	}

	

	
	
	

}
