package com.shrinkcom.medicale.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.activity.NavigationActivity;
import com.shrinkcom.medicale.activity.ProductActivity;
import com.shrinkcom.medicale.activity.ProductOneActivity;
import com.shrinkcom.medicale.activity.SingleProductActivity;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.productmodel.ProductModel;
import com.shrinkcom.medicale.model.productmodel.Products;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
 private String TAG="";
    Context context;
    private List<Products> productModelList ;
    UserSession userSession;
//    ValueFilter valueFilter;

    protected List<Products> filterList;
   ButtonClick buttonClick;
   String Vendor_id,User_id,liked;


    public ProductAdapter(Context context, List<Products> productModelList, ButtonClick buttonClick) {
        this.context = context;
        this.buttonClick = buttonClick;
        this.productModelList = productModelList;
        this.filterList = new ArrayList<>();
        this.userSession = new UserSession(context);
        this.filterList.addAll(this.productModelList);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drug_layout, parent, false);

        return new ProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Products productModel =  productModelList.get(position);

        Vendor_id = ProductActivity.vendor_id;
        User_id = ProductActivity.User_id;
        liked = userSession.read(ConstantApi.liked,"");



        holder.txt_product_name.setText(productModel.getProductName());
        holder.txt_rate.setText(productModel.getProductPrice());
        Picasso.with(context)
                .load("https://shrinkcom.com/pharma/" + productModel.getProductImage())
                .into(holder.category_image);

        if (productModel.getWishlist().equals("true")){
         holder.img_selected.setVisibility(View.VISIBLE);
         holder.img_unselected.setVisibility(View.GONE);
        }
        if (productModel.getWishlist().equals("false")){
            holder.img_selected.setVisibility(View.GONE);
            holder.img_unselected.setVisibility(View.VISIBLE);
        }



        holder.txt_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_id = productModel.getProductId();
                Intent intent = new Intent(context, ProductOneActivity.class);
                intent.putExtra("vendor_id",Vendor_id);
                intent.putExtra("product_id",product_id);
                context.startActivity(intent);
              /*  Intent intent = new Intent(context, SingleProductActivity.class);
                context.startActivity(intent);*/

            }
        });
        holder.img_unselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_id = productModel.getProductId();
                    if(holder.img_unselected.getVisibility()==View.VISIBLE) {
                    holder.img_selected.setVisibility(View.VISIBLE);
                    holder.img_unselected.setVisibility(View.GONE);
                    addTo_wishlist(product_id);
                }
            }
        });
        holder.img_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_id = productModel.getProductId();
                if (holder.img_selected.getVisibility()==View.VISIBLE) {
                    holder.img_unselected.setVisibility(View.VISIBLE);
                    holder.img_selected.setVisibility(View.GONE);
                    removeFromwishlist(product_id);
//                    addTo_wishlist(product_id);

                }
            }
        });

    }
    private void addTo_wishlist(String product_id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.add_to_wishlistUrl, new Response.Listener<String>() {
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
//                        userSession.checkliked("1");
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        //JSONObject resultObj = response.getJSONObject("msg");
                        progressDialog.dismiss();

                    }
                    // editor.putBoolean(Constant.IS_LOGIN, true);

                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
//action=register,username,email,phone,password,profile
                //  params.put("username", ""+username);

                params.put("pharmacy_id",  Vendor_id);
                params.put("product_id",  product_id);
                params.put("user_id",  User_id);
                params.put("action", "add_to_wishlist" );
                Log.e("paramsvaluee", ">>" + params);
                return params;
            }};
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


        // addToRequestQueue(multipartRequest);
    }
    private void removeFromwishlist(String product_id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.dislike_wishlistUrl, new Response.Listener<String>() {
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
//                        userSession.checkliked("1");
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        //JSONObject resultObj = response.getJSONObject("msg");
                        progressDialog.dismiss();

                    }
                    // editor.putBoolean(Constant.IS_LOGIN, true);

                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
//action=register,username,email,phone,password,profile
                //  params.put("username", ""+username);

                params.put("pharmacy_id",  Vendor_id);
                params.put("product_id",  product_id);
                params.put("user_id",  User_id);
                params.put("action", "add_to_wishlist" );
                Log.e("paramsvaluee", ">>" + params);
                return params;
            }};
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


        // addToRequestQueue(multipartRequest);
    }


    @Override
    public int getItemCount() {
//        Log.d(TAG, "... "+productModelList.size());
        return productModelList.size();

    }
//    public Filter getFilter() {
//        Log.d("filteradapter",productFilter.toString());
//        return productFilter;
//    }
//    public Filter getFilter() {
//        if (valueFilter == null) {
//            valueFilter = new ValueFilter();
//        }
//        return valueFilter;
//    }



//    private Filter productFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<Products> filteredProductList = new ArrayList<>();
//            if (constraint == null || constraint.length() == 0) {
//
//                filteredProductList.addAll(productModelList);
//            } else {
//                Log.i("adapter", "performFiltering " + constraint.toString());
//
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (Products item : productModelList) {
//                    if (item.getProductName().toLowerCase().contains(filterPattern)) {
//                        filteredProductList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredProductList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            productModelList.clear();
//            productModelList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//        public CharSequence convertResultToString(Object resultValue) {
//            return ((Products) resultValue).getProductName();
//        }
//    };



//    private class ValueFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//            List<Products> filteredProductList1 = new ArrayList<>();
//            if (constraint != null && constraint.length() > 0) {
//
//                for (int i = 0; i < filteredProductList1.size(); i++) {
//                    if ((filteredProductList1.get(i).getProductName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
//                        filterList.add(filteredProductList1.get(i));
//                    }
//                }
//                results.count = filterList.size();
//                results.values = filteredProductList1;
//            } else {
//                results.count = filterList.size();
//                results.values = filteredProductList1;
//            }
//            return results;
//
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint,
//                                      FilterResults results) {
//            productModelList = (List<Products>) results.values;
//            notifyDataSetChanged();
//        }
//    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_unselected,category_image,img_selected;
        TextView txt_product_name,txt_rate,txt_offer_rate,txt_add_to_cart;

        public MyViewHolder(View itemView) {
            super(itemView);
            img_selected = itemView.findViewById(R.id.img_selected);
            img_unselected = itemView.findViewById(R.id.img_unselected);
            category_image = itemView.findViewById(R.id.category_image);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_add_to_cart = itemView.findViewById(R.id.txt_add_to_cart);
            txt_rate = itemView.findViewById(R.id.txt_rate);
           // txt_offer_rate = itemView.findViewById(R.id.txt_offer_rate);
        }
    }


    public void filter(final String text) {


        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterList.addAll(productModelList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (Products item : productModelList) {

                        try {
                            if (item.getProductName().toLowerCase().contains(text.toLowerCase())) {
                                // Adding Matched items
                                boolean istrue = item.getProductName().toLowerCase().contains(text.toLowerCase());
                                Log.d("TAG", "runistruegetName: "+item.getProductName());
                                Log.d("TAG", "runistruetext: "+text);
                                filterList.add(item);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }



    public void updateList(List<Products> list2){
        productModelList = list2;

        Log.v("DIKSHA","LISTTTT"+productModelList.size());
        notifyDataSetChanged();
    }
}
