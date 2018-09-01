package com.ember.ember.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ember.ember.R;
import com.ember.ember.fragment.UserListFragment;
import com.ember.ember.fragment.ViewProfileFragment;
import com.ember.ember.model.UserDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.AutoTransition;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

public class MainActivity extends AppCompatActivity
        implements UserListFragment.OnListFragmentInteractionListener, ViewProfileFragment.OnFragmentInteractionListener {

    private boolean expanded;
    private UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDetails = (UserDetails) getIntent().getSerializableExtra("user");
        userDetails.setProfilePic();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_self:
                    selectedFragment = ViewProfileFragment.newInstance(userDetails);
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
            expanded = false;
            transaction.commit();
            return true;
        });
        navigation.setSelectedItemId(R.id.navigation_match);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
        cardTransition(R.layout.card_expanded);
        expanded = true;
    }

    public void collapseCard() {
        cardTransition(R.layout.fragment_user_list);
        expanded = false;
    }

    private void cardTransition(int targetLayout) {
        ViewGroup mSceneRoot = findViewById(R.id.scene_root);
        Scene targetScene = Scene.getSceneForLayout(mSceneRoot, targetLayout, this);
        Transition transition = new AutoTransition();
        transition.addTarget(R.id.thumbnail);
        transition.addTarget(R.id.name);
        TransitionManager.go(targetScene, transition);
    }

    @Override
    public void onBackPressed() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        int selected = navigation.getSelectedItemId();
        if (expanded && selected != R.id.navigation_self) {
            collapseCard();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, UserListFragment.newInstance(1, selected == R.id.navigation_matched));
            transaction.commit();
            return;
        }
        super.onBackPressed();
    }
}
