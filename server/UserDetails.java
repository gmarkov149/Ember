// Hardcoded user information for prototype

public class UserDetails implements Serializable {
    private boolean success;
    private String username;
    private String name;
    private String password;
    private String email;
    private String dob;
    private String hobbies;
    private String gender;
    private String location;
    private String languages;
    private String profilePicBytes;
    private Bitmap profilePic;
    private boolean interestedInMen;
    private boolean interestedInWomen;

    public UserDetails() {
        this.name = "test";
    }

    public UserDetails(String username, String name, String password, String email, String dob, String hobbies, String gender, String location, String languages, String profilePicBytes, boolean interestedInMen, boolean interestedInWomen) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.hobbies = hobbies;
        this.gender = gender;
        this.location = location;
        this.languages = languages;
        this.profilePicBytes = profilePicBytes;
        this.interestedInMen = interestedInMen;
        this.interestedInWomen = interestedInWomen;
        this.profilePic = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public String getLanguages() {
        return languages;
    }

    public String getProfilePicBytes() {
        return profilePicBytes;
    }

    public boolean isInterestedInMen() {
        return interestedInMen;
    }

    public boolean isInterestedInWomen() {
        return interestedInWomen;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    // public void setProfilePic() {
    //     if (profilePicBytes == null) return;
    //     profilePic = BitmapHelper.convertStringToBmp(profilePicBytes);
    // }
}