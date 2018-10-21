package com.ember.ember.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ember.ember.R;
import com.ember.ember.activity.RegisterActivity;
import com.ember.ember.adapter.HobbiesAdapter;
import com.ember.ember.model.Hobbies;
import com.ember.ember.model.UserDetails;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

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
    private final String[] languageList = new String[]{"English", "French", "Mandarin", "Tamil", "Hindi", "Malay"};

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
        UserDetails userDetails = ((RegisterActivity) getActivity()).getUserDetails();
        View rootView = inflater.inflate(selectedResource, container, false);
        if (isDetailsPage) {
            MaterialButton dob = rootView.findViewById(R.id.dob_button);
            dob.setIconResource(R.drawable.ic_baseline_date_range_24px);
            dob.setIconTint(ColorStateList.valueOf(Color.WHITE));
            if (userDetails != null) existingUserSetupDetails(rootView, userDetails);
        }
        else {
            Spinner hobbiesSpinner = rootView.findViewById(R.id.hobbies);
            HobbiesAdapter hobbiesAdapter = new HobbiesAdapter(getActivity(), 0, ((RegisterActivity) getActivity()).getHobbiesList());
            hobbiesSpinner.setAdapter(hobbiesAdapter);
            if (userDetails != null) existingUserSetupMore(rootView, userDetails);
            Spinner languageSpinner = rootView.findViewById(R.id.languages);
            ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, languageList);
            languageSpinner.setAdapter(languageAdapter);
        }
        return rootView;
    }

    private void existingUserSetupDetails(View v, UserDetails userDetails) {
        v.findViewById(R.id.username).setVisibility(View.GONE);
        v.findViewById(R.id.password).setVisibility(View.GONE);
        v.findViewById(R.id.repeat_password).setVisibility(View.GONE);
        fillExistingUserField(R.id.name, userDetails.getName(), v);
        fillExistingUserField(R.id.email, userDetails.getEmail(), v);
        fillExistingUserField(R.id.dob, userDetails.getDob(), v);
        ((RadioGroup) v.findViewById(R.id.gender)).check(
                userDetails.getGender() == null || userDetails.getGender().equals("Other") ? R.id.radio_other :
                userDetails.getGender().equals("Male") ? R.id.radio_male : R.id.radio_female );
    }

    private void existingUserSetupMore(View v, UserDetails userDetails) {
        if (userDetails.getProfilePic()!= null)
            ((ImageView) v.findViewById(R.id.pic)).setImageBitmap(userDetails.getProfilePic());
        fillExistingUserField(R.id.address, userDetails.getLocationStr(), v);
        ((CheckBox) v.findViewById(R.id.men)).setChecked(userDetails.isInterestedInMen());
        ((CheckBox) v.findViewById(R.id.women)).setChecked(userDetails.isInterestedInWomen());
        List<Hobbies> hobbiesList = ((RegisterActivity) getActivity()).getHobbiesList();
        String[] hobbiesArr = userDetails.getHobbies().split(" ");
        for (int i = 0; i < hobbiesArr.length; i++) {
            hobbiesList.get(i).setSelected(Boolean.parseBoolean(hobbiesArr[i]));
        }
        Spinner languageSpinner = v.findViewById(R.id.languages);
        if (userDetails.getLanguages() != null) {
            for (int i = 0; i < languageList.length; i++) {
                if (languageList[i].equalsIgnoreCase(userDetails.getLanguages())) {
                    languageSpinner.setSelection(i);
                }
            }
        }
    }

    private void fillExistingUserField(int field, String text, View v) {
        ((TextInputEditText) v.findViewById(field)).setText(text == null ? "" : text);
    }
}