package com.shrinkcom.medicale.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.Adapter.MyCartAdapter;
import com.shrinkcom.medicale.Adapter.MyPrescriptionAdapter;
import com.shrinkcom.medicale.Adapter.OrderHistoryAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.APPUTILS;
import com.shrinkcom.medicale.activity.AddDeliveryAddress;
import com.shrinkcom.medicale.activity.CartButtonClick;
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.activity.MyPrescriptionUploadActivity;
import com.shrinkcom.medicale.activity.NavigationActivity;
import com.shrinkcom.medicale.activity.PharmacyFragment;
import com.shrinkcom.medicale.activity.ShowAddressList;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.MyCartModel;
import com.shrinkcom.medicale.model.PharmacyListModel;
import com.shrinkcom.medicale.model.ProductListModel;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.shrinkcom.medicale.activity.NavigationActivity.showBadge;

public class MyCartFragment extends Fragment implements ButtonClick {
View view;
RecyclerView recycler_my_cart;
    MyCartAdapter myCartAdapter;
    AppCompatButton btn_place_order;
    private ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    private ArrayList<MyCartModel> myCartModelArrayList = new ArrayList<>();
    UserSession userSession;
    String User_id;
    TextView txt_subtotal,txt_total;
    TextView et_address;
    LinearLayout layy,maincart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_my_cart, container, false);

        recycler_my_cart = view.findViewById(R.id.recycler_my_cart);
        btn_place_order = view.findViewById(R.id.btn_place_order);
        txt_subtotal = view.findViewById(R.id.txt_subtotal);
        txt_total = view.findViewById(R.id.txt_total);
        layy = view.findViewById(R.id.layy);
        maincart = view.findViewById(R.id.maincart);


        et_address = view.findViewById(R.id.et_address);

        et_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAddressList.class);
                startActivity(intent);
            }
        });

        userSession = new UserSession(getContext());
        User_id = userSession.read(ConstantApi.pre_user_id,"");


//        myCartAdapter = new MyCartAdapter(getContext(), categoryModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        recycler_my_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        recycler_my_cart.setItemAnimator(new DefaultItemAnimator());
//        recycler_my_cart.setAdapter(myCartAdapter);
//        prepareCourse();
        Loadingdata();


        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ShowAddressList.class);
                startActivity(intent);


//                btn_showMessage();
            }
        });


        return view; }


    public void btn_showMessage() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.place_order_success, null);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        final TextView btn_take_photo = (TextView) mView.findViewById(R.id.btn_take_photo);
        AppCompatButton btn_login = (AppCompatButton) mView.findViewById(R.id.btn_login);

        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }





    private void prepareCourse() {
        CategoryModel categoryModel = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel1 = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel2 = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel3 = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel4 = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel5 = new CategoryModel("Mad Max: Fury Road","dws");


        categoryModelArrayList.add(categoryModel);
        categoryModelArrayList.add(categoryModel1);
        categoryModelArrayList.add(categoryModel2);
        categoryModelArrayList.add(categoryModel3);
        categoryModelArrayList.add(categoryModel4);
        categoryModelArrayList.add(categoryModel5);
    }
    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myCartModelArrayList.clear();

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.cart_itemsUrl, new Response.Listener<String>() {
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
                                String subtotal = jsonObject1.getString("total");


                                txt_subtotal.setText("$" + " " + subtotal);
                                txt_total.setText("$" + " " + subtotal);
                                myCartModelArrayList.add(new MyCartModel(cart_id, product_id, user_id, product_count, product_name,
                                        product_description, product_price, product_quantity, product_image, price_discounted, product_indication,
                                        product_packing, product_discount, category_id, sub_category_id, created_by, approved_by, brand_id, created_at, statuss));


//                            pharmacyListModels.add(new PharmacyListModel(
//                                    id,first_name,pharmacy_name,logo,country,state,city,locality,mobile,vendor_type,vendor_id,last_name,pincode,
//                                    delivery_address,statuss,subscription_status));
//                             myCartAdapter = new MyCartAdapter(getContext(), myCartModelArrayList,userSession);
                                myCartAdapter = new MyCartAdapter(getContext(), myCartModelArrayList, userSession, new CartButtonClick() {
                                    @Override
                                    public void onbuttonclick() {
                                        Loadingdata();
                                        fatchcart();
                                    }
                                    @Override
                                    public void onClickItems(String card_id, String quantity) {
                                        updatecart(card_id, quantity);
                                    }

                                });
                                recycler_my_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                recycler_my_cart.setItemAnimator(new DefaultItemAnimator());
                                recycler_my_cart.setAdapter(myCartAdapter);
                            }
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            layy.setVisibility(View.VISIBLE);
                            maincart.setVisibility(View.GONE);
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

    @Override
    public void onClick(String value) {

    }

    @Override
    public void onClickItem(String card_id, String quantity) {
//        updatecart(card_id,quantity);

    }
    private void updatecart(String cart_id, String qauntity) {
        if (APPUTILS.isNetworkConnected(getContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.update_cart, new Response.Listener<String>() {
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
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Loadingdata();

//                        myCartModels.remove(position);

//                        fatchcart();

                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();


//                        Intent intent = new Intent(getContext(), NavigationActivity.class);
//                        startActivity(intent);

                        } else {

                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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
                    params.put("user_id", User_id);
                    params.put("cart_id", cart_id);
                    params.put("quantity", qauntity);
                    params.put("action", "delete_cart");
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
    public void fatchcart() {
        if (APPUTILS.isNetworkConnected(getContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.cart_itemsUrl, new Response.Listener<String>() {
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
                                String subtotal = jsonObject1.getString("total");
                                ConstantApi.cart_count = jsonObject1.getString("cart_count");

                                showBadge(getContext(), R.id.navigation__my_cart, ConstantApi.cart_count);


//                            txt_subtotal.setText("$"+" "+subtotal);
//                            txt_total.setText("$"+" "+subtotal);
//                            myCartModelArrayList.add(new MyCartModel(cart_id,product_id,user_id,product_count,product_name,
//                                    product_description,product_price,product_quantity,product_image,price_discounted,product_indication,
//                                    product_packing,product_discount,category_id,sub_category_id,created_by,approved_by,brand_id,created_at,statuss));
//
//
////                            pharmacyListModels.add(new PharmacyListModel(
////                                    id,first_name,pharmacy_name,logo,country,state,city,locality,mobile,vendor_type,vendor_id,last_name,pincode,
////                                    delivery_address,statuss,subscription_status));
//                            myCartAdapter = new MyCartAdapter(getContext(), myCartModelArrayList);
//
//                            recycler_my_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//                            recycler_my_cart.setItemAnimator(new DefaultItemAnimator());
//                            recycler_my_cart.setAdapter(myCartAdapter);


                            }
                        } else {
                            showBadge(getContext(), R.id.navigation__my_cart, "0");

                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
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