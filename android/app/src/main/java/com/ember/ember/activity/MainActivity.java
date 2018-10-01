package com.ember.ember.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ember.ember.R;
import com.ember.ember.fragment.UserListFragment;
import com.ember.ember.fragment.ViewProfileFragment;
import com.ember.ember.helper.FieldFillHelper;
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

    public UserDetails getUserDetails() {
        return userDetails;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDetails = (UserDetails) getIntent().getSerializableExtra("user");
        userDetails.setProfilePic();
        userDetails.setHobbiesString();
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
        getUserDetailsCardTransition(item, R.layout.card_expanded);
        expanded = true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void editProfile(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userDetails);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void collapseCard() {
        cardTransition(R.layout.fragment_user_list);
        expanded = false;
    }

    private void cardTransition(int targetLayout) {
        ViewGroup mSceneRoot = findViewById(R.id.scene_root);
        Scene targetScene = Scene.getSceneForLayout(mSceneRoot, targetLayout, this);
        Transition transition = new AutoTransition();
        TransitionManager.go(targetScene, transition);
    }

    private void getUserDetailsCardTransition(UserDetails userDetails, int targetLayout) {
        ViewGroup mSceneRoot = findViewById(R.id.scene_root);
        ViewGroup targetView = (ViewGroup) getLayoutInflater().inflate(targetLayout, null);
        FieldFillHelper.fillFields(userDetails, targetView.findViewById(R.id.thumbnail), targetView.findViewById(R.id.name),
                targetView.findViewById(R.id.languages), targetView.findViewById(R.id.hobbies), targetView.findViewById(R.id.address));
        Scene targetScene = new Scene(mSceneRoot, targetView);
        Transition transition = new AutoTransition();
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
