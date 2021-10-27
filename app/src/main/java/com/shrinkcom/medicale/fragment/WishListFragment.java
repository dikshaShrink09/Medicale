package com.shrinkcom.medicale.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.Adapter.MyCartAdapter;
import com.shrinkcom.medicale.Adapter.WishListAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.APPUTILS;
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.GeneralCategoryModel;
import com.shrinkcom.medicale.model.WishListModel;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishListFragment extends Fragment {
View view ;
    RecyclerView recycler_my_cart;
   WishListAdapter myCartAdapter;
    private ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    private ArrayList<WishListModel> wishListModels = new ArrayList<WishListModel>();
    UserSession userSession;
    String User_id;
    LinearLayout layy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_wish_list, container, false);

        recycler_my_cart = view.findViewById(R.id.recycler_my_cart);
        layy = view.findViewById(R.id.layy);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        userSession = new UserSession(getContext());

        User_id = userSession.read(ConstantApi.pre_user_id,"");



//        prepareCourse();
        Loaddata();
   return view;
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

    private void Loaddata() {
        if (APPUTILS.isNetworkConnected(getContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.wishlist_viewUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                progressDialog.dismiss();
                    progressDialog.dismiss();

                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);

                                String wishlist_id = jo.getString("wishlist_id");
                                String pharmacy_id = jo.getString("pharmacy_id");
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

                                wishListModels.add(new WishListModel(wishlist_id, pharmacy_id, product_id, product_name, product_description, product_price, product_quantity,
                                        price_discounted, product_image, product_indication, product_packing, product_discount, category_id, sub_category_id,
                                        created_by, approved_by, brand_id, created_at, statuss, vendor_type));
                                recycler_my_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                recycler_my_cart.setItemAnimator(new DefaultItemAnimator());
                                myCartAdapter = new WishListAdapter(getContext(), wishListModels);
                                recycler_my_cart.setAdapter(myCartAdapter);


                                // Do you fancy stuff
                                // Example: String gifUrl = jo.getString("url");
                            }
                        } else {
                            layy.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();

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
                    params.put("user_id", "" + User_id);
                    params.put("action", "wishlist_view");

//                params.put("action", "user_login" );
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
        }else {

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

}