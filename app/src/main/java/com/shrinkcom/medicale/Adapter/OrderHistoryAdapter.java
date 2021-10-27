package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.OrderDatailActivity;
import com.shrinkcom.medicale.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    Context context;
    private List<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();

    public OrderHistoryAdapter(Context context, ArrayList<CategoryModel> categoryModelArrayList) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_history, parent, false);

        return new OrderHistoryAdapter.MyViewHolder(itemView);      }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
   holder.itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Intent intent = new Intent(context, OrderDatailActivity.class);
           context.startActivity(intent);
       }
   });
    }

    @Override
    public int getItemCount() {
        return (null != categoryModelArrayList ? categoryModelArrayList.size() : 0);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
