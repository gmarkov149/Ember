package com.ember.ember.helper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ember.ember.R;
import com.ember.ember.model.UserDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FieldFillHelper {

    public static void fillFields(UserDetails userDetails, ImageView thumbnail, TextView nameField, TextView langField, TextView hobbiesField, TextView addressField) {
        if (userDetails.getProfilePic() == null) {
            thumbnail.setImageResource(R.drawable.ic_baseline_account_circle_24px);
        }
        else {
            thumbnail.setImageBitmap(userDetails.getProfilePic());
        }
        try {
            if (userDetails.getDob() != null) {
                Date today = new Date();
                Date dob = new SimpleDateFormat("yyyy/MM/dd").parse(userDetails.getDob());
                long age = TimeUnit.DAYS.convert(today.getTime() - dob.getTime(), TimeUnit.MILLISECONDS) / 365;
                nameField.setText(userDetails.getName() + ", " + age);
            }
            else {
                nameField.setText(userDetails.getName());
            }
        } catch (ParseException e) {}
        setTextIfFilled(langField, userDetails.getLanguages(), "Speaks: ");
        setTextIfFilled(hobbiesField, userDetails.getHobbies(), "Enjoys: ");
        setTextIfFilled(addressField, userDetails.getLocation(), "Lives at: ");
    }

    private static void setTextIfFilled(TextView v, String field, String prefix) {
        if (field != null && !field.isEmpty()) {
            v.setVisibility(View.VISIBLE);
            v.setText(prefix + field);
        }
    }
}
