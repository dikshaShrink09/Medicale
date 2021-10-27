package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.shrinkcom.medicale.Adapter.DiseaseCategoryAdapter;
import com.shrinkcom.medicale.Adapter.ProductAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.DiseaseModel;
import com.shrinkcom.medicale.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class DiseaseCategoryActivity extends AppCompatActivity {
RecyclerView recycler_disease_cat;
ImageView back;

    ImageView img_cross,img_search_bar;
    private ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<ProductModel>();

    LinearLayout ll_searching;


    Toolbar toolbar;
    List<DiseaseModel> diseaseModelList = new ArrayList<>();

    ButtonClick buttonClick;
    DiseaseCategoryAdapter  diseaseCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_category);

        getSupportActionBar().hide();
        initview();

        img_search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.GONE);
                ll_searching.setVisibility(View.VISIBLE);

            }
        });
        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_searching.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();

            }
        });
// TODO: 2/25/2021 integrate adapter for disease category

        diseaseCategoryAdapter = new DiseaseCategoryAdapter(diseaseModelList, this, new ButtonClick() {
            @Override
            public void onClick(String value) {
                   Intent intent = new Intent(DiseaseCategoryActivity.this, PharmacyFragment.class);
             startActivity(intent);
            }

            @Override
            public void onClickItem(String card_id, String quantity) {

            }
        });
        recycler_disease_cat.setLayoutManager(new GridLayoutManager(this,3 ));
        recycler_disease_cat.setItemAnimator(new DefaultItemAnimator());
        recycler_disease_cat.setAdapter(diseaseCategoryAdapter);

    }


    void initview()
    {
        recycler_disease_cat = findViewById(R.id.recycler_disease_cat);

        ll_searching = findViewById(R.id.ll_searching);
        img_search_bar = findViewById(R.id.img_search_bar);
        img_cross = findViewById(R.id.img_cross);
        ll_searching = findViewById(R.id.ll_searching);
        img_cross = findViewById(R.id.img_cross);
        back = findViewById(R.id.back);

        toolbar = findViewById(R.id.toolbar);
    }
}