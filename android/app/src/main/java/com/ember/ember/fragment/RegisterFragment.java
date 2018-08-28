package com.ember.ember.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ember.ember.R;

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
        int selectedResource = getArguments().getInt(ARG_SECTION_NUMBER) == 1 ?
                R.layout.fragment_account_details : R.layout.fragment_more_about_you;
        View rootView = inflater.inflate(selectedResource, container, false);
        return rootView;
    }
}