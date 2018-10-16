package com.ember.ember.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ember.ember.R;
import com.ember.ember.fragment.UserListFragment;
import com.ember.ember.fragment.ViewProfileFragment;
import com.ember.ember.helper.FieldFillHelper;
import com.ember.ember.helper.http.ErrorHelper;
import com.ember.ember.helper.http.HttpHelper;
import com.ember.ember.model.UserDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.AutoTransition;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements UserListFragment.OnListFragmentInteractionListener, ViewProfileFragment.OnFragmentInteractionListener {

    private boolean expanded;
    private static UserDetails userDetails;

    public static UserDetails getUserDetails() {
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
    public void onListButtonFragmentInteraction(UserDetails match) {
        Call<Void> call = HttpHelper.match(userDetails.getUsername(), match);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, String.format("Matched with %s!",
                            match.getName()), Toast.LENGTH_SHORT).show();
                } else {
                    ErrorHelper.raiseToast(MainActivity.this, ErrorHelper.Problem.CALL_FAILED);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ErrorHelper.raiseToast(MainActivity.this, ErrorHelper.Problem.CALL_FAILED);
            }
        });
    }

    @Override
    public void onListChatFragmentInteraction(UserDetails item) {
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userDetails);
        bundle.putSerializable("other", item);
        intent.putExtras(bundle);
        startActivity(intent);
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
        FloatingActionButton button = targetView.findViewById(R.id.match);
        if (getCurrentNavTab() == R.id.navigation_matched) {
            button.setImageResource(R.drawable.ic_baseline_message_24px);
            button.setOnClickListener((View v) -> onListChatFragmentInteraction(userDetails));
        }
        else {
            button.setOnClickListener((View v) -> onListButtonFragmentInteraction(userDetails));
        }
        Scene targetScene = new Scene(mSceneRoot, targetView);
        Transition transition = new AutoTransition();
        TransitionManager.go(targetScene, transition);
    }

    private int getCurrentNavTab() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        return navigation.getSelectedItemId();
    }

    @Override
    public void onBackPressed() {
        int selected = getCurrentNavTab();
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