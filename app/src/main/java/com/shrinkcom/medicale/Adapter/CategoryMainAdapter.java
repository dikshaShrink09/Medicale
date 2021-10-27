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
import com.shrinkcom.medicale.activity.ProductActivity;
import com.shrinkcom.medicale.model.ProductModel;
import com.shrinkcom.medicale.model.SubcategoryModel;
import com.shrinkcom.medicale.model.productmodel.Products;
import com.shrinkcom.medicale.model.productmodel.SubCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryMainAdapter extends RecyclerView.Adapter<CategoryMainAdapter.MyViewholder> {

    Context context;
    private List<SubCategory> categoryModelArrayList = new ArrayList<SubCategory>();
    private List<Products> productModelArrayList = new ArrayList<Products>();


    public CategoryMainAdapter(Context context, List<SubCategory> categoryModelArrayList, List<Products> productModelArrayList) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
        this.productModelArrayList = productModelArrayList;
    }

    @NonNull
    @Override
    public CategoryMainAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_category, parent, false);

        return new CategoryMainAdapter.MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryMainAdapter.MyViewholder holder, int position) {
        SubCategory subCategory = categoryModelArrayList.get(position);
        Products products = productModelArrayList.get(position);
        holder.txt_category_name.setText(subCategory.getSubCatName());

//        Picasso.with(context)
//                .load("https://shrinkcom.com/pharma/" + subCategory.getSubCatName())
//                .into(holder.category_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
//                intent.putExtra("sub_category_id",subCategory.ge());
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

    public class MyViewholder extends RecyclerView.ViewHolder {
        LinearLayout ll1;
        TextView txt_category_name;
        ImageView category_image;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            ll1 = itemView.findViewById(R.id.ll1);
            txt_category_name = itemView.findViewById(R.id.txt_category_name);
            category_image = itemView.findViewById(R.id.category_image);
        }
    }
}
