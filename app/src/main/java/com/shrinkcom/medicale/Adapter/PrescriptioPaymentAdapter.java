package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.prescriptionpayamentmodel;

import java.util.List;

public class PrescriptioPaymentAdapter extends RecyclerView.Adapter<PrescriptioPaymentAdapter.viewholder> {
    Context context;
    List<prescriptionpayamentmodel> prescriptionpayamentmodels;

    public PrescriptioPaymentAdapter(Context context, List<prescriptionpayamentmodel> prescriptionpayamentmodels) {
        this.context = context;
        this.prescriptionpayamentmodels = prescriptionpayamentmodels;
    }

    @NonNull
    @Override
    public PrescriptioPaymentAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription, parent, false);

        return new PrescriptioPaymentAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptioPaymentAdapter.viewholder holder, int position) {
        prescriptionpayamentmodel datum = prescriptionpayamentmodels.get(position);
        holder.tv_discount_no.setText("OMR"+String.valueOf(datum.getPrice_discounted()));
        holder.tv_product_name.setText(datum.getMedicine_name());
        holder.tv_quantity_no.setText(datum.getProduct_count());
        double total = Integer.parseInt(datum.getProduct_count())*Double.parseDouble(datum.getMedicine_price());
        holder.tv_subtotal_no.setText("OMR"+String.valueOf(datum.getTotal_price()));
        holder.tv_unit_price.setText(datum.getMedicine_price());
        holder.tv_total_no .setText("OMR"+String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return prescriptionpayamentmodels.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tv_product_name,tv_unit_price,tv_quantity_no,tv_subtotal_no,tv_discount_no,tv_total_no;

        public viewholder(@NonNull View itemView) {
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
