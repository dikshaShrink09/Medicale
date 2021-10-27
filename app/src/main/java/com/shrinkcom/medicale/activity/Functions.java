package com.shrinkcom.medicale.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import com.shrinkcom.medicale.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class Functions {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_CAMERA = 109;
    static int intPosition;
    public static File fileName;
    public static final String SHARED_PROVIDER_AUTHORITY = "com.shrinkcom.clarish" + ".provider";

//    public void setIntent(Context context)
//    {
//        Intent i=new Intent(context, SupportActivity.class);
//        context.startActivity(i);
//    }

//    public void showAlert(Context context,String mTitle,String msg,View.OnClickListener listener){
//        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.setCancelable(false);
//        LayoutInflater inflater=alertDialog.getLayoutInflater();
//        View view =inflater.inflate(R.layout.alert_dialog, null);
//        alertDialog.setView(view, 0, 0, 0, 0);
//
//      TextView title =  view.findViewById(R.id.tvTitle);
//      TextView subTitle=  view.findViewById(R.id.tvSubTitle);
//      Button btnYes=  view.findViewById(R.id.btnYes);
//      Button btnNo=  view.findViewById(R.id.btnNo);
//        title.setText(""+mTitle);
//        subTitle.setText(""+msg);
//
//      btnYes.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              listener.onClick(v);
//              alertDialog.dismiss();
//          }
//      });
//        btnNo.setOnClickListener(new View.OnClickListener() {
//                                     @Override
//                                     public void onClick(View v) {
//                                         alertDialog.dismiss();
//                                     }
//                                 }
//        );
//        alertDialog.show();
//    }

//    public void showInfoAlert(Context context,String msg){
//        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.setCancelable(false);
//        LayoutInflater inflater=alertDialog.getLayoutInflater();
//        View view =inflater.inflate(R.layout.info_dialog, null);
//        alertDialog.setView(view, 0, 0, 0, 0);
//
//        TextView subTitle=  view.findViewById(R.id.tvSubTitle);
//        ImageView imgCancel=  view.findViewById(R.id.btnCancel);
//
//
//        subTitle.setText(""+msg);
//
//        imgCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                alertDialog.dismiss();
//            }
//        });
//
//        alertDialog.show();
//    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE ,Manifest.permission.CAMERA  }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE ,Manifest.permission.CAMERA }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission_camera(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_CAMERA);
                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_CAMERA);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

//    public static String singleSelectionAlertDialog(Context mContext, String[] listItems, TextView textView){
//
//       ArrayAdapter arrayAdapter = new ArrayAdapter(mContext,R.layout.alert_layout_list,listItems);
//
//        android.app.AlertDialog.Builder mBuilderAt = new android.app.AlertDialog.Builder(mContext);
//        mBuilderAt.setTitle("Choose an item");
//        mBuilderAt.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                textView.setText(listItems[i]);
//                intPosition = i;
//                dialogInterface.dismiss();
//            }
//        });
//        android.app.AlertDialog mDialogAt = mBuilderAt.create();
//        mDialogAt.show();
//
//        return textView.getText().toString();
//    }


    public static String convertDateFormat(String time, String format) {
        String convertedDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.JAPAN);
            TimeZone timeZone = TimeZone.getTimeZone("GMT+0");
            dateFormat.setTimeZone(timeZone);
            Date date = dateFormat.parse(time);
            dateFormat.setTimeZone(TimeZone.getDefault());
            SimpleDateFormat dateFormat1 = new SimpleDateFormat(
                    "dd/MM/yy hh:mm a",Locale.getDefault());
            convertedDate = dateFormat1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return convertedDate;
    }

    public int listPosition(){
        return intPosition;
    }


    public static String compressSavedImage(Context mContext, Uri imageUri) {
        InputStream imageStream = null;
        try {
            imageStream = mContext.getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(imageStream);
        String tempFile = "";
        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileOutputStream fos = null;
            try {
                ExifInterface exif = new ExifInterface(Functions.fileName.getAbsolutePath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bm = Bitmap.createBitmap(bm, 0, 0,
                        bm.getWidth(), bm.getHeight(), matrix,
                        true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream out;
            try {
                out = new FileOutputStream(Functions.fileName.getAbsolutePath());
                bm.compress(Bitmap.CompressFormat.JPEG, 70, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            tempFile = Functions.fileName.getAbsolutePath();
//            } else {
//                tempFile = Utility.compressImage(Utility.getUriPath(mContext, imageUri));
//            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static String compressImage(String filePath) {
        try {
            Bitmap scaledBitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float maxHeight = 1280.0f;
            float maxWidth = 720.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];
            try {
                bmp = BitmapFactory.decodeFile(filePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth,
                        actualHeight, Bitmap.Config.ARGB_4444);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                        scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                        true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream out;
            String filename = getFilename();
            try {
                out = new FileOutputStream(filename);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float)
                    reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "ClarisApp/");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getUriPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }else if ("pdf".equals(type)){
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
            else if (isGoogleDriveUri(uri)){
                return getDataColumn(context,uri,null,null);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }else if (isGoogleDriveUri(uri)){
                Log.e("getLastPathSegment",uri.getLastPathSegment());
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

    public static Uri getOutputMediaFileUri(int mediaTypeImage, Context mContext) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileName = createFile();
                return FileProvider.getUriForFile(mContext, SHARED_PROVIDER_AUTHORITY, fileName);
            } else {
                return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getOutputMediaFile(int type) {
        File mediaStorageDir =
                new File(Environment.getExternalStorageDirectory() + File.separator + "/ClarisApp");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(Environment.getExternalStorageDirectory() + File.separator +
                    "/ClarisApp" +
                    "/" + "IMG_AN_" + System.currentTimeMillis() + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    @NonNull
    public static File createFile() {
        File mediaStorageDir =
                new File(Environment.getExternalStorageDirectory() + File.separator + "/ClarisApp");
        mediaStorageDir.mkdirs();

        File sharedFile = null;
        try {
            sharedFile = File.createTempFile("IMG_AN_", ".jpg", mediaStorageDir);
            sharedFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sharedFile;
    }

    public static String userDeviceId(Context context){
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }
}