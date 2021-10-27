package com.shrinkcom.medicale.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.shrinkcom.medicale.activity.MakePayamentActivity;
import com.shrinkcom.medicale.activity.PrecscriptionPaymentActivity;
import com.shrinkcom.medicale.activity.ShowPrescriptionDetailsActivty;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.MyPresciptionmodel;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPrescriptionAdapter extends RecyclerView.Adapter<MyPrescriptionAdapter.MyViewHolder> {
    Context context;
    private List<MyPresciptionmodel> listModels = new ArrayList<>();
    UserSession userSession;
    String user_id;
    ButtonClick buttonClick;


    public MyPrescriptionAdapter(Context context, List<MyPresciptionmodel> listModels,ButtonClick buttonClick) {
        this.context = context;
        this.listModels = listModels;
        this.buttonClick = buttonClick;
    }

    @NonNull
    @Override
    public MyPrescriptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_prescriptionitem, parent, false);

        return new MyPrescriptionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPrescriptionAdapter.MyViewHolder holder, int position) {

        userSession = new UserSession(context);
        user_id = userSession.read(ConstantApi.pre_user_id,"");
        MyPresciptionmodel myPresciptionmodel = listModels.get(position);
        holder.txt_order_id.setText(myPresciptionmodel.getOrder_no());
        holder.txt_order_price.setText("OMR"+myPresciptionmodel.getTotal_amount());
        holder.txt_date.setText(myPresciptionmodel.getCreated_at());

        Picasso.with(context)
                .load("https://shrinkcom.com/pharma/file/prescriptions/" + myPresciptionmodel.getFile())
                .into(holder.img_video);


        if (myPresciptionmodel.getOrder_status().equals("0")){
            holder.txt_order_type.setText(R.string.pending);
        } if (myPresciptionmodel.getOrder_status().equals("1")){
            holder.txt_order_type.setText(R.string.confirm1);
            holder.txt_make_payment11.setVisibility(View.VISIBLE);
        } if (myPresciptionmodel.getOrder_status().equals("2")){
            holder.txt_order_type.setText(R.string.readytoshift);
        } if (myPresciptionmodel.getOrder_status().equals("3")){
            holder.txt_order_type.setText(R.string.cancelledbyyou);
            holder.tv_cancelorder.setVisibility(View.GONE);
            holder.txt_make_payment11.setVisibility(View.GONE);
        }if (myPresciptionmodel.getOrder_status().equals("4")){
            holder.txt_order_type.setText(R.string.rejectedbypharmacy);
            holder.tv_cancelorder.setVisibility(View.GONE);
            holder.txt_make_payment11.setVisibility(View.GONE);
        }if (myPresciptionmodel.getOrder_status().equals("5")){
            holder.txt_order_type.setText(R.string.readytodispatch);
        }if (myPresciptionmodel.getOrder_status().equals("6")){
            holder.txt_order_type.setText(R.string.dispatched);
        }if (myPresciptionmodel.getOrder_status().equals("7")){
            holder.txt_order_type.setText(R.string.intransit);
        }

        if (myPresciptionmodel.getStatus().equals("0")){
            holder.txt_paymentstatus.setText(R.string.unpaid);
        }
        if (myPresciptionmodel.getStatus().equals("1")){
            holder.txt_paymentstatus.setText(R.string.paid);
            holder.txt_make_payment11.setVisibility(View.GONE);

        }


        holder.txt_make_payment11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrecscriptionPaymentActivity.class);
                intent.putExtra("order_id",myPresciptionmodel.getOrder_id());
                context.startActivity(intent);

            }
        });

//        holder.tv_cancelorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert();
//            }
//        });
        holder.tv_cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick.onClick(myPresciptionmodel.getOrder_id());
            }


//            public void onClick(View v) {
//                deletecart(myPresciptionmodel.getOrder_id());
//            }
        });
        holder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPrescriptionDetailsActivty.class);
                intent.putExtra("order_id",myPresciptionmodel.getOrder_id());
                context.startActivity(intent);
            }
        });
    }
    private void deletecart(String order_id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
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
                    if(status.equals("1")) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
//                        Intent i = new Intent(this, SecondActivity.class);
//                        startActivityForResult(i, 1);
//                        myCartModels.remove(position);
//                        int newPosition = holder.getAdapterPosition();
//                        myCartModels.remove(newPosition);
//                        notifyItemRemoved(newPosition);
//                        notifyItemRangeChanged(newPosition, myCartModels.size());
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
                params.put("order_id",  order_id);
                params.put("action", "cancel_pro_order" );
                Log.e("paramsvaluee", ">>" + params);
                return params;
            }};
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


        // addToRequestQueue(multipartRequest);
    }



    public  void alert(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                context);

// Setting Dialog Title
        alertDialog2.setTitle("Confirm Delete...");

// Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want delete this item ?");

// Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.ic_baseline_delete_24);

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(context,
                                "You clicked on YES", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
// Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(context,
                                "You clicked on NO", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

// Showing Alert Dialog
        alertDialog2.show();
    }

    @Override
    public int getItemCount() {

        return (null != listModels ? listModels.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_selection,img_info,img_video;
        TextView txt_product_name,txt_rate,txt_offer_rate,txt_paymentstatus,txt_make_payment11,tv_cancelorder,txt_order_id,txt_order_price,txt_date,txt_order_type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_selection = itemView.findViewById(R.id.img_unselected);
//            img_info = itemView.findViewById(R.id.img_info);
            txt_make_payment11 = itemView.findViewById(R.id.txt_make_payment11);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            img_info = itemView.findViewById(R.id.img_info);
            txt_rate = itemView.findViewById(R.id.txt_rate);
            tv_cancelorder = itemView.findViewById(R.id.tv_cancelorder);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_order_price = itemView.findViewById(R.id.txt_order_price);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_order_type = itemView.findViewById(R.id.txt_order_type);
            img_video = itemView.findViewById(R.id.img_video);
            tv_cancelorder = itemView.findViewById(R.id.tv_cancelorder);
            txt_paymentstatus = itemView.findViewById(R.id.txt_paymentstatus);

        }
    }
}
