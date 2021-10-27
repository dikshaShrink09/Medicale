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
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {
    TextView  txt_back_to_login;
    AppCompatButton btn_reset_pass;
    String email,pin;
    OtpView otpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getSupportActionBar().hide();
        txt_back_to_login = findViewById(R.id.txt_back_to_login);
        btn_reset_pass = findViewById(R.id.btn_reset_pass);
        otpView = findViewById(R.id.otp_view);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override public void onOtpCompleted(String otp) {
                pin = otp;

                // do Stuff
                Log.d("onOtpCompleted=>", otp);
            }
        });


        txt_back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });



        btn_reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                matchotp(email,pin);

            }
        });
    }
    private void matchotp(final String email,String pin) {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(OtpActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.verify_otpUrl, new Response.Listener<String>() {
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
                            Toast.makeText(OtpActivity.this, msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(OtpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                    params.put("otp", pin);
                    params.put("action", "verify_otp");
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