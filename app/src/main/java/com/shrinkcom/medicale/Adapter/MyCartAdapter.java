package com.shrinkcom.medicale.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.CartButtonClick;
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.activity.NavigationActivity;
import com.shrinkcom.medicale.activity.ProductOneActivity;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.MyCartModel;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {

    Context context;
    private List<MyCartModel> myCartModels = new ArrayList<MyCartModel>();
    UserSession userSession;
    String user_id;
    static int counter = 1;
    ButtonClick buttonClick;
    static int productcount ;
    CartButtonClick cartButtonClick;

    public MyCartAdapter(Context context, ArrayList<MyCartModel> categoryModelArrayList,UserSession userSession,CartButtonClick buttonClick) {
        this.context = context;
        this.myCartModels = categoryModelArrayList;
        this.userSession = userSession;
        this.cartButtonClick = buttonClick;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_to_cart, parent, false);

        return new MyCartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        user_id = userSession.read(ConstantApi.pre_user_id,"");
        MyCartModel myCartModel = myCartModels.get(position);
        holder.tv_product_name.setText(myCartModel.getProduct_name());
        productcount = Integer.parseInt(myCartModel.getProduct_count());
        holder.tv_no_of_product.setText(String.valueOf(productcount));
        double tottalp = Integer.parseInt(myCartModel.getPrice_discounted())*productcount;
        holder.tv_total_price.setText("OMR"+" "+String.valueOf(tottalp));

        Picasso.with(context)
                .load("https://shrinkcom.com/pharma/" + myCartModel.getProduct_image())
                .into(holder.img_product_image);

        holder.iv_delete_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletecart(myCartModel.getCart_id(),myCartModels.get(position),holder);
            }
        });

        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mainprice = Double.parseDouble(myCartModel.getPrice_discounted());
                productcount++;
                holder.tv_no_of_product.setText(String.valueOf(productcount));
                double  total = mainprice * productcount;
                holder.tv_total_price.setText(String.valueOf(total));
                cartButtonClick.onClickItems(myCartModel.getCart_id(), String.valueOf(productcount));

//                itemplus(mainprice,counter);
            }
        });
        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productcount>1) {
                    double mainprice = Double.parseDouble(myCartModel.getPrice_discounted());
                    productcount--;
                    holder.tv_no_of_product.setText(String.valueOf(productcount));
                    double total = mainprice * productcount;
                    holder.tv_total_price.setText(String.valueOf(total));

                    cartButtonClick.onClickItems(myCartModel.getCart_id(), String.valueOf(productcount));

                }
//                itemplus(mainprice,counter);
            }
        });


    }

    private void itemplus(double mainprice, int counter) {
        double  total = mainprice * counter;


    }


    private void deletecart(String cart_id, MyCartModel position, MyViewHolder holder) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.delete_cartUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                String resultResponse = new String(response);
                Log.e("resultResponseabc", "--->" + resultResponse);
                try {
                    JSONObject json = new JSONObject(response);
                    String msg = json.getString("message");
                    String status = json.getString("status");
                    if(status.equals("1")) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
//                        myCartModels.remove(position);
                        int newPosition = holder.getAdapterPosition();
                        myCartModels.remove(newPosition);
                        notifyItemRemoved(newPosition);
                        notifyItemRangeChanged(newPosition, myCartModels.size());
                        cartButtonClick.onbuttonclick();
//                        fatchcart();

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


//                        Intent intent = new Intent(getContext(), NavigationActivity.class);
//                        startActivity(intent);

                    }else {

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        //JSONObject resultObj = response.getJSONObject("msg");
                        progressDialog.dismiss();

                    }
                    // editor.putBoolean(Constant.IS_LOGIN, true);

                }catch (Exception e){}
                progressDialog.dismiss();

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
                params.put("user_id",  user_id);
                params.put("cart_id",  cart_id);
                params.put("action", "delete_cart" );
                Log.e("paramsvaluee", ">>" + params);
                return params;
            }};
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


        // addToRequestQueue(multipartRequest);
    }
