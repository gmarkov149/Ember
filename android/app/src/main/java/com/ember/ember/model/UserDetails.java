package com.ember.ember.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class UserDetails implements Serializable {
    private boolean success;
    private String username;
    private String name;
    private List<String> hobbies;
    private boolean isMale;
    private String location;
    private List<String> languages;

    public boolean isSuccess() {
        return success;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public boolean isMale() {
        return isMale;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getLanguages() {
        return languages;
    }
}
