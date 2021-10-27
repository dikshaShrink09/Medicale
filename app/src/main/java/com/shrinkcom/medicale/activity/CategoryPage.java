package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.shrinkcom.medicale.Adapter.CategoryAdapter;
import com.shrinkcom.medicale.Adapter.CategoryMainAdapter;
import com.shrinkcom.medicale.Adapter.ProductAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.ProductModel;
import com.shrinkcom.medicale.model.productmodel.Products;
import com.shrinkcom.medicale.model.productmodel.SubCategory;
import com.shrinkcom.medicale.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryPage extends AppCompatActivity {

    List<SubCategory> subCategories = new ArrayList<>();
    RecyclerView recycler_subbb;
    String vendor_id;
    CategoryMainAdapter categoryMainAdapter;
    private List<Products> productModelArrayList = new ArrayList<Products>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        getSupportActionBar().hide();


        recycler_subbb = findViewById(R.id.recycler_subbb);
        vendor_id = getIntent().getStringExtra("vendor_id");

        Loadingdata();
    }

    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(CategoryPage.this);
            progressDialog.setCancelable(true);
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.products_listUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    Log.d("AnkushResponse", "onResponse: " + response);

                    Gson gsonObj = new Gson();
                    com.shrinkcom.medicale.model.productmodel.ProductModel productModel = gsonObj.fromJson(response.toString(), com.shrinkcom.medicale.model.productmodel.ProductModel.class);
                    try {
                        subCategories = productModel.getData().getSubCategory();
                        productModelArrayList = productModel.getData().getProductsList();
                        Log.d("arraysize", String.valueOf(subCategories.size()));
                        String status = productModel.getStatus();
                        if (status.equals("1")) {
                            if (subCategories.size() > 0) {

                                recycler_subbb.setLayoutManager(new GridLayoutManager(CategoryPage.this, 2));
                                recycler_subbb.setItemAnimator(new DefaultItemAnimator());
                                categoryMainAdapter = new CategoryMainAdapter(CategoryPage.this, subCategories, productModelArrayList);

                                recycler_subbb.setAdapter(categoryMainAdapter);
                            } else {
                                Toast.makeText(getApplicationContext(), "Emptylist", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

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
                    params.put("vendor_id", "" + vendor_id);

                    params.put("action", "products_list");
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