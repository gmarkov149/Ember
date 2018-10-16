package com.ember.ember.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ember.ember.R;
import com.ember.ember.helper.http.HttpHelper;
import com.ember.ember.helper.http.ErrorHelper;
import com.ember.ember.model.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    /**
     * set submit listener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSubmitWhenDoneListener();
    }

    /**
     * start register activity
     * @param v register button
     */
    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * check for empty boxes and call server to login
     * @param v login button
     */
    public void login(View v) {
        TextInputEditText username = findViewById(R.id.username);
        String usernameStr = username.getText().toString();
        TextInputEditText password = findViewById(R.id.password);
        String passwordStr = password.getText().toString();
        boolean hasError = false;
        if (usernameStr.isEmpty()) {
            ErrorHelper.setError(username, ErrorHelper.Problem.USERNAME_EMPTY);
            hasError = true;
        }
        if (passwordStr.isEmpty()) {
            ErrorHelper.setError(password, ErrorHelper.Problem.PASSWORD_EMPTY);
            hasError = true;
        }
        if (hasError) return;
        String hashedPassword = new String(Hex.encodeHex(DigestUtils.sha(passwordStr)));
        loginCall(usernameStr, hashedPassword, password);
    }

    /**
     * api call to server to check if user exists and password is valid
     * @param usernameStr username entered
     * @param hashedPassword hash of the password entered
     * @param password password entry box
     */
    private void loginCall(String usernameStr, String hashedPassword, TextInputEditText password) {
        Call<LoginResponse> call = HttpHelper.login(usernameStr, hashedPassword);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", response.body().getData());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    ErrorHelper.setError(password, ErrorHelper.Problem.LOGIN_FAILED);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ErrorHelper.setError(password, ErrorHelper.Problem.CALL_FAILED);
            }
        });
    }

    /**
     * allows enter button on keyboard to login
     */
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
}
