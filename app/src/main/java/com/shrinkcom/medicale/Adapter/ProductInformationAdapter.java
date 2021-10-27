package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.MyorderDetalsmodel;
import com.shrinkcom.medicale.model.summarytotalmodel;

import java.util.List;

public class ProductInformationAdapter extends RecyclerView.Adapter<ProductInformationAdapter.Viewholder> {

    Context context;
    List<MyorderDetalsmodel> myorderDetalsmodels ;

    public ProductInformationAdapter(Context context, List<MyorderDetalsmodel> myorderDetalsmodels) {
        this.context = context;
        this.myorderDetalsmodels = myorderDetalsmodels;
    }

    @NonNull
    @Override
    public ProductInformationAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_info, parent, false);

        return new ProductInformationAdapter.Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductInformationAdapter.Viewholder holder, int position) {
        MyorderDetalsmodel datum = myorderDetalsmodels.get(position);
        holder.tv_discount_no.setText("OMR"+String.valueOf(datum.getPrice_discounted()));
        holder.tv_product_name.setText(datum.getProduct_name());
        holder.tv_quantity_no.setText(datum.getProduct_quantity());
        double total = Integer.parseInt(datum.getProduct_count())*Integer.parseInt(datum.getPrice_discounted());
        holder.tv_subtotal_no.setText("OMR"+String.valueOf(total));
        holder.tv_unit_price.setText(datum.getProduct_price());
        holder.tv_total_no .setText("OMR"+String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return myorderDetalsmodels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_product_name,tv_unit_price,tv_quantity_no,tv_subtotal_no,tv_discount_no,tv_total_no;

        public Viewholder(@NonNull View itemView) {
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
