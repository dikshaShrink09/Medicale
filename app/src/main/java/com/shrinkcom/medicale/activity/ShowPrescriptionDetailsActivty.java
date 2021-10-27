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
import com.shrinkcom.medicale.Adapter.PrescriptioPaymentAdapter;
import com.shrinkcom.medicale.Adapter.ProductInformationAdapter;
import com.shrinkcom.medicale.Adapter.ShowPrescriptionDetailsAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.MyorderDetalsmodel;
import com.shrinkcom.medicale.model.ShowPrescriptionDetaislModel;
import com.shrinkcom.medicale.model.prescriptionpayamentmodel;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowPrescriptionDetailsActivty extends AppCompatActivity {
    RecyclerView recycler_product_name;
    TextView txt_item_total,txt_delivery_charges_price,txt_delivery_chas_price,txt_total;
    ShowPrescriptionDetailsAdapter showPrescriptionDetailsAdapter;
    String grand_total ;
    ArrayList<ShowPrescriptionDetaislModel>showPrescriptionDetaislModels = new ArrayList<>();
    String orderid;
    LinearLayout layy,ll3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prescription_details_activty);

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

            final ProgressDialog progressDialog = new ProgressDialog(ShowPrescriptionDetailsActivty.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.presc_medicinesurl, new Response.Listener<String>() {
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
                            JSONArray jsonArray = jsonObject1.getJSONArray("order_detail");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jo = jsonArray.getJSONObject(i);
                                String id = jo.getString("id");
                                String medicine_name = jo.getString("medicine_name");
                                String medicine_price = jo.getString("medicine_price");
                                String medicine_discount = jo.getString("medicine_discount");
                                String total_price = jo.getString("total_price");
                                String price_discounted = jo.getString("price_discounted");
                                String user_id = jo.getString("user_id");
                                String pharmacy_id = jo.getString("pharmacy_id");
                                String accepted_by_pharmacy = jo.getString("accepted_by_pharmacy");
                                String accepted_by_admin = jo.getString("accepted_by_admin");
                                String order_id = jo.getString("order_id");
                                String product_count = jo.getString("product_count");
                                String product_id = jo.getString("product_id");
                                String product_indication = jo.getString("product_indication");
                                String product_status = jo.getString("product_status");
                                int sub_total = jsonObject1.getInt("sub_total");
                                int discount = jsonObject1.getInt("discount");


                                grand_total = String.valueOf(jsonObject1.getInt("total_amount"));
                                txt_item_total.setText("OMR" + " " + String.valueOf(sub_total));
                                txt_total.setText("OMR" + " " + grand_total);
////
////
                                txt_delivery_chas_price.setText("OMR" + String.valueOf(discount));
                                txt_delivery_charges_price.setText("0.00");
                                showPrescriptionDetaislModels.add(new ShowPrescriptionDetaislModel(id, medicine_name, medicine_price, medicine_discount,
                                        total_price, price_discounted, user_id, pharmacy_id, accepted_by_pharmacy, accepted_by_admin, order_id, product_count, product_id,
                                        product_indication, product_status));

                                recycler_product_name.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                recycler_product_name.setItemAnimator(new DefaultItemAnimator());
                                showPrescriptionDetailsAdapter = new ShowPrescriptionDetailsAdapter(getApplicationContext(), showPrescriptionDetaislModels);
                                recycler_product_name.setAdapter(showPrescriptionDetailsAdapter);
                            }
                        } else {
                            layy.setVisibility(View.VISIBLE);
                            ll3.setVisibility(View.GONE);
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

//                params.put("action", "presc_summary" );
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