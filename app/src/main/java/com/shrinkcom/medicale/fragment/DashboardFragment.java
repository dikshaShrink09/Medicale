package com.shrinkcom.medicale.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shrinkcom.medicale.Adapter.CategoryAdapter;
import com.shrinkcom.medicale.Adapter.GeneralCategoryAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.APPUTILS;
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.activity.DiseaseCategoryActivity;
import com.shrinkcom.medicale.activity.LoginActivity;
import com.shrinkcom.medicale.activity.MedicineCategoryActivity;
import com.shrinkcom.medicale.activity.MyPrescriptionUploadActivity;
import com.shrinkcom.medicale.activity.NavigationActivity;
import com.shrinkcom.medicale.activity.PharmacyFragment;
import com.shrinkcom.medicale.interfacess.ButtonClick;
import com.shrinkcom.medicale.model.CategoryModel;
import com.shrinkcom.medicale.model.GeneralCategoryModel;
import com.shrinkcom.medicale.model.SliderModel;
import com.shrinkcom.medicale.model.SubcategoryModel;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment  {
View view;
AppCompatButton btn_upload_prescription,btn_buy_otc_product;
RecyclerView recycler_category_dashboard,generalcategoryrecycle;
TextView txt_view_all,txt_city_name;
ImageView back;
    TextView tv_tittle;
    LinearLayout lay,lay1;

LinearLayout ll1,ll_upload,lay_uploadprescription;
    Context mcontext;
    private SliderLayout sliderShow;
    private  ArrayList<SubcategoryModel> subcategoryModels = new ArrayList<>();
    private  ArrayList<GeneralCategoryModel> generalCategoryModels = new ArrayList<>();
    private ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
    private ArrayList<SliderModel> sliderModels = new ArrayList<SliderModel>();
    private CategoryAdapter categoryAdapter;
    private GeneralCategoryAdapter generalCategoryAdapter;
    ArrayList<String> arraylist ;
    LinearLayout mainprogress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_dashboard, container, false);
        recycler_category_dashboard = view.findViewById(R.id.recycler_category_dashboard);
        lay = view.findViewById(R.id.layy);
        lay1 = view.findViewById(R.id.layy1);
        generalcategoryrecycle = view.findViewById(R.id.generalcategoryrecycle);

        mainprogress = view.findViewById(R.id.mainprogress);
        ll_upload = view.findViewById(R.id.ll_upload);
        lay_uploadprescription = view.findViewById(R.id.lay_uploadprescription);
        initview();
        Sliderimages();
