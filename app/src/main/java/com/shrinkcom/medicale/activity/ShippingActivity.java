package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.Adapter.MakePaymentAdapter;
import com.shrinkcom.medicale.DBhelper.GetUserModal;
import com.shrinkcom.medicale.DBhelper.LoginDataBaseAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.summarymodelpack.Datum;
import com.shrinkcom.medicale.model.summarytotalmodel;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingActivity extends AppCompatActivity {
    RecyclerView recycler_product_name;
    private List<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    MakePaymentAdapter makePaymentAdapter;
    ImageView back;
    TextView fullname,tv_city,tv_code,tv_street,tv_state,tv_email,tv_phone;
    LoginDataBaseAdapter loginDataBaseAdapter;
    String name;
    private List<Datum> datumArrayList = new ArrayList<Datum>();
    UserSession userSession;
    AppCompatButton btn_re_order;
    String User_id;
    TextView txt_item_total,txt_delivery_chas_price,txt_total,txt_headingx;
    String full,city,code,street,state,email,phone,user_id,grand_total;

    ArrayList<summarytotalmodel> summarytotalmodels = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        getSupportActionBar().hide();
        back = findViewById(R.id.back);
        recycler_product_name = findViewById(R.id.recycler_product_name);

        userSession = new UserSession(getApplicationContext());
        user_id = userSession.read(ConstantApi.pre_user_id,"");

        userSession = new UserSession(getApplicationContext());
        User_id = userSession.read(ConstantApi.pre_user_id,"");

        fullname = findViewById(R.id.fullname);
        btn_re_order = findViewById(R.id.btn_re_order);
        txt_headingx = findViewById(R.id.txt_headingx);
        tv_city = findViewById(R.id.tv_city);
        tv_code = findViewById(R.id.tv_code);
        tv_street = findViewById(R.id.tv_street);
        tv_state = findViewById(R.id.tv_state);
        tv_email = findViewById(R.id.tv_email);
        tv_phone = findViewById(R.id.tv_phone);
        txt_item_total = findViewById(R.id.txt_item_total);
        txt_delivery_chas_price = findViewById(R.id.txt_delivery_chas_price);
        txt_total = findViewById(R.id.txt_total);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        name = getIntent().getStringExtra("name");
        city = getIntent().getStringExtra("city");
        street = getIntent().getStringExtra("street");
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("zipcode");
        email = getIntent().getStringExtra("email");
        state = getIntent().getStringExtra("state");



            fullname.setText(name);
            tv_city.setText(city);
            tv_code.setText(code);
            tv_email.setText(email);
            tv_phone.setText(phone);
            tv_state.setText(state);
            tv_street.setText(street);
            Log.d("name>",name);


        Loadingdata();


//        prepareCourse();

        btn_re_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senrequest();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(ShippingActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.summaryUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("cart");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jo = jsonArray.getJSONObject(i);
                                String cart_id = jo.getString("cart_id");
                                String product_id = jo.getString("product_id");
                                String user_id = jo.getString("user_id");
                                String product_count = jo.getString("product_count");
                                String pharmacy_id = jo.getString("pharmacy_id");
                                String product_name = jo.getString("product_name");
                                String product_description = jo.getString("product_description");
                                String product_price = jo.getString("product_price");
                                String product_quantity = jo.getString("product_quantity");
                                String price_discounted = jo.getString("price_discounted");
                                String product_image = jo.getString("product_image");
                                String product_indication = jo.getString("product_indication");
                                String product_packing = jo.getString("product_packing");
                                String product_discount = jo.getString("product_discount");
                                String category_id = jo.getString("category_id");
                                String sub_category_id = jo.getString("sub_category_id");
                                String created_by = jo.getString("created_by");
                                String approved_by = jo.getString("approved_by");
                                String brand_id = jo.getString("brand_id");
                                String created_at = jo.getString("created_at");
                                String statuss = jo.getString("status");
                                String vendor_type = jo.getString("vendor_type");
                                int subtotal = jo.getInt("subtotal");
                                String discount = jo.getString("discount");
                                int total = jsonObject1.getInt("grand_total");
                                int sub_total = jsonObject1.getInt("sub_total");
                                grand_total = String.valueOf(jsonObject1.getInt("grand_total"));
                                txt_item_total.setText("OMR" + " " + String.valueOf(sub_total));
                                txt_total.setText("OMR" + " " + String.valueOf(total));
                                int discountedp = sub_total - total;

                                txt_delivery_chas_price.setText("OMR" + String.valueOf(discountedp));

                                summarytotalmodels.add(new summarytotalmodel(cart_id, product_id, user_id, product_count, pharmacy_id,
                                        product_name, product_description, product_price, product_quantity, price_discounted, product_image,
                                        product_indication, product_packing, product_discount, category_id, sub_category_id, created_by, approved_by, brand_id, created_at, statuss, subtotal, discount, total));

                                recycler_product_name.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recycler_product_name.setItemAnimator(new DefaultItemAnimator());
                                recycler_product_name.setAdapter(makePaymentAdapter);
                                makePaymentAdapter = new MakePaymentAdapter(ShippingActivity.this, summarytotalmodels);
                                recycler_product_name.setAdapter(makePaymentAdapter);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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
                    params.put("user_id", "" + User_id);

//                params.put("action", "user_login" );
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

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
    private void senrequest() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(ShippingActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.send_requestUrl, new Response.Listener<String>() {
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
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                            startActivity(intent);

                        } else {

                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
                    params.put("user_id", user_id);
                    params.put("grand_total", grand_total);
                    params.put("full_name", name);
                    params.put("street_address", street);
                    params.put("city", city);
                    params.put("postal_code", code);
                    params.put("state_country", state);
                    params.put("email", email);
                    params.put("phone_number", phone);
                    params.put("action", "send_request");
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


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