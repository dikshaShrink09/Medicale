package com.shrinkcom.medicale.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.shrinkcom.medicale.Adapter.CategoryAdapter;
import com.shrinkcom.medicale.Adapter.PharmacyListSpinnerAdapter;
import com.shrinkcom.medicale.Adapter.ProductAdapter;
import com.shrinkcom.medicale.MainActivity;
import com.shrinkcom.medicale.R;

import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.PharmacySpinnerModel;
import com.shrinkcom.medicale.model.ProductModel;
import com.shrinkcom.medicale.model.SubcategoryModel;
import com.shrinkcom.medicale.model.productmodel.Products;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.medicale.R.color.blue;


public class ProductActivity extends AppCompatActivity {
    View view;
    List<PharmacySpinnerModel> pharmacySpinnerModelList = new ArrayList<>();
    PharmacyListSpinnerAdapter pharmacyListSpinnerAdapter;
    RecyclerView recycler_category_dashboard;
    RecyclerView recycler_product;
    LinearLayout ll2, ll1;
    ImageView back, img_cross, img_search_bar;
    private ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<ProductModel>();
    private List<Products> productModels = new ArrayList<Products>();
    TextView tv_tittle;
    LinearLayout ll_searching;
    private ProductAdapter productAdapter;
    Context mContext;
    Toolbar toolbar;
    Spinner spinner_select_pharmacy;
    private static SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    public static String vendor_id;
    public static String sub_cat_id;
    EditText txt_search;
    EditText et;
    UserSession userSession;
    public static String User_id;
    Spinner spinner_select_sorting;
    String[] qn = {"Default", "Latest",
            "Low to hight", "hight to low"};
    LinearLayout layy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product);
        getSupportActionBar().hide();
        layy =  findViewById(R.id.layy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Products");
        toolbar.setBackgroundColor(getResources().getColor(blue));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Page");
        }
        toolbar.inflateMenu(R.menu.twomenu);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.search) {
                    toolbar.setVisibility(View.GONE);
                    ll_searching.setVisibility(View.VISIBLE);
//                    Toast.makeText(getApplicationContext(), "toolclick", Toast.LENGTH_SHORT).show();// do something
                } else if (item.getItemId() == R.id.action_menu1) {
                    Loadingdatasortdata("1");
                    Toast.makeText(getApplicationContext(), "menu1", Toast.LENGTH_SHORT).show();
                }else  if(item.getItemId() == R.id.action_menu2){
                    Loadingdatasortdata("2");
                    Toast.makeText(getApplicationContext(), "menu2", Toast.LENGTH_SHORT).show();

                }else  if (item.getItemId() == R.id.action_menu3){
                    Loadingdatasortdata("3");
                    Toast.makeText(getApplicationContext(), "menu3", Toast.LENGTH_SHORT).show();

                }else  if (item.getItemId() == R.id.action_menu4){
                    Loadingdatasortdata("4");
                    Toast.makeText(getApplicationContext(), "menu4", Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });
        toolbar.setNavigationOnClickListener(view -> onBackPressed());


//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll_searching = findViewById(R.id.ll_searching);
//        img_search_bar = findViewById(R.id.img_search_bar);
        img_cross = findViewById(R.id.img_cross);
        ll_searching = findViewById(R.id.ll_searching);
        img_cross = findViewById(R.id.img_cross);
        txt_search = findViewById(R.id.txt_search);

        toolbar = findViewById(R.id.toolbar);
        userSession = new UserSession(getApplicationContext());

        vendor_id = getIntent().getStringExtra("vendor_id");
        sub_cat_id = getIntent().getStringExtra("sub_cat_id");
        User_id = userSession.read(ConstantApi.pre_user_id, "");

        initview();
        txt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
                Log.v("DIKSHA", "LISTTTT!!!!!!!!!!!!!!!" + editable.toString());

            }

        });


//        txt_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                productAdapter.filter(newText);
//                Log.d("filteractivty",newText);
////                productAdapter.filter(newText);
////                    productAdapter.getFilter().filter(newText);
//                return true;
//            }
//        });
        Loadingdata();


//        prepareproduct();


//        img_search_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toolbar.setVisibility(View.GONE);
//                ll_searching.setVisibility(View.VISIBLE);
//
//            }
//        });
        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_searching.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);


            }
        });
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//                finish();
//
//            }
//        });



/*
        recycler_product.addOnScrollListener(new MyScrollListener(mgr) {

            @Override
            public void scrollTheShit(int dy, int widthOfLastChild) {

                recycler_category_dashboard.setY(ll2.getY() + dy);
            }
        });*/
