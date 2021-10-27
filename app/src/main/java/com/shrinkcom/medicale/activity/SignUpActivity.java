package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
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
import com.shrinkcom.medicale.volley.VolleyMultipartRequest;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    AppCompatButton btn_register;
    TextView txt_login_here;
    EditText edt_name,edt_email,edt_mobile_no,edt_pass;
    String strname,str_email,str_mobile,str_password;
    ProgressDialog progressDialog;
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        intiview();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
//                startActivity(intent);
               if (!validation()) {
                    return;
                }

//               callForSignup(strname,str_email,str_mobile,str_password);
               sendDataApi(strname,str_email,str_mobile,str_password);


            }
        });

        txt_login_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }
    void intiview()
    {
        userSession = new UserSession(getApplicationContext());
        btn_register = findViewById(R.id.btn_register);
        txt_login_here = findViewById(R.id.txt_login_here);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile_no = findViewById(R.id.edt_mobile_no);
        edt_pass = findViewById(R.id.edt_pass);
    }

    boolean validation() {
        boolean valid = true;
         strname = edt_name.getText().toString().trim();
        str_email= edt_email.getText().toString().trim();
       str_mobile = edt_mobile_no.getText().toString().trim();
        str_password = edt_pass.getText().toString().trim();



        if (strname.isEmpty()) {
            edt_name.setError(getResources().getString(R.string.name_please));
            valid = false;
        } else {
            edt_name.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            edt_email.setError(getResources().getString(R.string.enter_email));

            valid = false;
        } else {
            edt_email.setError(null);
        }
        if (str_mobile.length() < 5) {
            edt_mobile_no.setError(getResources().getString(R.string.enter_mobile));

            valid = false;
        } else {
            edt_mobile_no.setError(null);
        }

        if (str_password.isEmpty()) {
            edt_pass.setError(getResources().getString(R.string.enter_pass));
            valid = false;
        } else {
            edt_pass.setError(null);
        }

        return valid;
    }



    private void sendDataApi(final String name, final String email, final String userphone, final String userpassword) {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();

            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.RegisterUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);

                    try {

                        JSONObject json = new JSONObject(response);
                        String msg = json.getString("message");
                        String status = json.getString("status");

                        if (status.equals("1")) {
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
                            Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, NavigationActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                            //JSONObject resultObj = response.getJSONObject("msg");
                            progressDialog.dismiss();

                        }
                        // editor.putBoolean(Constant.IS_LOGIN, true);

                    } catch (Exception e) {
                    }
                    progressDialog.dismiss();

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
                    params.put("mobile_number", userphone);
                    params.put("password", userpassword);
                    params.put("full_name", name);
                    params.put("action", "user_reg");
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
        }else{

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