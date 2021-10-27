package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.medicale.Adapter.PharmacyListSpinnerAdapter;
import com.shrinkcom.medicale.MultipleImage.ImageAdapter;
import com.shrinkcom.medicale.MultipleImage.ImageModel;
import com.shrinkcom.medicale.MultipleImage.SelectedImageAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.CommonModel;
import com.shrinkcom.medicale.model.ImageAttachModel;
import com.shrinkcom.medicale.model.PharmacySpinnerModel;
import com.shrinkcom.medicale.retrofit.ApiService;
import com.shrinkcom.medicale.retrofit.RetrofitFactory;
import com.shrinkcom.medicale.retrofit.profilemodel.ProfileModel;
import com.shrinkcom.medicale.storage.UserSession;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.shrinkcom.medicale.activity.Functions.getUriPath;

public class MyPrescriptionUploadActivity extends AppCompatActivity {
    AppCompatButton btn_upload_prescription, btn_take_photo1;
    TextView txt_valid_prescription;

    PharmacyListSpinnerAdapter pharmacyListSpinnerAdapter;
    Spinner spinner_select_pharmacy;

    Context mContext;
    ImageView back, logo;
    List<PharmacySpinnerModel> pharmacySpinnerModelList = new ArrayList<>();
    static final int REQUEST_GALLERY_PHOTO = 2;
    private static final int PICK_FROM_GALLERY = 1;
    File mPhotoFile;
    MultipartBody.Part body;
    private String imagePath;
    private List<ImageAttachModel> imagePathList = new ArrayList<>();
    UserSession  userSession;
    private Uri fileUri;
    private String imageUri = "";
    List<ImageAttachModel>imageAttachModelList;
    File file;
    EditText et_email,et_phone,et_city,et_street,et_state,et_zipcode,et_fullname;

    String email,phone,city1,street,state,zipcode,fullname;
    File profileImageFile;


    List<MultipartBody.Part> parts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prescription_upload);
        getSupportActionBar().hide();
        mContext = this;

        userSession = new UserSession(getApplicationContext());
        imageAttachModelList = new ArrayList<>();

        initview();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        getProvince();

        btn_take_photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ((ActivityCompat.checkSelfPermission(MyPrescriptionUploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(MyPrescriptionUploadActivity.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(MyPrescriptionUploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
                    } else {
                        onPictureSelector(v.getContext());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        txt_valid_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prescription();
            }
        });
        btn_upload_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = et_email.getText().toString();
                street = et_street.getText().toString();
                city1 = et_city.getText().toString();
                zipcode = et_zipcode.getText().toString();
                fullname = et_fullname.getText().toString();
                state = et_state.getText().toString();
                phone = et_phone.getText().toString();
                if (fullname.equals("")) {
                    Toast.makeText(mContext, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (street.equals("")) {
                    Toast.makeText(mContext, "Please enter street", Toast.LENGTH_SHORT).show();

                } else if (city1.equals("")) {
                    Toast.makeText(mContext, "Please enter city", Toast.LENGTH_SHORT).show();

                } else if (zipcode.equals("")) {
                    Toast.makeText(mContext, "Please enter zipcode", Toast.LENGTH_SHORT).show();

                } else if (state.equals("")) {
                    Toast.makeText(mContext, "Please enter state", Toast.LENGTH_SHORT).show();

                } else if (email.equals("")) {
                    Toast.makeText(mContext, "Please enter email", Toast.LENGTH_SHORT).show();

                } else if (phone.equals("")) {
                    Toast.makeText(mContext, "Please enter phone number", Toast.LENGTH_SHORT).show();

                } else {

                    uploadPrescription();
                }
            }





//                btn_showMessage();

        });

    }

    public void btn_showMessage() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.upload_prescription_dialog, null);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        final TextView btn_take_photo = (TextView) mView.findViewById(R.id.btn_take_photo);
        AppCompatButton btn_login = (AppCompatButton) mView.findViewById(R.id.btn_login);


        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPrescriptionUploadActivity.this, NavigationActivity.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void prescription() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.valid_prescription_guid, null);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        final TextView btn_take_photo = (TextView) mView.findViewById(R.id.btn_take_photo);
        final ImageView img_cross = (ImageView) mView.findViewById(R.id.img_cross);
        AppCompatButton btn_login = (AppCompatButton) mView.findViewById(R.id.btn_login);


        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  /*  Intent intent = new Intent(MyPrescriptionUploadActivity.this, NavigationActivity.class);
                    startActivity(intent);*/
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void onPictureSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.prescription_layout);

