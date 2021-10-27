package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.PharmacyFragment;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.GeneralCategoryModel;
import com.shrinkcom.medicale.model.SubcategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GeneralCategoryAdapter extends RecyclerView.Adapter<GeneralCategoryAdapter.Viewholder> {
    Context context;
    private List<GeneralCategoryModel> categoryModelArrayList = new ArrayList<GeneralCategoryModel>();
    ButtonClick buttonClick;

    public GeneralCategoryAdapter(Context context, List<GeneralCategoryModel> categoryModelArrayList, ButtonClick buttonClick) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
        this.buttonClick = buttonClick;
    }

    @NonNull
    @Override
    public GeneralCategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.general_category, parent, false);

        return new GeneralCategoryAdapter.Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralCategoryAdapter.Viewholder holder, int position) {
        GeneralCategoryModel categoryModel = categoryModelArrayList.get(position);
        holder.txt_category_name.setText(categoryModel.getSub_cat_name());

        Picasso.with(context)
                .load("https://shrinkcom.com/pharma/" + categoryModel.getSub_cat_photo())
                .into(holder.category_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PharmacyFragment.class);
                intent.putExtra("sub_category_id",categoryModel.getSub_cat_id());
                context.startActivity(intent);

//             buttonClick.onClick();
            /* Intent intent = new Intent(context, PharmacyFragment.class);
             context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != categoryModelArrayList ? categoryModelArrayList.size() : 0);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        LinearLayout ll1;
        TextView txt_category_name;
        ImageView category_image;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ll1 = itemView.findViewById(R.id.ll1);
            txt_category_name = itemView.findViewById(R.id.txt_category_name);
            category_image = itemView.findViewById(R.id.category_image);
        }
    }
}
