package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.Adapter.MakePaymentAdapter;
import com.shrinkcom.medicale.Adapter.ProductInformationAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.MyorderDetalsmodel;
import com.shrinkcom.medicale.model.checkout_model;
import com.shrinkcom.medicale.model.summarytotalmodel;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductInformationActivity extends AppCompatActivity {

    RecyclerView recycler_product_name;
    TextView txt_item_total,txt_delivery_charges_price,txt_delivery_chas_price,txt_total;
    ProductInformationAdapter productInformationAdapter;
    String grand_total ;
    ArrayList<MyorderDetalsmodel>myorderDetalsmodels = new ArrayList<>();
    String orderid;
    LinearLayout ll3,layy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information2);
        getSupportActionBar().hide();


        recycler_product_name = findViewById(R.id.recycler_product_name);
        txt_item_total = findViewById(R.id.txt_item_total);
        txt_delivery_charges_price = findViewById(R.id.txt_delivery_charges_price);
        txt_delivery_chas_price = findViewById(R.id.txt_delivery_chas_price);
        txt_total = findViewById(R.id.txt_total);
        layy = findViewById(R.id.layy);
        ll3 = findViewById(R.id.ll3);

        orderid = getIntent().getStringExtra("order_id");
        Toast.makeText(this, orderid, Toast.LENGTH_SHORT).show();
        Loadingdata();

    }
    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(ProductInformationActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.order_detailsUrl, new Response.Listener<String>() {
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


                                int subtotal = jsonObject1.getInt("amount");
//
                                String total = jsonObject1.getString("total_amount");
//
//                            grand_total = String.valueOf(jsonObject1.getString("total_amount"));
                                txt_item_total.setText("OMR" + " " + String.valueOf(subtotal));
                                txt_total.setText("OMR" + " " + String.valueOf(total));
//
//
                                txt_delivery_chas_price.setText("OMR" + product_discount);
                                txt_delivery_charges_price.setText("0.00");


                                myorderDetalsmodels.add(new MyorderDetalsmodel(product_count, product_id, product_name, product_description, product_price,
                                        product_quantity, price_discounted, product_image, product_indication, product_packing, product_discount, category_id, sub_category_id,
                                        created_by, approved_by, brand_id, created_at, statuss, vendor_type)
                                );
                                recycler_product_name.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                recycler_product_name.setItemAnimator(new DefaultItemAnimator());
                                productInformationAdapter = new ProductInformationAdapter(getApplicationContext(), myorderDetalsmodels);
                                recycler_product_name.setAdapter(productInformationAdapter);
                            }
                        } else {
                            ll3.setVisibility(View.GONE);
                            layy.setVisibility(View.VISIBLE);
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
                    params.put("order_id", "" + orderid);

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
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

}