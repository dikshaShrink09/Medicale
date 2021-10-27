package com.shrinkcom.medicale.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.shrinkcom.medicale.R;

import java.io.ByteArrayOutputStream;

public class APPUTILS {
    public static byte[]  getBytes(Bitmap bitmap){

        byte[] byteArray = new byte[0];
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
            byteArray = stream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "ERORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: ");

        }

        // bitmap.recycle();
        return byteArray;
    }

    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    public static void checkInternet(Context context,String message){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setTitle(context.getResources().getString(R.string.alert));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                context.getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                context.getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}