/*
        //todo open bottomsheet for location
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheerDialog = new BottomSheetDialog(getActivity());
                View parentView = getLayoutInflater().inflate(R.layout.item_bottom_sheet_location_search, null);
                bottomSheerDialog.setContentView(parentView);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
                bottomSheetBehavior.setPeekHeight(600);
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                bottomSheerDialog.show();
                parentView.findViewById(R.id.img_dismis).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                                  *//*  Intent intent = new Intent(mContext, FinishRideActivity.class);
                                    startActivity(intent);*//*
                        bottomSheerDialog.cancel();
                    }
                });


            }
        });

        getProvince( );*/

    }


    void filter(String text) {
        List<Products> temp = new ArrayList();
        Log.e("DADAAA", ">>>>" + productModels.size());
        for (Products d : productModels) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getProductName().toLowerCase().contains(text.toLowerCase()) || d.getProductName().toUpperCase().contains(text.toUpperCase())) {
                temp.add(d);

                Log.v("DIKSHA", "LISTTTT!!  temp  >>" + temp.size());
//                Log.v("DIKSHA","LISTTTT!!"+lessonModelList);
            }
        }
        //update recyclerview
        productAdapter.updateList(temp);
    }


    void initview() {
        ll2 = findViewById(R.id.ll2);
        ll1 = findViewById(R.id.ll1);
        spinner_select_pharmacy = findViewById(R.id.spinner_select_pharmacy);
        recycler_category_dashboard = findViewById(R.id.recycler_category_dashboard);
        recycler_product = findViewById(R.id.recycler_product);
        tv_tittle = findViewById(R.id.tv_tittle);





    }


    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.products_listUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    Log.d("AnkushResponse", "onResponse: " + response);

                    Gson gsonObj = new Gson();
                    com.shrinkcom.medicale.model.productmodel.ProductModel productModel = gsonObj.fromJson(response.toString(), com.shrinkcom.medicale.model.productmodel.ProductModel.class);
                    try {
                        productModels = productModel.getData().getProductsList();
                        Log.d("arraysize", String.valueOf(productModels.size()));
                        String status = productModel.getStatus();
                        if (status.equals("1")) {
                            if (productModels.size() > 0) {
                                recycler_product.setLayoutManager(new GridLayoutManager(ProductActivity.this, 2));
                                recycler_product.setItemAnimator(new DefaultItemAnimator());
                                productAdapter = new ProductAdapter(ProductActivity.this, productModels, new ButtonClick() {
                                    @Override
                                    public void onClick(String product_id) {
                                        Intent intent = new Intent(getApplicationContext(), ProductOneActivity.class);
                                        intent.putExtra("vendor_id", vendor_id);
                                        intent.putExtra("product_id", product_id);
                                        startActivity(intent);
//                                    startActivity(intent);
//                                    startActivity(new Intent(ProductActivity.this, ProductOneActivity.class));

                                    }

                                    @Override
                                    public void onClickItem(String card_id, String quantity) {

                                    }
                                });
                                recycler_product.setAdapter(productAdapter);
                            } else {
                                layy.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Emptylist", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            layy.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    error.printStackTrace();
                    Log.e("ERRORRRRR", "---->" + error);

                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
//action=register,username,email,phone,password,profile
                    params.put("vendor_id", "" + vendor_id);
                    params.put("sub_category_id", "" + sub_cat_id);
                    params.put("user_id", "" + User_id);

                    params.put("action", "products_list");
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
        }else {

            final Dialog dialog = new Dialog(getApplicationContext());
            dialog.setContentView(R.layout.custom_dialog);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(dialog.getWindow().getAttributes());
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.CENTER;
            dialog.getWindow().setAttributes(params);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
            TextView retry = (TextView) dialog.findViewById(R.id.retry);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }
    }



//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.search:
//                toolbar.setVisibility(View.GONE);
//                ll_searching.setVisibility(View.VISIBLE);
//                        getSupportActionBar().hide();
//
//
//                break;
//            case R.id.action_menu1:
////                int id = item.getItemId();
////                switch (id){
////                    case R.id.action_menu1:
//                        Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
////                        return true;
////                    case R.id.action_menu1:
////                        Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
////                        return true;
//////                    case R.id.item3:
//////                        Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
//////
//////                    case R.id.item4:
//////                        Toast.makeText(getApplicationContext(),"Item 4 Selected",Toast.LENGTH_LONG).show();
////                        return true;
////                    default:
////                        return super.onOptionsItemSelected(item);
////                }
//        }
//        return super.onOptionsItemSelected(item);
//    }


//    @Override
//    public void onBackPressed() {
//        if (!searchView.isIconified()) {
//            searchView.setIconified(true);
//            return;
//        }
//        super.onBackPressed();
//    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.twomenu, menu);
//        inflater.inflate(R.menu.menu_main,menu);
//        MenuItem searchItem = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        return true;
//    }



    private void Loadingdatasortdata(String sorting_id) {
        final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.product_sortingUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.d("AnkushResponse", "onResponse: " + response);

                Gson gsonObj = new Gson();
                com.shrinkcom.medicale.model.productmodel.ProductModel productModel = gsonObj.fromJson(response.toString(), com.shrinkcom.medicale.model.productmodel.ProductModel.class);
                try {
                    productModels = productModel.getData().getProductsList();
                    Log.d("arraysize", String.valueOf(productModels.size()));
                    String status = productModel.getStatus();
                    if (status.equals("1")) {
                        if (productModels.size() > 0) {
                            recycler_product.setLayoutManager(new GridLayoutManager(ProductActivity.this, 2));
                            recycler_product.setItemAnimator(new DefaultItemAnimator());
                            productAdapter = new ProductAdapter(ProductActivity.this, productModels, new ButtonClick() {
                                @Override
                                public void onClick(String product_id) {
//                                    Intent intent = new Intent(getApplicationContext(),ProductOneActivity.class);
//                                    intent.putExtra("vendor_id",vendor_id);
//                                    intent.putExtra("product_id",product_id);
//                                    startActivity(intent);
//                                    startActivity(new Intent(ProductActivity.this, ProductOneActivity.class));

                                }

                                @Override
                                public void onClickItem(String card_id, String quantity) {

                                }
                            });
                            recycler_product.setAdapter(productAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "Emptylist", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Log.e("ERRORRRRR", "---->" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
//action=register,username,email,phone,password,profile
                params.put("pharmacy_id", "" + vendor_id);
                params.put("sort_product", "" + sorting_id);
                params.put("sub_category_id", "" + sub_cat_id);
                params.put("user_id", "" + User_id);
                params.put("action", "product_sorting");
                Log.e("paramsvaluee", ">>" + params);
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        // addToRequestQueue(multipartRequest);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }


}






