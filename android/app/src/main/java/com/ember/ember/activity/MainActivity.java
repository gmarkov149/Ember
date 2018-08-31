package com.ember.ember.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ember.ember.R;
import com.ember.ember.fragment.UserListFragment;
import com.ember.ember.fragment.ViewProfileFragment;
import com.ember.ember.model.UserDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements UserListFragment.OnListFragmentInteractionListener, ViewProfileFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_self:
                    selectedFragment = ViewProfileFragment.newInstance(new UserDetails());
                    break;
                case R.id.navigation_match:
                    selectedFragment = UserListFragment.newInstance(1, false);
                    break;
                case R.id.navigation_matched:
                    selectedFragment = UserListFragment.newInstance(1, true);
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        navigation.setSelectedItemId(R.id.navigation_match);
        transaction.replace(R.id.content, UserListFragment.newInstance(1, false));
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(UserDetails item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void expandCard(View v) {
        Intent intent = new Intent(this, OtherProfileActivity.class);
        View sourceView = (View) v.getParent().getParent();
        Bundle options = ActivityOptionsCompat.makeScaleUpAnimation(
                sourceView, 0, 0, sourceView.getWidth(), sourceView.getHeight()).toBundle();

        ActivityCompat.startActivity(this, intent, options);
    }
}
