package com.shrinkcom.medicale.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.shrinkcom.medicale.Adapter.CategoryAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.SubcategoryModel;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MedicineCategoryActivity extends Fragment {
    RecyclerView recycler_category;
    private ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    View view;
    private CategoryAdapter categoryAdapter;
    ImageView back,img_add_to_cart;
    EditText edt_city_name,txt_search;
    String search_data = "";
    private static SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    TextView tv_tittle;
    private  ArrayList<SubcategoryModel> subcategoryModels = new ArrayList<>();
    LinearLayout layy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_medicine_category, container, false);
  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);*//*
        setContentView(R.layout.activity_medicine_category);
        getSupportActionBar().hide();*/
        initview();
       // back = findViewById(R.id.back);
/*

        search_data = txt_search.getText().toString().trim();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                finish();
            }
        });
*/


        categoryAdapter = new CategoryAdapter(getContext(), subcategoryModels, new ButtonClick() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(String value) {

               startActivity(new Intent(getActivity(),PharmacyFragment.class));
            }

            @Override
            public void onClickItem(String card_id, String quantity) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        Loadingdata();

            return view; }

    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.sub_categoriesUrl, new Response.Listener<String>() {
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
                            JSONArray jsonArray = jsonObject1.getJSONArray("category");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                String sub_cat_id = jo.getString("sub_cat_id");
                                String cat_id = jo.getString("cat_id");
                                String sub_cat_name = jo.getString("sub_cat_name");
                                String createdd_at = jo.getString("createdd_at");
                                String sub_cat_photo = jo.getString("sub_cat_photo");
                                subcategoryModels.add(new SubcategoryModel(sub_cat_id, cat_id, sub_cat_name, createdd_at, sub_cat_photo));
                                recycler_category.setLayoutManager(new GridLayoutManager(getContext(), 2));
                                recycler_category.setItemAnimator(new DefaultItemAnimator());
                                recycler_category.setAdapter(categoryAdapter);

                                // Do you fancy stuff
                                // Example: String gifUrl = jo.getString("url");
                            }
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            layy.setVisibility(View.VISIBLE);
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
                    //  params.put("username", ""+username);

//                params.put("action", "user_login" );
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
        }else{

            final Dialog dialog = new Dialog(getContext());
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

    //    private void prepareCourse() {
//        CategoryModel categoryModel = new CategoryModel("Mad Max: Fury Road","dws");
//        CategoryModel categoryModel1 = new CategoryModel("Mad Max: Fury Road","dws");
//        CategoryModel categoryModel2 = new CategoryModel("Mad Max: Fury Road","dws");
//        CategoryModel categoryModel3 = new CategoryModel("Mad Max: Fury Road","dws");
//        CategoryModel categoryModel4 = new CategoryModel("Mad Max: Fury Road","dws");
//        CategoryModel categoryModel5 = new CategoryModel("Mad Max: Fury Road","dws");
//
//
//        categoryModelArrayList.add(categoryModel);
//        categoryModelArrayList.add(categoryModel1);
//        categoryModelArrayList.add(categoryModel2);
//        categoryModelArrayList.add(categoryModel3);
//        categoryModelArrayList.add(categoryModel4);
//        categoryModelArrayList.add(categoryModel5);
//    }
    void initview()
    {
        edt_city_name = view.findViewById(R.id.edt_city_name);
        txt_search = view.findViewById(R.id.txt_search);
        recycler_category = view.findViewById(R.id.recycler_category);
        back= view.findViewById(R.id.back);
        img_add_to_cart= view.findViewById(R.id.img_add_to_cart);
        tv_tittle =view.findViewById(R.id.tv_tittle);
        layy =view.findViewById(R.id.layy);
    }


//TODO integrate search category api
    /*   private void loginApi(String search_data) {
        final ProgressDialog progressDialog = new ProgressDialog(MedicineCategoryActivity.this);
        progressDialog.setCancelable(true);
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestClient.ROOT_URL+"/webservices.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                String resultResponse = new String(response);
                Log.e("resultResponseabc", "--->" + resultResponse);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                LoginPojo userpojo = gson.fromJson(resultResponse,LoginPojo.class);
                if (userpojo.getResult()==1){
                    ;
                }else {
                    Toast.makeText(MedicineCategoryActivity.this, getResources().getString(R.string.exist_email), Toast.LENGTH_SHORT).show();
                }



            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Log.e("ERRORRRRR","---->"+error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
//action=register,username,email,phone,password,profile
                params.put("search", "" + email_id);

                return params;
            }};
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        // addToRequestQueue(multipartRequest);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ImageView searchIcon = ( searchItem.getActionView()).findViewById(R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.search_bar));
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                 //   Log.i("onQueryTextChangedad", newText);

//                    productAdapter.getFilter().filter(newText);

                 //   productAdapter.filter(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Log.i("onQueryTextSubmit", query);
                //    productAdapter.filter(query);
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.search:

                break;
        }
        return true;
    }

}