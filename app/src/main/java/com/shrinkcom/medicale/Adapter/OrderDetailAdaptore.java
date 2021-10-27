package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdaptore extends RecyclerView.Adapter<OrderDetailAdaptore.MyviewHolder> {

    Context context;
    private List<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();

    public OrderDetailAdaptore(Context context, ArrayList<CategoryModel> categoryModelArrayList) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
    }



    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail, parent, false);

        return new OrderDetailAdaptore.MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (null != categoryModelArrayList ? categoryModelArrayList.size() : 0);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
