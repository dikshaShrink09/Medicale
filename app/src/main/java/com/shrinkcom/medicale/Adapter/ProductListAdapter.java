package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.MakePayamentActivity;
import com.shrinkcom.medicale.activity.ProductInformationActivity;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.ShowMyorder;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter  extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    Context context;
    private List<ShowMyorder> listModels = new ArrayList<ShowMyorder>();
    ButtonClick buttonClick;


    public ProductListAdapter(Context context, List<ShowMyorder> listModels, ButtonClick buttonClick) {
        this.context = context;
        this.listModels = listModels;
        this.buttonClick = buttonClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_list, parent, false);

        return new ProductListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ShowMyorder showMyorder = listModels.get(position);
        holder.txt_order_id.setText(showMyorder.getOrder_no());
        holder.txt_order_price.setText("OMR"+showMyorder.getTotal_amount());
        holder.txt_date.setText(showMyorder.getCreated_at());


        if (showMyorder.getOrder_status().equals("0")){
            holder.txt_order_type.setText(R.string.pending);
        } if (showMyorder.getOrder_status().equals("1")){
            holder.txt_order_type.setText(R.string.confirm1);
            holder.txt_make_payment11.setVisibility(View.VISIBLE);
        } if (showMyorder.getOrder_status().equals("2")){
            holder.txt_order_type.setText(R.string.readytoshift);
        } if (showMyorder.getOrder_status().equals("3")){
            holder.txt_order_type.setText(R.string.cancelledbyyou);
            holder.tv_cancelorder.setVisibility(View.GONE);
            holder.txt_make_payment11.setVisibility(View.GONE);
        }if (showMyorder.getOrder_status().equals("4")){
            holder.txt_order_type.setText(R.string.rejectedbypharmacy);
            holder.tv_cancelorder.setVisibility(View.GONE);
            holder.txt_make_payment11.setVisibility(View.GONE);
        }if (showMyorder.getOrder_status().equals("5")){
            holder.txt_order_type.setText(R.string.readytodispatch);
        }if (showMyorder.getOrder_status().equals("6")){
            holder.txt_order_type.setText(R.string.dispatched);
        }if (showMyorder.getOrder_status().equals("7")){
            holder.txt_order_type.setText(R.string.intransit);
        }

        if (showMyorder.getStatus().equals("0")){
            holder.txt_paymentstatus.setText(R.string.unpaid);
        }
        if (showMyorder.getStatus().equals("1")){
            holder.txt_paymentstatus.setText(R.string.paid);
            holder.txt_make_payment11.setVisibility(View.GONE);

        }

        holder.tv_cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick.onClick(showMyorder.getOrder_id());
            }


//            public void onClick(View v) {
//                deletecart(myPresciptionmodel.getOrder_id());
//            }
        });



        holder.txt_make_payment11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MakePayamentActivity.class);
                intent.putExtra("order_id",showMyorder.getOrder_id());
                context.startActivity(intent);

            }
        });
        holder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductInformationActivity.class);
                intent.putExtra("order_id",showMyorder.getOrder_id());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != listModels ? listModels.size() : 0);

    }
 public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView img_selection,img_info;
    CardView card_one;
    TextView txt_product_name,txt_rate,txt_offer_rate;
    TextView txt_make_payment11,tv_cancelorder,txt_order_id,txt_order_price,txt_order_type,txt_paymentstatus,txt_date;
    public MyViewHolder(View itemView) {
        super(itemView);
        img_selection = itemView.findViewById(R.id.img_unselected);
        img_info = itemView.findViewById(R.id.img_info);
        txt_product_name = itemView.findViewById(R.id.txt_product_name);
        txt_rate = itemView.findViewById(R.id.txt_rate);
        txt_make_payment11 = itemView.findViewById(R.id.txt_make_payment11);
        tv_cancelorder = itemView.findViewById(R.id.tv_cancelorder);
        txt_order_id = itemView.findViewById(R.id.txt_order_id);
        txt_order_price = itemView.findViewById(R.id.txt_order_price);
        txt_order_type = itemView.findViewById(R.id.txt_order_type);
        txt_paymentstatus = itemView.findViewById(R.id.txt_paymentstatus);
        txt_date = itemView.findViewById(R.id.txt_date);
        card_one = itemView.findViewById(R.id.card_one);

    }
}

}

