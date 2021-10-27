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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText et_old_pass,et_new_pass,et_confirm_password;
    Button btn_submit;
    String oldepass,newpass,user_id,confirm;
    UserSession userSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        et_old_pass = findViewById(R.id.et_old_pass);
        et_new_pass = findViewById(R.id.et_new_pass);
        btn_submit = findViewById(R.id.btn_submit);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        getSupportActionBar().hide();

        userSession = new UserSession(getApplicationContext());
        user_id = userSession.read(ConstantApi.pre_user_id,"");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldepass = et_old_pass.getText().toString();
                newpass = et_new_pass.getText().toString();
                confirm = et_confirm_password.getText().toString();

                if (!newpass.equals(confirm)){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter correct password", Toast.LENGTH_SHORT).show();
                }else {
                    changepassword(oldepass,newpass);
                }


            }
        });

    }
    private void changepassword(final String oldp,String newp) {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(ChangePasswordActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.change_passwordUrl, new Response.Listener<String>() {
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
                            Toast.makeText(ChangePasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                    params.put("old_password", oldp);
                    params.put("password", newp);
                    params.put("action", "reset_password");
                    params.put("user_id", user_id);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent  intent = new Intent(getApplicationContext(),NavigationActivity.class);
        startActivity(intent);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

}