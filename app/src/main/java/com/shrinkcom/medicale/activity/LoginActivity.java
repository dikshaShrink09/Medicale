package com.shrinkcom.medicale.activity;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    TextView txt_forgot_pass,txt_register_now;
    EditText edt_name,edt_pass;
    AppCompatButton btn_login;
    String email_id,pass;
    UserSession userSession;
    ProgressDialog progressDialog;
    LinearLayout mainprogress;
    RelativeLayout transparent_layer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        userSession = new UserSession(LoginActivity.this);
        initview();

        txt_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);

            }
        });

        txt_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_id = edt_name.getText().toString().trim();
                pass = edt_pass.getText().toString().trim();


                if (!validateUsername(email_id)) {

                } else if (!validatePassword(pass)) {

                } else {


                    LoginApi(email_id,pass);
                }



//                Intent intent = new Intent(LoginActivity.this,NavigationActivity.class);
//                startActivity(intent);

            }
        });

    }



  void  initview()
  {
      userSession = new UserSession(LoginActivity.this);
      btn_login = findViewById(R.id.btn_login);
      txt_forgot_pass = findViewById(R.id.txt_forgot_pass);
      txt_register_now = findViewById(R.id.txt_register_now);
      edt_name = findViewById(R.id.edt_name);
      edt_pass = findViewById(R.id.edt_pass);
      mainprogress = findViewById(R.id.mainprogress);
      transparent_layer = findViewById(R.id.transparent_layer);


  }
    private boolean validateUsername(String email) {
        email_id = edt_name.getText().toString().trim();
        pass = edt_pass.getText().toString().trim();


        if (email.length() < 4 || email.length() > 30) {
            edt_name.setError(getResources().getString(R.string.email_valid));
            return false;
        }
        else if (!email.matches("^[A-za-z0-9.@]+")) {
            edt_name.setError(getResources().getString(R.string.email_valid_two));
            return false;
        }
        else if (!email.contains("@") || !email.contains(".")) {
            edt_name.setError(getResources().getString(R.string.email_valid_three));
            return false;
        }
        return true;
    }
    private boolean validatePassword(String pass) {
        if (pass.length() < 4 || pass.length() > 20) {
            edt_pass.setError(getResources().getString(R.string.pass_valid));
            return false;
        }
        return true;
    }


    // TODO:26/1/2021 Userlogin
    private void LoginApi(final String email, final String password) {
        if (APPUTILS.isNetworkConnected(LoginActivity.this)) {

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();

            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.LoginUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject json = new JSONObject(response);
                        String msg = json.getString("message");

                        if (msg.equals("success")) {
                            JSONObject resultObj = json.getJSONObject("data");
                            String email = resultObj.getString("email");
                            String full_name = resultObj.getString("full_name");
                            String mobile_number = resultObj.getString("mobile");
                            String id = resultObj.getString("id");
                            String pincode = resultObj.getString("pincode");
                            String address = resultObj.getString("address");
                            String photo = resultObj.getString("photo");
                            userSession.createLoginSession(id, full_name, email, mobile_number, photo, pincode, address);
                            progressDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            //JSONObject resultObj = response.getJSONObject("msg");
                            progressDialog.dismiss();

                        }
                        // editor.putBoolean(Constant.IS_LOGIN, true);

                    } catch (Exception e) {
                    }
//                progressDialog.dismiss();
                    progressDialog.dismiss();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                    progressDialog.dismiss();
                    error.printStackTrace();
                    Log.e("ERRORRRRR", "---->" + error);

                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
//action=register,username,email,phone,password,profile
                    //  params.put("username", ""+username);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("action", "user_login");
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
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