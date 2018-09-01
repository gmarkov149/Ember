package com.ember.ember.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ember.ember.R;
import com.ember.ember.adapter.SectionsPagerAdapter;
import com.ember.ember.fragment.DatePickerFragment;
import com.ember.ember.helper.BitmapHelper;
import com.ember.ember.helper.http.ErrorHelper;
import com.ember.ember.helper.http.HttpHelper;
import com.ember.ember.model.UserDetails;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String mCurrentPhotoPath;
    private boolean photoChanged;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_PICK_PHOTO = 2;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Map<Integer, ErrorHelper.Problem> toValidate;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private void setupValidationMap() {
        toValidate = new HashMap<>();
        toValidate.put(R.id.username, ErrorHelper.Problem.USERNAME_EMPTY);
        toValidate.put(R.id.name, ErrorHelper.Problem.NAME_EMPTY);
        toValidate.put(R.id.password, ErrorHelper.Problem.PASSWORD_EMPTY);
        toValidate.put(R.id.email, ErrorHelper.Problem.EMAIL_EMPTY);
        toValidate.put(R.id.dob, ErrorHelper.Problem.DOB_EMPTY);
    }

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

    public void pickImage(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setCameraPic();
        }
        else if (requestCode == REQUEST_PICK_PHOTO && resultCode == RESULT_OK && data.getData() != null) {
            setGalleryPic(data.getData());
        }
    }

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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this, R.id.dob);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void setCameraPic() {
        ImageView mImageView = findViewById(R.id.pic);
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        bitmap = BitmapHelper.scaleBmpFromFile(mCurrentPhotoPath, targetW, targetH);
        mImageView.setImageBitmap(bitmap);
        photoChanged = true;
    }

    private void setGalleryPic(Uri data) {
        try {
            ImageView mImageView = findViewById(R.id.pic);
            int targetW = mImageView.getWidth();
            int targetH = mImageView.getHeight();
            InputStream inputStream = getContentResolver().openInputStream(data);
            bitmap = BitmapHelper.scaleBmpFromStream(inputStream, targetW, targetH);
            mImageView.setImageBitmap(bitmap);
            photoChanged = true;
        } catch (FileNotFoundException e) {}
    }

    public void submit(View v) {
        if (validateEditText() || checkPasswordsMatch()) return;
        if (mViewPager.getCurrentItem() == 0) {
            mViewPager.setCurrentItem(1);
            return;
        }
        RadioButton radioButton = findViewById(((RadioGroup) findViewById(R.id.gender)).getCheckedRadioButtonId());
        String gender = radioButton.getText().toString();
        try {
            UserDetails userDetails = new UserDetails(
                getTextField(R.id.username),
                getTextField(R.id.name),
                getTextField(R.id.password),
                getTextField(R.id.email),
                getTextField(R.id.dob),
                getTextField(R.id.hobbies),
                gender,
                getTextField(R.id.address),
                getTextField(R.id.languages),
                photoChanged ? BitmapHelper.convertBmpToString(bitmap) : null,
                ((CheckBox) findViewById(R.id.men)).isChecked(),
                ((CheckBox) findViewById(R.id.women)).isChecked()
            );
            executeCall(userDetails);
        } catch (IOException e) {}
    }

    private void executeCall(UserDetails userDetails) throws IOException {
//        Call<Void> call = HttpHelper.register(userDetails);
//        Response<Void> res = call.execute();
//        if (!res.isSuccessful()) {
//            ErrorHelper.raiseToast(this, ErrorHelper.Problem.CALL_FAILED);
//        }
//        else {
//            Intent intent = new Intent(this, MainActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("user", userDetails);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userDetails);
        intent.putExtras(bundle);
        startActivity(intent);
    }

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

    private String getTextField(int id) {
        return ((TextInputEditText) findViewById(id)).getText().toString();
    }
}
