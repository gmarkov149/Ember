package com.ember.ember.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.ember.ember.R;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class BitmapHelper {

    public static String convertBmpToString(Bitmap bitmap) throws UnsupportedEncodingException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return new String(byteArray, "UTF-8");
    }

    public static Bitmap scaleBmp(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    public static Bitmap convertStringToBmp(String bytes) {
        byte[] thumbnailByteArr = bytes.getBytes();
        return BitmapFactory.decodeByteArray(thumbnailByteArr, 0, thumbnailByteArr.length);
    }
}
