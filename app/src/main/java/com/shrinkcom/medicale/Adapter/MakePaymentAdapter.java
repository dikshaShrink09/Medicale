package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.summarytotalmodel;

import java.util.List;

public class MakePaymentAdapter extends RecyclerView.Adapter<MakePaymentAdapter.MyViewHolder> {
    Context context;
    List<summarytotalmodel> data ;

    public MakePaymentAdapter(Context context, List<summarytotalmodel> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_information, parent, false);

        return new MakePaymentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        summarytotalmodel datum = data.get(position);
        holder.tv_discount_no.setText("OMR"+String.valueOf(datum.getDiscount()));
        holder.tv_product_name.setText(datum.getProduct_name());
        holder.tv_quantity_no.setText(datum.getProduct_quantity());
        holder.tv_subtotal_no.setText("OMR"+String.valueOf(datum.getSubtotal()));
        holder.tv_unit_price.setText(datum.getProduct_price());
        holder.tv_total_no .setText("OMR"+String.valueOf(datum.getTotal()));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_name,tv_unit_price,tv_quantity_no,tv_subtotal_no,tv_discount_no,tv_total_no;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_discount_no = itemView.findViewById(R.id.tv_discount_no);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_unit_price = itemView.findViewById(R.id.tv_unit_price);
            tv_quantity_no = itemView.findViewById(R.id.tv_quantity_no);
            tv_subtotal_no = itemView.findViewById(R.id.tv_subtotal_no);
            tv_total_no = itemView.findViewById(R.id.tv_total_no);
        }
    }
}
