package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.ShippingActivity;
import com.shrinkcom.medicale.model.AddressListModel;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.Viewholder> {
    List<AddressListModel> addressListModels;
    Context context;

    public AddressListAdapter(List<AddressListModel> addressListModels, Context context) {
        this.addressListModels = addressListModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addreslist_custom, parent, false);

        return new AddressListAdapter.Viewholder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.Viewholder holder, int position) {

        AddressListModel addressListModel =addressListModels.get(position);
        String street = addressListModel.getStreet();
        String city = addressListModel.getCity();
        String code = addressListModel.getCode();
        String state = addressListModel.getState();
        String name = addressListModel.getName();
        String email = addressListModel.getEmail();
        String concanate = street+ "  "+city +"  "+state;
        holder.tv_address.setText(street);
        holder.tv_code.setText(code);
        holder.tv_street.setText(state);
        holder.tv_city.setText(city);
        holder.tv_name.setText(name);
        holder.tv_email.setText(email);

        holder.holders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShippingActivity.class);
                intent.putExtra("name",addressListModel.getName());
                intent.putExtra("email",addressListModel.getEmail());
                intent.putExtra("street",addressListModel.getStreet());
                intent.putExtra("city",addressListModel.getCity());
                intent.putExtra("state",addressListModel.getState());
                intent.putExtra("phone",addressListModel.getPhone());
                intent.putExtra("zipcode",addressListModel.getCode());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return addressListModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_address,tv_code,tv_street,tv_city,tv_name,tv_email;
        LinearLayout holders;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tv_address = itemView.findViewById(R.id.tv_address);
            tv_code = itemView.findViewById(R.id.tv_code);
            tv_street = itemView.findViewById(R.id.tv_street);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            holders = itemView.findViewById(R.id.holders);
        }
    }
}