//    private void fatchcart() {
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setCancelable(true);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.progress_layout);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.cart_itemsUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                String resultResponse = new String(response);
//                Log.e("resultResponseabc", "--->" + resultResponse);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String status = jsonObject.getString("status");
//                    if (status.equals("1")) {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                        JSONArray jsonArray = jsonObject1.getJSONArray("cart");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject jo = jsonArray.getJSONObject(i);
//
////                            String cart_id = jo.getString("cart_id");
////                            String product_id = jo.getString("product_id");
////                            String user_id = jo.getString("user_id");
////                            String product_count = jo.getString("product_count");
////                            String product_name = jo.getString("product_name");
////                            String product_description = jo.getString("product_description");
////                            String product_price = jo.getString("product_price");
////                            String product_quantity = jo.getString("product_quantity");
////                            String price_discounted = jo.getString("price_discounted");
////                            String product_image = jo.getString("product_image");
////                            String product_indication = jo.getString("product_indication");
////                            String product_packing = jo.getString("product_packing");
////                            String product_discount = jo.getString("product_discount");
////                            String category_id = jo.getString("category_id");
////                            String sub_category_id = jo.getString("sub_category_id");
////
////                            String created_by = jo.getString("created_by");
////                            String approved_by = jo.getString("approved_by");
////                            String brand_id = jo.getString("brand_id");
////                            String created_at = jo.getString("created_at");
////                            String statuss = jo.getString("status");
////                            String subtotal = jsonObject1.getString("total");
//                            ConstantApi.cart_count = jsonObject1.getString("cart_count");
//
////                            maintext.setText(ConstantApi.cart_count);
//
////                            NavigationActivity.showBadge(getApplicationContext(),NavigationActivity.navigation,R.id.navigation__my_cart,product_count);
//
//
////                            txt_subtotal.setText("$"+" "+subtotal);
////                            txt_total.setText("$"+" "+subtotal);
////                            myCartModelArrayList.add(new MyCartModel(cart_id,product_id,user_id,product_count,product_name,
////                                    product_description,product_price,product_quantity,product_image,price_discounted,product_indication,
////                                    product_packing,product_discount,category_id,sub_category_id,created_by,approved_by,brand_id,created_at,statuss));
////
////
//////                            pharmacyListModels.add(new PharmacyListModel(
//////                                    id,first_name,pharmacy_name,logo,country,state,city,locality,mobile,vendor_type,vendor_id,last_name,pincode,
//////                                    delivery_address,statuss,subscription_status));
////                            myCartAdapter = new MyCartAdapter(getContext(), myCartModelArrayList);
////
////                            recycler_my_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
////                            recycler_my_cart.setItemAnimator(new DefaultItemAnimator());
////                            recycler_my_cart.setAdapter(myCartAdapter);
//
//
//                        }
//                    }else {
//                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//
//        },  new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                error.printStackTrace();
//                Log.e("ERRORRRRR","---->"+error);
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<>();
////action=register,username,email,phone,password,profile
//                params.put("user_id", ""+user_id);
//
////                params.put("action", "user_login" );
//                Log.e("paramsvaluee", ">>" + params);
//                return params;
//            }};
//        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
//
//        // addToRequestQueue(multipartRequest);
//    }



    @Override
    public int getItemCount() {
        return (null != myCartModels ? myCartModels.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_name,tv_total_price;
        ImageView img_product_image,iv_delete_cart;
        TextView tv_plus,tv_minus,tv_no_of_product;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_total_price = itemView.findViewById(R.id.tv_total_price);
            img_product_image = itemView.findViewById(R.id.img_product_image);
            iv_delete_cart = itemView.findViewById(R.id.iv_delete_cart);
            tv_plus = itemView.findViewById(R.id.tv_plus);
            tv_minus = itemView.findViewById(R.id.tv_minus);
            tv_no_of_product = itemView.findViewById(R.id.tv_no_of_product);

        }
    }
}
