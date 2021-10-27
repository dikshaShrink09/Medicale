package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.CategoryPage;
import com.shrinkcom.medicale.activity.PharmacyFragment;
import com.shrinkcom.medicale.activity.ProductActivity;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.PharmacyListModel;
import com.shrinkcom.medicale.storage.UserSession;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.MyviewHolder>{
    Context context;
    private List<PharmacyListModel> categoryModelArrayList = new ArrayList<PharmacyListModel>();
    ButtonClick buttonClick;
    String sub_cat_id;
    UserSession userSession;
    public PharmacyAdapter(Context context, ArrayList<PharmacyListModel> categoryModelArrayList,ButtonClick buttonClick) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
        this.buttonClick = buttonClick;
    }



    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pharmacy_activity, parent, false);

        return new PharmacyAdapter.MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        sub_cat_id = PharmacyFragment.sub_category_id;

        PharmacyListModel pharmacyListModel = categoryModelArrayList.get(position);
        holder.txt_pharmacy_name.setText(pharmacyListModel.getPharmacy_name());
        holder.txt_pharmacy_address.setText(pharmacyListModel.getLocality());
        Picasso.with(context)
                .load("https://shrinkcom.com/pharma/" + pharmacyListModel.getLogo())
                .into(holder.img_video);



holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("vendor_id",pharmacyListModel.getVendor_id());
        intent.putExtra("sub_cat_id",sub_cat_id);
        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return (null != categoryModelArrayList ? categoryModelArrayList.size() : 0);
    }



    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView txt_pharmacy_name,txt_pharmacy_address;
        ImageView img_video;
        public MyviewHolder(@NonNull View itemView) {

            super(itemView);

            txt_pharmacy_name = itemView.findViewById(R.id.txt_pharmacy_name);
            txt_pharmacy_address = itemView.findViewById(R.id.txt_pharmacy_address);
            img_video = itemView.findViewById(R.id.img_video);
        }
    }

}