//        inflateImageSlider(arraylist);
        txt_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     Intent intent = new Intent(getActivity(), MedicineCategoryActivity.class);
                startActivity(intent);*/
                startActivity(new Intent(getActivity(), NavigationActivity.class).putExtra("position", 1));
              /*  tv_tittle.setText(R.string.otc_product);
                loadFragment(new MedicineCategoryActivity());*/

            }
        });

        btn_buy_otc_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NavigationActivity.class).putExtra("position", 1));

            }
        });

        lay_uploadprescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyPrescriptionUploadActivity.class);
                startActivity(intent);
            }
        });



        //todo open bottomsheet for location
  ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheerDialog = new BottomSheetDialog(getActivity());
                View parentView = getLayoutInflater().inflate(R.layout.item_bottom_sheet_location_search,null);
                bottomSheerDialog.setContentView(parentView);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
                bottomSheetBehavior.setPeekHeight(600);
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics());
                bottomSheerDialog.show();
                parentView.findViewById(R.id.img_dismis).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                                  /*  Intent intent = new Intent(mContext, FinishRideActivity.class);
                                    startActivity(intent);*/
                        bottomSheerDialog.cancel();
                    }
                });


            }
        });




        categoryAdapter = new CategoryAdapter(getContext(), subcategoryModels, new ButtonClick() {
            @Override
            public void onClick(String value) {

                startActivity(new Intent(getActivity(), PharmacyFragment.class));
                //tv_tittle.setText("Order History");
            }

            @Override
            public void onClickItem(String card_id, String quantity) {

            }
        });
        generalCategoryAdapter = new GeneralCategoryAdapter(getContext(), generalCategoryModels, new ButtonClick() {
            @Override
            public void onClick(String value) {

                startActivity(new Intent(getActivity(), PharmacyFragment.class));
                //tv_tittle.setText("Order History");
            }

            @Override
            public void onClickItem(String card_id, String quantity) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

//        prepareCourse();
        Loadingdata();
        LoadinggeneralCategory();


        ll_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PharmacyFragment.class);
                intent.putExtra("upload_status","btn_upload");
                startActivity(intent);
            }
        });
       /* btn_bye_otc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProductFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                navigation.setSelectedItemId(R.id.navigation_medicine);
            }
        });*/


    return view;
    }

    private void Loadingdata() {
        if (APPUTILS.isNetworkConnected(getContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.all_categoriesUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                progressDialog.dismiss();
                    progressDialog.dismiss();

                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                String sub_cat_id = jo.getString("sub_cat_id");
                                String cat_id = jo.getString("cat_id");
                                String sub_cat_name = jo.getString("sub_cat_name");
                                String createdd_at = jo.getString("createdd_at");
                                String sub_cat_photo = jo.getString("sub_cat_photo");
                                subcategoryModels.add(new SubcategoryModel(sub_cat_id, cat_id, sub_cat_name, createdd_at, sub_cat_photo));
                                recycler_category_dashboard.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                                recycler_category_dashboard.setItemAnimator(new DefaultItemAnimator());
                                recycler_category_dashboard.setAdapter(categoryAdapter);

                                // Do you fancy stuff
                                // Example: String gifUrl = jo.getString("url");
                            }
                        } else {
                            lay.setVisibility(View.VISIBLE);
                            Toast.makeText(mcontext, "Error", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();

                        e.printStackTrace();
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
                    //  params.put("username", ""+username);

//                params.put("action", "user_login" );
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
        }else {
            final Dialog dialog = new Dialog(getContext());
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
    private void LoadinggeneralCategory() {
        if (APPUTILS.isNetworkConnected(getContext())) {


            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.general_sub_categoriesUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                progressDialog.dismiss();
                    progressDialog.dismiss();

                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("category");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                String sub_cat_id = jo.getString("sub_cat_id");
                                String cat_id = jo.getString("cat_id");
                                String sub_cat_name = jo.getString("sub_cat_name");
                                String createdd_at = jo.getString("createdd_at");
                                String sub_cat_photo = jo.getString("sub_cat_photo");
                                generalCategoryModels.add(new GeneralCategoryModel(sub_cat_id, cat_id, sub_cat_name, createdd_at, sub_cat_photo));
                                generalcategoryrecycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                                generalcategoryrecycle.setItemAnimator(new DefaultItemAnimator());

                                generalcategoryrecycle.setAdapter(categoryAdapter);

                                // Do you fancy stuff
                                // Example: String gifUrl = jo.getString("url");
                            }
                        } else {
                            lay.setVisibility(View.VISIBLE);
                            Toast.makeText(mcontext, "Error", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();

                        e.printStackTrace();
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
                    //  params.put("username", ""+username);

//                params.put("action", "user_login" );
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
        }else {
            final Dialog dialog = new Dialog(getContext());
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
    private void Sliderimages() {
        if (APPUTILS.isNetworkConnected(getContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(true);
            progressDialog.show();

            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.sliderUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                Log.d("jsonrespect>", String.valueOf(jsonArray.length()));
                                String id = jo.getString("id");
                                String title = jo.getString("title");
                                String description = jo.getString("description");
                                String image = jo.getString("image");
//                                arraylist = new ArrayList<>();

                                String strimages = "https://shrinkcom.com/pharma/" +
                                        "" + image;
                                sliderModels.add(new SliderModel(strimages));

                                Log.e("Gallery", strimages);
//                                arraylist.add(strimages);
                                Log.d("arraylist>", String.valueOf(sliderModels.size()));
                                progressDialog.dismiss();
                            }
                            inflateImageSlider(sliderModels);
                        } else {
                            progressDialog.dismiss();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();


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
                    //  params.put("username", ""+username);

//                params.put("action", "user_login" );
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


            // addToRequestQueue(multipartRequest);
        }else{
            final Dialog dialog = new Dialog(getContext());
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



//TODO 29/01/2021 integrate api of slider images
//    private void getGalleryImages() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(mcontext);
//        progressDialog.setCancelable(true);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.progreass_lay);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        Call<DashboardGallerPojo> call = apiservices.getGallery("slider");
//        call.enqueue(new Callback<DashboardGallerPojo>() {
//            @Override
//            public void onResponse(Call<DashboardGallerPojo> call, Response<DashboardGallerPojo> response) {
//                DashboardGallerPojo jong = response.body();
//                //      Log.e("GALLERY", ">>" + jong.getResult());
//                ArrayList<String> arraylist = new ArrayList<>();
//                try {
//                    if (jong.getResult() == 1) {
//                        HashMap<String, String> url_maps = new HashMap<String, String>();
//
//                        for (int i = 0; i < jong.getUserData().size(); i++) {
//                            String strimages = BASE_URL + jong.getUserData().get(i).getSImage();
//
//                            Log.e("Gallery", strimages);
//
//                            arraylist.add(strimages);
//                        }
//                        inflateImageSlider(arraylist);
//                    } else {
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                progressDialog.dismiss();
//
//            }
//
//            @Override
//            public void onFailure(Call<DashboardGallerPojo> call, Throwable t) {
//                Log.e("FAILURE", ">>" + call + " :  " + t);
//                progressDialog.dismiss();
//            }
//        });
//    }
    private void inflateImageSlider(ArrayList<SliderModel> arraylist) {
        {
            // Using Image Slider -----------------------------------------------------------------------
            sliderShow = view.findViewById(R.id.slider);

            for (SliderModel s : arraylist) {
                Log.e("SSSSS", "" + s);
                Log.d("final>>", String.valueOf(arraylist.size()));
                DefaultSliderView sliderView = new DefaultSliderView(getActivity());
                sliderView.image(s.getImgUrl());
                sliderShow.addSlider(sliderView);
            }
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

           /* sliderShow = view.findViewById(R.id.slider);
            for (String s : arraylist) {
                Log.e("SSSSS", "" + s);
                DefaultSliderView sliderView = new DefaultSliderView(mcontext);
                sliderView.image(s);
                sliderShow.addSlider(sliderView);
            }
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);*/

        }
    }
 void initview() {

     txt_view_all = view.findViewById(R.id.txt_view_all);
     tv_tittle = view.findViewById(R.id.tv_tittle);
     sliderShow = view.findViewById(R.id.slider);
     txt_city_name = view.findViewById(R.id.txt_city_name);
     ll1 = view.findViewById(R.id.ll1);
     btn_buy_otc_product = view.findViewById(R.id.btn_buy_otc_product);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}