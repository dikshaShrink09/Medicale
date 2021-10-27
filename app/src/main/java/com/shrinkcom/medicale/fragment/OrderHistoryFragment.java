package com.shrinkcom.medicale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shrinkcom.medicale.Adapter.CategoryAdapter;
import com.shrinkcom.medicale.Adapter.OrderHistoryAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.CategoryModel;

import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment {
View  view;
RecyclerView recycler_order_history;
    private OrderHistoryAdapter orderHistoryAdapter;
    private ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_order_history, container, false);

        recycler_order_history = view.findViewById(R.id.recycler_order_history);


        orderHistoryAdapter = new OrderHistoryAdapter(getContext(), categoryModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_order_history.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_order_history.setItemAnimator(new DefaultItemAnimator());
        recycler_order_history.setAdapter(orderHistoryAdapter);
        prepareCourse();


   return view; }


    private void prepareCourse() {
        CategoryModel categoryModel = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel1 = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel2 = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel3 = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel4 = new CategoryModel("Mad Max: Fury Road","dws");
        CategoryModel categoryModel5 = new CategoryModel("Mad Max: Fury Road","dws");


        categoryModelArrayList.add(categoryModel);
        categoryModelArrayList.add(categoryModel1);
        categoryModelArrayList.add(categoryModel2);
        categoryModelArrayList.add(categoryModel3);
        categoryModelArrayList.add(categoryModel4);
        categoryModelArrayList.add(categoryModel5);
    }
}