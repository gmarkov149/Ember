package com.ember.ember.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ember.ember.R;
import com.ember.ember.adapter.SectionsPagerAdapter;
import com.ember.ember.fragment.DatePickerFragment;
import com.ember.ember.handlers.ExceptionHandler;
import com.ember.ember.helper.BitmapHelper;
import com.ember.ember.helper.ErrorHelper;
import com.ember.ember.helper.http.HttpHelper;
import com.ember.ember.model.Hobbies;
import com.ember.ember.model.UserDetails;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String mCurrentPhotoPath;
    private boolean photoChanged;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_PICK_PHOTO = 2;
    static final int REQUEST_MAP_LOCATION = 3;
    private ArrayList<Hobbies> hobbiesList;
    /**

     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private UserDetails userDetails;
    private Map<Integer, ErrorHelper.Problem> toValidate;
    private Bitmap bitmap;

    /**
     * initialize hobbies list and variables to validate, and decide if the user is editing or creating
     * a new user
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        photoChanged = false;
        setContentView(R.layout.activity_register);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        setupValidationMap();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_baseline_face_24px);
        tabs.getTabAt(1).setIcon(R.drawable.ic_baseline_favorite_24px);
        setupHobbiesList();
        userDetails = null;
        if (getIntent().hasExtra("user")) {
            userDetails = (UserDetails) getIntent().getSerializableExtra("user");
            userDetails.setProfilePic();
        }
    }

    /**
     * create a hobbies list from the possible hobbies
     */
    private void setupHobbiesList() {
        hobbiesList = new ArrayList<>();

        for (int i = 0; i < UserDetails.possibleHobbies.length; i++) {
            Hobbies hobbies = new Hobbies();
            hobbies.setHobby(UserDetails.possibleHobbies[i]);
            hobbies.setSelected(false);
            hobbiesList.add(hobbies);
        }
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public ArrayList<Hobbies> getHobbiesList() {
        return hobbiesList;
    }

    /**
     * sets up a validation map that will be iterated through during submission to look for empty fields
     */
    private void setupValidationMap() {
        toValidate = new HashMap<>();
        if (userDetails != null) {
            toValidate.put(R.id.username, ErrorHelper.Problem.USERNAME_EMPTY);
            toValidate.put(R.id.password, ErrorHelper.Problem.PASSWORD_EMPTY);
        }
        toValidate.put(R.id.name, ErrorHelper.Problem.NAME_EMPTY);
        toValidate.put(R.id.email, ErrorHelper.Problem.EMAIL_EMPTY);
        toValidate.put(R.id.dob, ErrorHelper.Problem.DOB_EMPTY);
    }

    /**
     * start built in camera intent
     * @param v take picture button
     */
    public void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) { }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * start built in gallery intent
     * @param v select from gallery button
     */
    public void pickImage(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    /**
     * check for return result from pick image or take picture buttons
     * @param requestCode identify which button was clicked
     * @param resultCode check if there are any errors
     * @param data data of the gallery pic
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setCameraPic();
        }
        else if (requestCode == REQUEST_PICK_PHOTO && resultCode == RESULT_OK && data.getData() != null) {
            setGalleryPic(data.getData());
        }
        else if (requestCode == REQUEST_MAP_LOCATION && resultCode == RESULT_OK) {
            TextInputEditText addressField = findViewById(R.id.address);
            addressField.setTag(data.getStringExtra("latlon"));
            addressField.setText(data.getStringExtra("address"));
        }
    }

    /**
     * create file data and image
     * @return
     * @throws IOException file not found
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * starts date picker fragment to select dob with calendar
     * @param v select date button
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this, R.id.dob);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * starts map fragment to select location
     * @param v select location button
     */
    public void showMapDialog(View v) {
        Intent mapIntent = new Intent(this, MapActivity.class);
        startActivityForResult(mapIntent, REQUEST_MAP_LOCATION);
    }

    /**
     * set thumbnail to camera picture
     */
    private void setCameraPic() {
        ImageView mImageView = findViewById(R.id.pic);
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        bitmap = BitmapHelper.scaleBmpFromFile(mCurrentPhotoPath, targetW, targetH);
        mImageView.setImageBitmap(bitmap);
        photoChanged = true;
    }

    /**
     * set thumbnail to gallery picture
     * @param data file path of picture selected
     */
    private void setGalleryPic(Uri data) {
        try {
            ImageView mImageView = findViewById(R.id.pic);
            int targetW = mImageView.getWidth();
            int targetH = mImageView.getHeight();
            bitmap = BitmapHelper.scaleBmpFromStream(getContentResolver(), data, targetW, targetH);
            mImageView.setImageBitmap(bitmap);
            photoChanged = true;
        } catch (IOException e) {}
    }

    /**
     * validate inputs, create UserDetails object and submit to server
     * @param v submit button
     */
    public void submit(View v) {
        if (validateEditText() || userDetails == null && checkPasswordsMatch()) return;
        if (mViewPager.getCurrentItem() == 0) {
            mViewPager.setCurrentItem(1);
            return;
        }
        StringBuilder hobbiesString = new StringBuilder();
        for (int i = 0; i < hobbiesList.size(); i++) {
            Hobbies hobby = hobbiesList.get(i);
            hobbiesString.append(String.valueOf(hobby.isSelected()) + " ");
        }
        if (hobbiesString.length() > 0) {
            hobbiesString.setLength(hobbiesString.length() - 1);
        }
        RadioButton radioButton = findViewById(((RadioGroup) findViewById(R.id.gender)).getCheckedRadioButtonId());
        String gender = radioButton.getText().toString();
        boolean isRegister = userDetails == null;
        Object address = findViewById(R.id.address).getTag();
        UserDetails newUserDetails = new UserDetails(
            isRegister ? getTextField(R.id.username) : userDetails.getUsername(),
            getTextField(R.id.name),
            isRegister ? new String(Hex.encodeHex(DigestUtils.sha(getTextField(R.id.password))))
                : userDetails.getPassword(),
            getTextField(R.id.email),
            getTextField(R.id.dob),
            hobbiesString.toString(),
            gender,
            !(address instanceof String) ? "" : address.toString(),
            getTextField(R.id.address),
            ((Spinner) findViewById(R.id.languages)).getSelectedItem().toString(),
            photoChanged ? BitmapHelper.convertBmpToString(bitmap) : isRegister ? null : userDetails.getProfilePicBytes(),
            ((CheckBox) findViewById(R.id.men)).isChecked(),
            ((CheckBox) findViewById(R.id.women)).isChecked()
        );
        executeCall(newUserDetails, isRegister);
    }

    /**
     * submits user details to server and go to main activity if successful
     * @param userDetails details of user to submit
     * @param isRegister boolean representing create or edit mode
     */
    private void executeCall(UserDetails userDetails, boolean isRegister) {
        Call<Void> call = isRegister ? HttpHelper.register(userDetails)
                : HttpHelper.editProfile(userDetails);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", userDetails);
                    intent.putExtras(bundle);
                    if (isRegister) {
                        startActivity(intent);
                    }
                    else {
                        setResult(RESULT_OK, intent);
                    }
                    finish();
                } else {
                    ErrorHelper.raiseToast(RegisterActivity.this, ErrorHelper.Problem.CALL_FAILED);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                    ErrorHelper.raiseToast(RegisterActivity.this, ErrorHelper.Problem.USERNAME_EXISTS);
            }
        });
    }

    /**
     * iterate through validation map and check if field is filled
     * @return
     */
    private boolean validateEditText() {
        boolean hasError = false;
        for (Map.Entry<Integer, ErrorHelper.Problem> entry : toValidate.entrySet()) {
            TextInputEditText input = findViewById(entry.getKey());
            String inputStr = input.getText().toString();
            if (inputStr.isEmpty()) {
                ErrorHelper.setError(input, entry.getValue());
                hasError = true;
            }
        }
        return hasError;
    }

    /**
     * check if the passwords entered twice match
     * @return true if match
     */
    private boolean checkPasswordsMatch() {
        String password = getTextField(R.id.password);
        TextInputEditText repeatedPassword = findViewById(R.id.repeat_password);
        String repeatedPasswordStr = repeatedPassword.getText().toString();
        if (!password.equals(repeatedPasswordStr)) {
            ErrorHelper.setError(repeatedPassword, ErrorHelper.Problem.PASSWORDS_NO_MATCH);
            return true;
        }
        return false;
    }

    /**
     * get text in an edit text
     * @param id resource id of the field
     * @return the contents of the edit text
     */
    private String getTextField(int id) {
        Editable text = ((TextInputEditText) findViewById(id)).getText();
        return text == null ? "" : text.toString();
    }
}
