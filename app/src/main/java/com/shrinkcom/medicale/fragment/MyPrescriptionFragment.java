package com.shrinkcom.medicale.fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.shrinkcom.medicale.Adapter.CategoryAdapter;
import com.shrinkcom.medicale.Adapter.MyPrescriptionAdapter;
import com.shrinkcom.medicale.Adapter.ProductListAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.APPUTILS;
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.activity.PharmacyFragment;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.MyPresciptionmodel;
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


public class MyPrescriptionFragment extends Fragment implements ButtonClick {
    View view;
    RecyclerView recycler_product_prescription;
    String User_id;
    UserSession userSession;
    LinearLayout layy;

    MyPrescriptionAdapter myPrescriptionAdapter;
    ArrayList<MyPresciptionmodel> myPresciptionmodels = new ArrayList<>();

    public static Fragment newInstance() {

        return new MyPrescriptionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_prescription, container, false);

        userSession = new UserSession(getContext());
        User_id = userSession.read(ConstantApi.pre_user_id,"");

        recycler_product_prescription = view.findViewById(R.id.recycler_product_prescription);
        layy = view.findViewById(R.id.layy);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        Loadingdata();

//        prepareCourse();


        return view;
    }
//    private void prepareCourse() {
//        MyPresciptionmodel productListModel = new MyPresciptionmodel("dws");
//
//        myPresciptionmodels.add(productListModel);
//        myPresciptionmodels.add(productListModel);
//        myPresciptionmodels.add(productListModel);
//        myPresciptionmodels.add(productListModel);
//        myPresciptionmodels.add(productListModel);
//
//    }

    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            myPresciptionmodels.clear();
            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.show_prescriptionUrl, new Response.Listener<String>() {
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
                            if(jsonArray.length()!=0) {
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

                                    myPresciptionmodels.add(new MyPresciptionmodel(order_id, file, user_id, pharmacy_id, requested_pharmacy, statuss, created_at, coupon_name, coupon_amount, coupon_type, order_no, order_status,
                                            total_amount, user_first_name, user_last_name, user_street_address, user_city, user_postal_code, user_state, user_email, user_phone
                                            , product_status, product_count));

                                    recycler_product_prescription.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recycler_product_prescription.setHasFixedSize(true);
                                    myPrescriptionAdapter = new MyPrescriptionAdapter(getContext(), myPresciptionmodels, new ButtonClick() {
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
                                    recycler_product_prescription.setAdapter(myPrescriptionAdapter);


                                }
                            }else {
                                layy.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getContext(), "No data ", Toast.LENGTH_SHORT).show();
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
                    params.put("user_id", "" + User_id);

                    params.put("action", "user_login");
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
    private void deletecart(String order_id) {
        if (APPUTILS.isNetworkConnected(getContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.cancel_presc_orderUrl, new Response.Listener<String>() {
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
                    params.put("user_id", User_id);
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


    @Override
    public void onClick(String value) {
        Toast.makeText(getContext(), "buttonclick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickItem(String card_id, String quantity) {

    }
}

