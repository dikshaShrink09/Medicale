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
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.SubcategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>  {
    Context context;
private List<SubcategoryModel> categoryModelArrayList = new ArrayList<SubcategoryModel>();
ButtonClick buttonClick;

    public CategoryAdapter(Context context, ArrayList<SubcategoryModel> categoryModelArrayList,ButtonClick buttonClick) {
        this.context = context;
        this.buttonClick = buttonClick;

        this.categoryModelArrayList = categoryModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SubcategoryModel categoryModel = categoryModelArrayList.get(position);
        holder.txt_category_name.setText(categoryModel.getSub_cat_name());

        Picasso.with(context)
                .load("https://shrinkcom.com/pharma/" + categoryModel.getSub_cat_photo())
                .into(holder.category_image);

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
               Intent intent = new Intent(context,PharmacyFragment.class);
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
