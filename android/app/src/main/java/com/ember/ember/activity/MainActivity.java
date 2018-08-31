package com.ember.ember.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.ember.ember.R;
import com.ember.ember.fragment.UserListFragment;
import com.ember.ember.fragment.ViewProfileFragment;
import com.ember.ember.model.UserDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements UserListFragment.OnListFragmentInteractionListener, ViewProfileFragment.OnFragmentInteractionListener {

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
                    selectedFragment = UserListFragment.newInstance(2, false);
                    break;
                case R.id.navigation_matched:
                    selectedFragment = UserListFragment.newInstance(2, true);
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        navigation.setSelectedItemId(R.id.navigation_match);
        transaction.replace(R.id.content, UserListFragment.newInstance(2, false));
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(UserDetails item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
