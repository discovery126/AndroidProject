package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class MyBitMap {

    public static String getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,stream);
        return Base64.encodeToString(stream.toByteArray(),Base64.NO_WRAP);
    }

    public static Bitmap stringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}
