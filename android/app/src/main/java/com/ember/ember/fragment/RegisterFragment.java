package com.ember.ember.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.ember.ember.R;
import com.ember.ember.activity.RegisterActivity;
import com.ember.ember.adapter.HobbiesAdapter;
import com.ember.ember.model.Hobbies;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

/**
 * A fragment containing a simple view.
 */
public class RegisterFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public RegisterFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RegisterFragment newInstance(int sectionNumber) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean isDetailsPage = getArguments().getInt(ARG_SECTION_NUMBER) == 1;
        int selectedResource = isDetailsPage ?
                R.layout.fragment_account_details : R.layout.fragment_more_about_you;
        View rootView = inflater.inflate(selectedResource, container, false);
        if (isDetailsPage) {
            MaterialButton dob = rootView.findViewById(R.id.dob_button);
            dob.setIconResource(R.drawable.ic_baseline_date_range_24px);
            dob.setIconTint(ColorStateList.valueOf(Color.WHITE));
        }
        else {
            Spinner spinner = rootView.findViewById(R.id.hobbies);
            HobbiesAdapter hobbiesAdapter = new HobbiesAdapter(getActivity(), 0, ((RegisterActivity) getActivity()).getHobbiesList());
            spinner.setAdapter(hobbiesAdapter);
        }
        return rootView;
    }
}