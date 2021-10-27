package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.DiseaseModel;

import java.util.ArrayList;
import java.util.List;

public class DiseaseCategoryAdapter extends RecyclerView.Adapter<DiseaseCategoryAdapter.MyViewHolder> {
List<DiseaseModel> diseaseModelList = new ArrayList<>();
Context context;
ButtonClick buttonClick;

    public DiseaseCategoryAdapter(List<DiseaseModel> diseaseModelList, Context context, ButtonClick buttonClick) {
        this.diseaseModelList = diseaseModelList;
        this.context = context;
        this.buttonClick = buttonClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new DiseaseCategoryAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                buttonClick.onClick("h");
            /* Intent intent = new Intent(context, PharmacyFragment.class);
             context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
       /* return (null != diseaseModelList ? diseaseModelList.size() : 0);*/
        return 7;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll1;
        TextView txt_category_name;
        ImageView category_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll1 = itemView.findViewById(R.id.ll1);
            txt_category_name = itemView.findViewById(R.id.txt_category_name);
            category_image = itemView.findViewById(R.id.category_image);
        }
    }
}
