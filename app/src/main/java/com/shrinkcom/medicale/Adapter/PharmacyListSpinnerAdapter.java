package com.shrinkcom.medicale.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.PharmacySpinnerModel;

import java.util.ArrayList;
import java.util.List;

public class PharmacyListSpinnerAdapter extends BaseAdapter {

    Context contexta;

    LayoutInflater inflter;
List<PharmacySpinnerModel> pharmacySpinnerModelList = new ArrayList<>();

    public PharmacyListSpinnerAdapter(Context context,  List<PharmacySpinnerModel> pharmacySpinnerModelList) {
        Log.d("TAG", "PharmacyListSpinnerAdapter: "+context);
        this.contexta = context;
        this.pharmacySpinnerModelList = pharmacySpinnerModelList;
     //   inflter = (LayoutInflater.from(contexta));

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

      //  view = inflter.inflate(R.layout.item_spinner_layout, parent,false);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) contexta.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_layout, parent, false);

        }


        return view;
    }
}
