package com.ember.ember.model;

import android.graphics.Bitmap;

import com.ember.ember.helper.BitmapHelper;

import java.io.Serializable;

public class UserDetails implements Serializable {
    private String username;
    private String name;
    private String password;
    private String email;
    private String dob;
    private String hobbies;
    private String gender;
    private String location;
    private String locationStr;
    private String languages;
    private String profilePicBytes;
    private Bitmap profilePic;
    private boolean interestedInMen;
    private boolean interestedInWomen;
    private String hobbiesString;

    public static final String[] possibleHobbies = {
            "Hobbies", "Fitness", "Music", "Dancing", "Reading",
            "Walking", "Traveling", "Eating", "Crafts", "Fishing",
            "Hiking", "Animals"};

    public UserDetails(String username, String name, String password, String email, String dob, String hobbies, String gender, String location, String locationStr, String languages, String profilePicBytes, boolean interestedInMen, boolean interestedInWomen) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.hobbies = hobbies;
        this.gender = gender;
        this.location = location;
        this.locationStr = locationStr;
        this.languages = languages;
        this.profilePicBytes = profilePicBytes;
        this.interestedInMen = interestedInMen;
        this.interestedInWomen = interestedInWomen;
        this.profilePic = null;
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

    public String getLocationStr() {
        return locationStr;
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

    public String getHobbiesString() {
        return hobbiesString;
    }

    public void setProfilePic() {
        if (profilePicBytes == null || profilePicBytes.equals("null")) return;
        profilePic = BitmapHelper.convertStringToBmp(profilePicBytes);
    }

    public void nullProfilePic() {
        profilePic = null;
    }

    public void setHobbiesString() {
        StringBuilder hobbiesSb = new StringBuilder();
        String[] hobbiesArr = hobbies.split(" ");
        for (int i = 1; i < hobbiesArr.length; i++) {
            if (Boolean.parseBoolean(hobbiesArr[i])) {
                hobbiesSb.append(possibleHobbies[i] + ", ");
            }
        }
        if (hobbiesSb.length() > 0) {
            hobbiesSb.setLength(hobbiesSb.length() - 2);
        }
        hobbiesString = hobbiesSb.toString();
    }

}
