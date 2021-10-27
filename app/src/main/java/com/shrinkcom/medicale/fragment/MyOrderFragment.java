package com.shrinkcom.medicale.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.shrinkcom.medicale.Adapter.ProductListAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.APPUTILS;
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.ShowMyorder;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyOrderFragment extends Fragment {

 RecyclerView recycler_product_all;
 View view;
 UserSession userSession;
 String user_id;
 LinearLayout layy;
    private ArrayList<ShowMyorder> listModels = new ArrayList<ShowMyorder>();

    private ProductListAdapter productListAdapter;
    public static Fragment newInstance() {

        return new MyOrderFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_order, container, false);

        recycler_product_all = view.findViewById(R.id.recycler_product_all);
        layy = view.findViewById(R.id.layy);

        userSession = new UserSession(getContext());
        user_id = userSession.read(ConstantApi.pre_user_id,"");



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
            listModels.clear();

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.show_ordersUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jo = jsonArray.getJSONObject(i);
                                String order_id = jo.getString("order_id");
                                String file = jo.getString("file");
                                String user_id = jo.getString("user_id");
                                String pharmacy_id = jo.getString("pharmacy_id");
                                String requested_pharmacy = jo.getString("requested_pharmacy");
                                String statuss = jo.getString("status");
                                String created_at = jo.getString("created_at");
                                String coupon_name = jo.getString("coupon_name");
                                String coupon_amount = jo.getString("coupon_amount");
                                String coupon_type = jo.getString("coupon_type");
                                String order_no = jo.getString("order_no");
                                String order_status = jo.getString("order_status");
                                String total_amount = jo.getString("total_amount");
                                String user_first_name = jo.getString("user_first_name");
                                String user_last_name = jo.getString("user_last_name");
                                String user_street_address = jo.getString("user_street_address");
                                String user_city = jo.getString("user_city");
                                String user_postal_code = jo.getString("user_postal_code");
                                String user_state = jo.getString("user_state");
                                String user_email = jo.getString("user_email");
                                String user_phone = jo.getString("user_phone");
                                String product_status = jo.getString("product_status");
                                String product_count = jo.getString("product_count");

//                            grand_total = String.valueOf(jsonObject1.getInt("grand_total"));
//
//
//                            txt_total.setText("$"+" "+String.valueOf(total));
                                listModels.add(new ShowMyorder(order_id, file, user_id, pharmacy_id, requested_pharmacy, statuss, created_at, coupon_name, coupon_amount,
                                        coupon_type, order_no, order_status, total_amount, user_first_name, user_last_name, user_street_address, user_city, user_postal_code,
                                        user_state, user_email, user_phone, product_status, product_count));
                                recycler_product_all.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                recycler_product_all.setItemAnimator(new DefaultItemAnimator());
                                productListAdapter = new ProductListAdapter(getContext(), listModels, new ButtonClick() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onClick(String id) {
                                        deletecart(id);
//                                    startActivity(new Intent(getActivity(),PharmacyFragment.class));
                                    }

                                    @Override
                                    public void onClickItem(String card_id, String quantity) {

                                    }
                                });
                                recycler_product_all.setAdapter(productListAdapter);

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
                    params.put("user_id", "" + user_id);

                    params.put("action", "show_orders");
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

    private void deletecart(String order_id) {
        if (APPUTILS.isNetworkConnected(getContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.cancel_pro_orderUrl, new Response.Listener<String>() {
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
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Loadingdata();
//                        myCartModels.remove(position);
//                        int newPosition = holder.getAdapterPosition();
//                        myCartModels.remove(newPosition);
//                        notifyItemRemoved(newPosition);
//                        notifyItemRangeChanged(newPosition, myCartModels.size());
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
                    params.put("user_id", user_id);
                    params.put("order_id", order_id);
                    params.put("action", "cancel_pro_order");
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