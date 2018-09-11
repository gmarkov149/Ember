
public class User 
{
	private String id;
	private String username;
	private String password;
	private String email;
	private String fName;
	private String lName;
	private String gender;
	private boolean interestedInMen;
	private boolean interestedInWomen;
	private boolean interestedInNeither;
	
	

	public User(String id, String username, String password, String email, String fName, String gender) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fName = fName;
		this.gender = gender;
		
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
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

	public boolean isInterestedInNeither() {
		return interestedInNeither;
	}

	public void setInterestedInNeither(boolean interestedInNeither) {
		this.interestedInNeither = interestedInNeither;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password.hashCode() + ", email=" + email + ", fName="
				+ fName + ", gender=" + gender + "Women?" + " " + interestedInWomen +  "]";
	}
	
	

}
