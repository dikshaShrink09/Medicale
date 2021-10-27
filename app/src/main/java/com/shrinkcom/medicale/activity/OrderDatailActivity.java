package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.shrinkcom.medicale.Adapter.OrderDetailAdaptore;
import com.shrinkcom.medicale.Adapter.OrderHistoryAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDatailActivity extends AppCompatActivity {
ImageView back;
RecyclerView recycler_product_name;
    private List<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    OrderDetailAdaptore orderDetailAdaptore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_datail);
        getSupportActionBar().hide();
        back = findViewById(R.id.back);
        recycler_product_name = findViewById(R.id.recycler_product_name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });




        orderDetailAdaptore = new OrderDetailAdaptore(OrderDatailActivity.this, (ArrayList<CategoryModel>) categoryModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderDatailActivity.this);
        recycler_product_name.setLayoutManager(new LinearLayoutManager(OrderDatailActivity.this, LinearLayoutManager.VERTICAL, false));
        recycler_product_name.setItemAnimator(new DefaultItemAnimator());
        recycler_product_name.setAdapter(orderDetailAdaptore);
        prepareCourse();

    }
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
        categoryModelArrayList.add(categoryModel5);
        categoryModelArrayList.add(categoryModel5);
        categoryModelArrayList.add(categoryModel5);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

}