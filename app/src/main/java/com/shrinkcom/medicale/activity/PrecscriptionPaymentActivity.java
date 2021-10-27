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
import com.shrinkcom.medicale.Adapter.ProductInformationAdapter;
import com.shrinkcom.medicale.Adapter.PrescriptioPaymentAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.prescriptionpayamentmodel;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrecscriptionPaymentActivity extends AppCompatActivity {
    RecyclerView recycler_product_name;
    TextView txt_item_total,txt_delivery_charges_price,txt_delivery_chas_price,txt_total;
    ProductInformationAdapter productInformationAdapter;
    String grand_total ;
    ArrayList<prescriptionpayamentmodel> prescriptionpayamentmodels = new ArrayList<>();
    String orderid;
    LinearLayout superr,layy;
    TextView tv_cancel,tv_apply;
    AppCompatButton btn_re_order;
    PrescriptioPaymentAdapter prescriptioPaymentAdapter;
    EditText edt_coupoen;
    LinearLayout ll3;
    ImageView back;

    String order_id,UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prescription_details);
        getSupportActionBar().hide();


        recycler_product_name = findViewById(R.id.recycler_product_name);
        txt_item_total = findViewById(R.id.txt_item_total);
        txt_delivery_charges_price = findViewById(R.id.txt_delivery_charges_price);
        txt_delivery_chas_price = findViewById(R.id.txt_delivery_chas_price);
        txt_total = findViewById(R.id.txt_total);
        superr = findViewById(R.id.superr);
        layy = findViewById(R.id.layy);
        back = findViewById(R.id.back);


        btn_re_order = findViewById(R.id.btn_re_order);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_apply = findViewById(R.id.tv_apply);
        edt_coupoen = findViewById(R.id.edt_coupoen);
        ll3 = findViewById(R.id.ll3);

        orderid = getIntent().getStringExtra("order_id");
        Toast.makeText(this, orderid, Toast.LENGTH_SHORT).show();

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


        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coupen = edt_coupoen.getText().toString();
                if (coupen.equals("")){
                    Toast.makeText(PrecscriptionPaymentActivity.this, "Please enter coupon", Toast.LENGTH_SHORT).show();
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

        Loadingdata();
    }
    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(PrecscriptionPaymentActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.presc_summaryUrl, new Response.Listener<String>() {
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
                            JSONArray jsonArray = jsonObject1.getJSONArray("order_details");
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


                                grand_total = String.valueOf(jsonObject1.getInt("total"));
                                txt_item_total.setText("OMR" + " " + String.valueOf(sub_total));
                                txt_total.setText("OMR" + " " + grand_total);
////
////
                                txt_delivery_chas_price.setText("OMR" + String.valueOf(discount));
                                txt_delivery_charges_price.setText("0.00");
                                prescriptionpayamentmodels.add(new prescriptionpayamentmodel(id, medicine_name, medicine_price, medicine_discount,
                                        total_price, price_discounted, user_id, pharmacy_id, accepted_by_pharmacy, accepted_by_admin, order_id, product_count, product_id,
                                        product_indication, product_status));

                                recycler_product_name.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                recycler_product_name.setItemAnimator(new DefaultItemAnimator());
                                prescriptioPaymentAdapter = new PrescriptioPaymentAdapter(getApplicationContext(), prescriptionpayamentmodels);
                                recycler_product_name.setAdapter(prescriptioPaymentAdapter);
                            }
                        } else {
                            superr.setVisibility(View.GONE);
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

                    params.put("action", "presc_summary");
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
    private void Applycoupon(String coupon) {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(PrecscriptionPaymentActivity.this);
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
                    params.put("order_id", orderid);
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
    private void place_order() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(PrecscriptionPaymentActivity.this);
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }


}