//        LinearLayout layout_camera = (LinearLayout) dialog.findViewById(R.id.layout_camera);
//        layout_camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onSelectCamera();
//                dialog.dismiss();
//
//            }
//        });

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


    void initview() {
        txt_valid_prescription = findViewById(R.id.txt_valid_prescription);
        btn_upload_prescription = findViewById(R.id.btn_upload_prescription);
        spinner_select_pharmacy = findViewById(R.id.spinner_select_pharmacy);
        btn_take_photo1 = findViewById(R.id.btn_take_photo);
        et_city = findViewById(R.id.et_city);
        et_email = findViewById(R.id.et_emaill);
        et_fullname = findViewById(R.id.et_fullname);
        et_state = findViewById(R.id.et_state);
        et_street = findViewById(R.id.et_street);
        et_zipcode = findViewById(R.id.et_zip);
        et_phone = findViewById(R.id.et_phonenumber);

        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
    }

    public void getProvince() {

        pharmacyListSpinnerAdapter = new PharmacyListSpinnerAdapter(mContext, pharmacySpinnerModelList);
        spinner_select_pharmacy.setAdapter(pharmacyListSpinnerAdapter);
        pharmacyListSpinnerAdapter.notifyDataSetChanged();

        spinner_select_pharmacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                spinner_select_pharmacy.setSelection(position);


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void onSelectGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);


//one can be replaced with any action code
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                mPhotoFile = (new File(getRealPathFromUri(selectedImage)));

//                if (data.getClipData() != null) {
//                    int count = data.getClipData().getItemCount();
//                    Log.d("count>>", String.valueOf(count));
//                    //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
//                    for (int i = 0; i < count; i++) {
//                         fileUri = data.getClipData().getItemAt(i).getUri();
////                        fileUri = data.getData();
////                        imageUri = getRealPathFromUri( fileUri);
//                        getRealPathFromUri(fileUri);
////                        fileUri = data.getData();
////                        imageUri = getUriPath(mContext, fileUri);
////                        imageAttachModelList.add(fileUri.toString());
//                        Toast.makeText(mContext, "You have picked"+" " +String.valueOf(count)+"images", Toast.LENGTH_SHORT).show();
//                    }
//                    //do something with the image (save it to some directory or whatever you need to do with it here)
//                }
            } else if (data.getData() != null) {
                String imagePath = data.getData().getPath();
                Uri imgUri = data.getData();
                getRealPathFromUri(imgUri);
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        }
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
    private void uploadPrescription() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(MyPrescriptionUploadActivity.this);
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
                body = MultipartBody.Part.createFormData("prescription", profileImageFile.getName(), requestFile);

            } else {
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                body = MultipartBody.Part.createFormData("prescription", "", attachmentEmpty);

            }


            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "" + userSession.read(ConstantApi.pre_user_id, ""));
            RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), "" + fullname);
            RequestBody street_address = RequestBody.create(MediaType.parse("text/plain"), "" + street);
            RequestBody city = RequestBody.create(MediaType.parse("text/plain"), "" + city1);
            RequestBody postal_code = RequestBody.create(MediaType.parse("text/plain"), "" + zipcode);
            RequestBody state_country = RequestBody.create(MediaType.parse("text/plain"), "" + state);
            RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"), "" + phone);
            RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), "" + email);

            Call<CommonModel> req = apiService.postPrescription(body, user_id, first_name, street_address, city, postal_code, state_country, phone_number,email1);
            req.enqueue(new Callback<CommonModel>() {
                @Override
                public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                    progressDialog.dismiss();

                    Log.d("response----->", response.body().toString());

                    CommonModel profileUploadModel = new CommonModel();
                    profileUploadModel = response.body();
                    if (response.isSuccessful()) {

                        Log.d("response>>", response.toString());
                        Toast.makeText(getApplicationContext(), "" + profileUploadModel.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<CommonModel> call, Throwable t) {
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
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri){
        File file = new File(getUriPath(mContext,fileUri));
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        return MultipartBody.Part.createFormData(partName, file.getName(),requestBody);
    }
    public void addFile(String fileName, Uri filePath){
        ImageAttachModel imageAttachModel = new ImageAttachModel();
        imageAttachModel.setFileName(fileName);
        imageAttachModel.setFilePath(filePath);
        imageAttachModelList.add(imageAttachModel);


        Log.e("imageAttachModel",""+imageAttachModelList.size());
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }


}
