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
import com.ember.ember.helper.http.ErrorHelper;
import com.ember.ember.model.UserDetails;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSubmitWhenDoneListener();
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

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

    private void loginCall(String usernameStr, String hashedPassword, TextInputEditText password) {
        Call<UserDetails> call = HttpHelper.login(usernameStr, hashedPassword);
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", response.body());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    ErrorHelper.setError(password, ErrorHelper.Problem.CALL_FAILED);
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                ErrorHelper.setError(password, ErrorHelper.Problem.CALL_FAILED);
            }
        });
//            Response<UserDetails> res = call.execute();
//            if (!res.isSuccessful()) {
//                ErrorHelper.setError(password, ErrorHelper.Problem.CALL_FAILED);
//            }
//            else if (!res.body().isSuccess()) {
//                ErrorHelper.setError(password, ErrorHelper.Problem.LOGIN_FAILED);
//            }
//            else {
//                Intent intent = new Intent(this, MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("user", res.body());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
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
}
