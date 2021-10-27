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
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.activity.ProductActivity;
import com.shrinkcom.medicale.activity.ProductOneActivity;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.MyCartModel;
import com.shrinkcom.medicale.model.WishListModel;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {
    Context context;
    private List<WishListModel> wishListModels = new ArrayList<>();
    UserSession userSession;
    String user_id;


    public WishListAdapter(Context context, List<WishListModel> wishListModels) {
        this.context = context;
        this.wishListModels = wishListModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wish_list, parent, false);
        return new WishListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WishListModel wishListModel =wishListModels.get(position);
        holder.tv_product_name.setText(wishListModel.getProduct_name());
        holder.tv_total_price.setText("$"+" "+wishListModel.getPrice_discounted());

        userSession = new UserSession(context);
        user_id = userSession.read(ConstantApi.pre_user_id,"");


        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletecart(wishListModel.getWishlist_id(),holder);
            }
        });

        String v_id = ProductActivity.vendor_id;

        Picasso.with(context)
                .load("https://shrinkcom.com/pharma/" + wishListModel.getProduct_image())
                .into(holder.img_product_image);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ProductOneActivity.class);
            intent.putExtra("product_id",wishListModel.getProduct_id());
            intent.putExtra("vendor_id",wishListModel.getPharmacy_id());
            context.startActivity(intent);
        }
    });
    }
    private void deletecart(String wishid, WishListAdapter.MyViewHolder holder) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.delete_wishlistUrl, new Response.Listener<String>() {
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
                        wishListModels.remove(newPosition);
                        notifyItemRemoved(newPosition);
                        notifyItemRangeChanged(newPosition, wishListModels.size());
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
                params.put("wishlist_id",  wishid);
                params.put("action", "delete_wishlist" );
                Log.e("paramsvaluee", ">>" + params);
                return params;
            }};
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


        // addToRequestQueue(multipartRequest);
    }


    @Override
    public int getItemCount() {
        return wishListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_image,iv_delete;
        TextView tv_product_name,tv_total_price;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_total_price = itemView.findViewById(R.id.tv_total_price);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            img_product_image = itemView.findViewById(R.id.img_product_image);
            iv_delete = itemView.findViewById(R.id.iv_delete);

        }
    }
}
