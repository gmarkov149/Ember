package com.ember.ember.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.ember.ember.R;
import com.ember.ember.helper.http.HttpHelper;
import com.ember.ember.model.UserDetails;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSubmitWhenDoneListener();
    }

    public void register(View v) {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivity(intent);
    }

    public void login(View v) {
        String username = getEditText(R.id.username);
        String password = getEditText(R.id.password);
        if (username.isEmpty() || password.isEmpty()) {
            raiseToast(Problem.EMPTY_FIELDS);
            return;
        }
        String hashedPassword = new String(Hex.encodeHex(DigestUtils.sha(password)));
        Toast.makeText(this, hashedPassword, Toast.LENGTH_SHORT).show();

        try {
            Call<UserDetails> call = HttpHelper.login(username, hashedPassword);
            Response<UserDetails> res = call.execute();
            if (!res.isSuccessful()) {
                raiseToast(Problem.CALL_FAILED);
            }
            else if (!res.body().isSuccess()) {
                raiseToast(Problem.LOGIN_FAILED);
            }
            else {
//                Intent intent = new Intent(this, MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("user", res.body());
//                intent.putExtras(bundle);
//                startActivity(intent);
                // TODO: navigate to main page here
            }
        } catch (IOException e) {
            raiseToast(Problem.CALL_FAILED);
        }
    }

    private String getEditText(int id) {
        return ((TextInputEditText) findViewById(id)).getText().toString();
    }

    private void setSubmitWhenDoneListener() {
        TextInputEditText passwordField = findViewById(R.id.password);

        passwordField.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                findViewById(R.id.login).performClick();
                return true;
            }
            return false;
        });
    }

    private enum Problem {
        CALL_FAILED, LOGIN_FAILED, EMPTY_FIELDS
    }

    private void raiseToast(Problem problem) {
        String problemAlert = "";
        switch(problem) {
            case CALL_FAILED:
                problemAlert = "Login failed, are you connected to the Internet?";
                break;
            case EMPTY_FIELDS:
                problemAlert = "Username and password cannot be empty!";
                break;
            case LOGIN_FAILED:
                problemAlert = "Username/password is incorrect!";
                break;
        }
        Toast.makeText(this, problemAlert, Toast.LENGTH_SHORT).show();
    }
}
