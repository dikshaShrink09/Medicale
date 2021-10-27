package com.shrinkcom.medicale.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.multidex.BuildConfig;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.retrofit.ApiService;
import com.shrinkcom.medicale.retrofit.RetrofitFactory;
import com.shrinkcom.medicale.retrofit.profilemodel.ProfileModel;
import com.shrinkcom.medicale.storage.UserSession;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {
    ImageView img_myprofile,img_add_profile;
    EditText tv_name,tv_email,tv_mobile,tv_pin_code,tv_address;
    AppCompatButton btn_update_profile;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    MultipartBody.Part body;
    Context context;
    private static final int PICK_FROM_GALLERY = 1;
    File profileImageFile, filePath;
    UserSession userSession;
    String ImageURL,mobilenumer;
     Dialog progressDialog;
     LinearLayout mainprogress;
    ProgressBar prog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        img_myprofile = findViewById(R.id.img_myprofile);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_pin_code = findViewById(R.id.tv_pin_code);
        tv_address = findViewById(R.id.tv_address);
        btn_update_profile = findViewById(R.id.btn_update_profile);
        img_add_profile = findViewById(R.id.img_add_profile);

        mainprogress = findViewById(R.id.mainprogress);
        userSession = new UserSession(getApplicationContext());
        context = this;


        tv_name.setText(userSession.read(ConstantApi.pre_first, ""));
        tv_pin_code.setText(userSession.read(ConstantApi.pre_pincode, ""));
        tv_address.setText(userSession.read(ConstantApi.pre_address, ""));
        tv_email.setText(userSession.read(ConstantApi.pre_email, ""));
        tv_mobile.setText(userSession.read(ConstantApi.pre_contact, ""));

        ImageURL = userSession.read(ConstantApi.image, "");

//        progressDialog = new ProgressDialog(ProfileActivity.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setContentView(R.layout.progress_layout);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//         prog = (ProgressBar) progressDialog.findViewById(R.id.dialog_loading);
//        prog.setVisibility(View.VISIBLE);



        Log.d("imageurl", "initiUI: "+ImageURL);
        if (!ImageURL.isEmpty()){
            Glide.with(getApplicationContext()).load("https://shrinkcom.com/pharma/" +ImageURL).into(img_myprofile);
        }else {
            Glide.with(getApplicationContext()).load(R.drawable.bg_profile).into(img_myprofile);
        }



        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobilenumer = tv_mobile.getText().toString();
                Log.d("mobilenumer",mobilenumer);

                callForImageupload();
            }
        });

        img_add_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if ((ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&(ActivityCompat.checkSelfPermission(ProfileActivity.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
                    } else {
                        onPictureSelector(v.getContext()) ;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void onPictureSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.custom_dialog_layout);

        LinearLayout layout_camera = (LinearLayout) dialog.findViewById(R.id.layout_camera);
        layout_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectCamera();
                dialog.dismiss();

            }
        });

        LinearLayout layout_gallery = (LinearLayout) dialog.findViewById(R.id.layout_gallery);
        layout_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSelectGallery();
                dialog.dismiss();
            }
        });

        dialog.show();


    }
    private void onSelectCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d("photoFile", photoFile.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        "com.shrinkcom.medicale" + ".provider",
                        photoFile);
                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }


    }
    private void onSelectGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);


//one can be replaced with any action code
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        Log.d("mFile", mFile.toString());
        return mFile;
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
//             try {
//                 mPhotoFile = mCompressor.compressToFile(mPhotoFile);
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
                Glide.with(ProfileActivity.this).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.person)).into(img_myprofile);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                mPhotoFile = (new File(getRealPathFromUri(selectedImage)));
                Glide.with(ProfileActivity.this).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.person)).into(img_myprofile);

            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }
    private void callForImageupload() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Log.d("progress", "progress");
            Retrofit retrofit = RetrofitFactory.getRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);

            if (mPhotoFile != null) {
                profileImageFile = new File(String.valueOf(mPhotoFile));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), profileImageFile);
                // MultipartBody.Part is used to send also the actual file name
                Log.d("imagepathh", "callForImageupload: " + profileImageFile.getName());
                body = MultipartBody.Part.createFormData("photo", profileImageFile.getName(), requestFile);

            } else {
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

                body = MultipartBody.Part.createFormData("image", "", attachmentEmpty);

            }


            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "" + userSession.read(ConstantApi.pre_user_id, ""));
            RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), "" + tv_name.getText().toString());
            RequestBody address = RequestBody.create(MediaType.parse("text/plain"), "" + tv_address.getText().toString());
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), "" + userSession.read(ConstantApi.pre_email, ""));
            RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), "" + mobilenumer);
            RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), "" + tv_pin_code.getText().toString());
            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "" + "edit_profile");

            Call<ProfileModel> req = apiService.postImage(body, user_id, first_name, phone, address, pincode, action);
            req.enqueue(new Callback<ProfileModel>() {
                @Override
                public void onResponse(Call<ProfileModel> call, retrofit2.Response<ProfileModel> response) {
                    progressDialog.dismiss();

                    Log.d("response----->", response.body().toString());

                    ProfileModel profileUploadModel = new ProfileModel();
                    profileUploadModel = response.body();
                    if (profileUploadModel.getStatus().equals("1")) {
                        Log.d("response>>", response.toString());
                        Toast.makeText(context, "" + profileUploadModel.toString(), Toast.LENGTH_SHORT).show();

                        String email = tv_email.getText().toString();
                        String fullname = profileUploadModel.getData().getFullName();
                        String contact = profileUploadModel.getData().getMobile();
                        String user_id = profileUploadModel.getData().getId();
                        String image = profileUploadModel.getData().getPhoto();
                        String pre_address = profileUploadModel.getData().getAddress();
                        String pre_pincode = profileUploadModel.getData().getPincode();

                        userSession.createLoginSession(user_id, fullname, email, contact, image, pre_pincode, pre_address);

                        progressDialog.dismiss();

                        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                        startActivity(intent);


                    }
                }

                @Override
                public void onFailure(Call<ProfileModel> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }

            });
            // Log.e("sasas", "o<<<<<>>>>>" + files_bytes.size());

        }else {
            final Dialog dialog = new Dialog(getApplicationContext());
            dialog.setContentView(R.layout.custom_dialog);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(dialog.getWindow().getAttributes());
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.CENTER;
            dialog.getWindow().setAttributes(params);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
            TextView retry = (TextView) dialog.findViewById(R.id.retry);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

}