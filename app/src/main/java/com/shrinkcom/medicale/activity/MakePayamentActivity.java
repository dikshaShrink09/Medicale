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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.Adapter.CheckoutAdapter;
import com.shrinkcom.medicale.Adapter.MakePaymentAdapter;
import com.shrinkcom.medicale.Adapter.ProductInformationAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.MyorderDetalsmodel;
import com.shrinkcom.medicale.model.checkout_model;
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

public class MakePayamentActivity extends AppCompatActivity {
   RecyclerView recycler_product_name;
    private List<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    CheckoutAdapter makePaymentAdapter;
    ImageView back;
    TextView tv_cancel,tv_apply;
    AppCompatButton btn_re_order;
    TextView txt_item_total,txt_delivery_chas_price,txt_total,txt_headingx;
    ArrayList<checkout_model> summarytotalmodels = new ArrayList<>();

    String order_id,UserId,grand_total;
    UserSession userSession;
    EditText edt_coupoen;
    LinearLayout ll3,layy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);
        getSupportActionBar().hide();
        back = findViewById(R.id.back);
        recycler_product_name = findViewById(R.id.recycler_product_name);
        btn_re_order = findViewById(R.id.btn_re_order);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_apply = findViewById(R.id.tv_apply);
        edt_coupoen = findViewById(R.id.edt_coupoen);
        ll3 = findViewById(R.id.ll3);
        layy = findViewById(R.id.layy);

        txt_item_total = findViewById(R.id.txt_item_total);
        txt_delivery_chas_price = findViewById(R.id.txt_delivery_chas_price);
        txt_total = findViewById(R.id.txt_total);

        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");

        userSession = new UserSession(getApplicationContext());
        UserId = userSession.read(ConstantApi.pre_user_id,"");
//        makePaymentAdapter = new MakePaymentAdapter(this,categoryModelArrayList);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);



        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coupen = edt_coupoen.getText().toString();
                if (coupen.equals("")){
                    Toast.makeText(MakePayamentActivity.this, "Please enter coupon", Toast.LENGTH_SHORT).show();
                }else {
                    Applycoupon(coupen);
                }

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_coupoen.setText("");
            }
        });

        btn_re_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place_order();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Loadingdata();

    }
    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(MakePayamentActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.checkout_summaryUrl, new Response.Listener<String>() {
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
                            JSONArray jsonArray = jsonObject1.getJSONArray("products");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jo = jsonArray.getJSONObject(i);
                                String product_count = jo.getString("product_count");
                                String product_id = jo.getString("product_id");
                                String product_name = jo.getString("product_name");
                                String product_description = jo.getString("product_description");
                                String product_price = jo.getString("product_price");
                                String product_quantity = jo.getString("product_quantity");
                                String price_discounted = jo.getString("price_discounted");
                                String product_image = jo.getString("product_image");
                                String product_indication = jo.getString("product_indication");
                                String product_discount = jo.getString("product_discount");
                                String category_id = jo.getString("category_id");
                                String sub_category_id = jo.getString("sub_category_id");
                                String created_by = jo.getString("created_by");
                                String approved_by = jo.getString("approved_by");
                                String brand_id = jo.getString("brand_id");
                                String created_at = jo.getString("created_at");
                                String statuss = jo.getString("status");
                                String vendor_type = jo.getString("vendor_type");


//                            int subtotal = jsonObject1.getInt("amount");
//
                                grand_total = jsonObject1.getString("grand_total");
//
//                            grand_total = String.valueOf(jsonObject1.getString("total_amount"));
//                            txt_item_total.setText("OMR"+" "+String.valueOf(subtotal));
                                txt_total.setText("OMR" + " " + String.valueOf(grand_total));
//
//
                                txt_delivery_chas_price.setText("OMR" + product_discount);
//                            txt_delivery_charges_price.setText("0.00");

                                summarytotalmodels.add(new checkout_model(product_count, product_id, product_name, product_count, product_price,
                                        product_description, product_quantity, price_discounted, product_image, product_indication, product_discount, category_id, sub_category_id,
                                        created_by, approved_by, brand_id, created_at, statuss, vendor_type)
                                );
                                recycler_product_name.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                recycler_product_name.setItemAnimator(new DefaultItemAnimator());
                                makePaymentAdapter = new CheckoutAdapter(getApplicationContext(), summarytotalmodels);
                                recycler_product_name.setAdapter(makePaymentAdapter);
                            }
                        } else {
                            layy.setVisibility(View.VISIBLE);
                            ll3.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
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
                    params.put("order_id", "" + order_id);

                    params.put("action", "order_details");
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
    private void place_order() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(MakePayamentActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.place_orderUrl, new Response.Listener<String>() {
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
                    params.put("order_id", order_id);
                    params.put("action", "place_order");
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
        }else  {

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
    private void Applycoupon(String coupon) {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(MakePayamentActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.apply_coupon, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject json = new JSONObject(response);
                        String msg = json.getString("message");
                        if (msg.equals("success")) {
                            JSONObject jsonObject = json.getJSONObject("data");
                            int finalprice = jsonObject.getInt("final_price");
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            txt_total.setText(String.valueOf(finalprice));
//                        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
//                        startActivity(intent);

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
                    params.put("order_id", order_id);
                    params.put("coupon_name", coupon);
                    params.put("grand_total", grand_total);
                    params.put("action", "apply_coupon");
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


