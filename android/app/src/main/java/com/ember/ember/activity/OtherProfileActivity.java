package com.ember.ember.activity;

import android.os.Bundle;
import android.view.View;

import com.ember.ember.R;

import androidx.appcompat.app.AppCompatActivity;

public class OtherProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
    }

    public void collapseCard(View v) {
        finishAfterTransition();
    }
}
