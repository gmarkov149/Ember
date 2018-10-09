package com.ember.ember.helper.http;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ErrorHelper {

    public enum Problem {
        USERNAME_EXISTS, CALL_FAILED, LOGIN_FAILED, USERNAME_EMPTY, PASSWORD_EMPTY, NAME_EMPTY, EMAIL_EMPTY, DOB_EMPTY, PASSWORDS_NO_MATCH
    }

    public static void setError(TextInputEditText text, Problem problem) {
        String problemAlert = getStatement(problem);
        text.setError(problemAlert);
    }

    public static void raiseToast(Context context, Problem problem) {
        String problemAlert = getStatement(problem);
        Toast.makeText(context, problemAlert, Toast.LENGTH_SHORT).show();
    }

    private static String getStatement(Problem problem) {
        String problemAlert = "";
        switch(problem) {
            case USERNAME_EXISTS:
                problemAlert = "Username already exists!";
                break;
            case CALL_FAILED:
                problemAlert = "Login failed, are you connected to the Internet?";
                break;
            case USERNAME_EMPTY:
                problemAlert = "Username cannot be empty!";
                break;
            case PASSWORD_EMPTY:
                problemAlert = "Password cannot be empty!";
                break;
            case LOGIN_FAILED:
                problemAlert = "Username/password is incorrect!";
                break;
            case PASSWORDS_NO_MATCH:
                problemAlert = "Passwords do not match!";
                break;
            case NAME_EMPTY:
                problemAlert = "Name cannot be empty!";
                break;
            case EMAIL_EMPTY:
                problemAlert = "Email cannot be empty!";
                break;
            case DOB_EMPTY:
                problemAlert = "Date of Birth cannot be empty!";
                break;
        }
        return problemAlert;
    }
}
