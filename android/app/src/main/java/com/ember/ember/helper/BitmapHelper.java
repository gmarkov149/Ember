package com.ember.ember.helper;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.net.Uri;

public class BitmapHelper {

    private static int MAX_BITMAP_SIZE = 900000;

    public static String convertBmpToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap scaleBmpFromFile(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        bmOptions = getBitmapOptions(bmOptions, targetW, targetH);
        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    public static Bitmap scaleBmpFromStream(ContentResolver contentResolver, Uri data, int targetW, int targetH) throws IOException {
        InputStream inputStream = contentResolver.openInputStream(data);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, bmOptions);
        inputStream.close();
        bmOptions = getBitmapOptions(bmOptions, targetW, targetH);
        inputStream = contentResolver.openInputStream(data);
        return BitmapFactory.decodeStream(inputStream, null, bmOptions);
    }

    private static BitmapFactory.Options getBitmapOptions(BitmapFactory.Options bmOptions, int targetW, int targetH) {
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        scaleFactor = scaleFactor > 0 ? scaleFactor : 1;
        int size = photoH * photoW / scaleFactor / scaleFactor;
        int scaling = (size + MAX_BITMAP_SIZE - 1) / MAX_BITMAP_SIZE + 1;
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor * scaling;
        bmOptions.inPurgeable = true;
        return bmOptions;
    }

    public static Bitmap convertStringToBmp(String bytes) {
        byte[] thumbnailByteArr = Base64.decode(bytes, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(thumbnailByteArr, 0, thumbnailByteArr.length);
    }
}
