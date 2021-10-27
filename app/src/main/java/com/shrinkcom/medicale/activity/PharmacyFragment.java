package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.Adapter.PharmacyAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.PharmacyListModel;
import com.shrinkcom.medicale.model.SubcategoryModel;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PharmacyFragment extends  AppCompatActivity {
    ImageView back, img_search_bar, img_cross;
    View view;
    TextView tv_tittle;
    LinearLayout ll_searching;
    Toolbar toolbar;
    PharmacyAdapter pharmacyAdapter;
    String upload_status;
    private List<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    private List<PharmacyListModel> pharmacyListModels = new ArrayList<PharmacyListModel>();
    RecyclerView recycler_pharmacy;
    public static String sub_category_id;
    Context context;
    LinearLayout layy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        getSupportActionBar().hide();

        upload_status = getIntent().getStringExtra("upload_status");
        sub_category_id = getIntent().getStringExtra("sub_category_id");

        ll_searching = findViewById(R.id.ll_searching);
        img_cross = findViewById(R.id.img_cross);

        toolbar = findViewById(R.id.toolbar);
        back = findViewById(R.id.back);
        img_search_bar = findViewById(R.id.img_search_bar);
        recycler_pharmacy = findViewById(R.id.recycler_pharmacy);
        layy = findViewById(R.id.layy);

        context = this;

        pharmacyAdapter = new PharmacyAdapter(PharmacyFragment.this, (ArrayList<PharmacyListModel>) pharmacyListModels, new ButtonClick() {
            @Override
            public void onClick(String value) {
                if(upload_status==null)
                {
                    startActivity(new Intent(PharmacyFragment.this, ProductActivity.class));
                }
               else if(upload_status.equals("btn_upload")) {
                    Intent intent = new Intent(PharmacyFragment.this, MyPrescriptionUploadActivity.class);

                    startActivity(intent);
                }
                else {
                    startActivity(new Intent(PharmacyFragment.this, ProductActivity.class));
                }


            }

            @Override
            public void onClickItem(String card_id, String quantity) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PharmacyFragment.this);

//        prepareCourse();
        Loadingdata();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        img_search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.GONE);
                ll_searching.setVisibility(View.VISIBLE);

            }
        });
        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_searching.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);


            }
        });



    }


//    private void prepareCourse() {
//        CategoryModel categoryModel = new CategoryModel("Mad Max: Fury Road", "dws");
//        CategoryModel categoryModel1 = new CategoryModel("Mad Max: Fury Road", "dws");
//        CategoryModel categoryModel2 = new CategoryModel("Mad Max: Fury Road", "dws");
//        CategoryModel categoryModel3 = new CategoryModel("Mad Max: Fury Road", "dws");
//        CategoryModel categoryModel4 = new CategoryModel("Mad Max: Fury Road", "dws");
//        CategoryModel categoryModel5 = new CategoryModel("Mad Max: Fury Road", "dws");
//
//
//        categoryModelArrayList.add(categoryModel);
//        categoryModelArrayList.add(categoryModel1);
//        categoryModelArrayList.add(categoryModel2);
//        categoryModelArrayList.add(categoryModel3);
//        categoryModelArrayList.add(categoryModel4);
//        categoryModelArrayList.add(categoryModel5);
//    }
    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(PharmacyFragment.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.pharmacy_listUrl, new Response.Listener<String>() {
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
                            JSONArray jsonArray = jsonObject1.getJSONArray("pharmacy_list");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jo = jsonArray.getJSONObject(i);
                                String id = jo.getString("id");
                                String first_name = jo.getString("first_name");
                                String pharmacy_name = jo.getString("pharmacy_name");
                                String logo = jo.getString("logo");
                                String country = jo.getString("country");
                                String state = jo.getString("state");
                                String city = jo.getString("city");
                                String locality = jo.getString("locality");
                                String mobile = jo.getString("mobile");
                                String vendor_type = jo.getString("vendor_type");
                                String vendor_id = jo.getString("vendor_id");
                                String last_name = jo.getString("last_name");
                                String pincode = jo.getString("pincode");
                                String delivery_address = jo.getString("delivery_address");
                                String statuss = jo.getString("status");
                                String subscription_status = jo.getString("subscription_status");
                                pharmacyListModels.add(new PharmacyListModel(
                                        id, first_name, pharmacy_name, logo, country, state, city, locality, mobile, vendor_type, vendor_id, last_name, pincode,
                                        delivery_address, statuss, subscription_status));

                                recycler_pharmacy.setLayoutManager(new LinearLayoutManager(PharmacyFragment.this, LinearLayoutManager.VERTICAL, false));
                                recycler_pharmacy.setItemAnimator(new DefaultItemAnimator());
                                recycler_pharmacy.setAdapter(pharmacyAdapter);


                            }
                        } else {
                            layy.setVisibility(View.VISIBLE);
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
                    params.put("sub_category_id", "" + sub_category_id);

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

}




