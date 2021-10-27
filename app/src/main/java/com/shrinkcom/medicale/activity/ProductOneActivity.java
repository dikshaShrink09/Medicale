package com.shrinkcom.medicale.activity;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.devs.readmoreoption.ReadMoreOption;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shrinkcom.medicale.Adapter.MyCartAdapter;
import com.shrinkcom.medicale.MainActivity;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.fragment.MyCartFragment;
import com.shrinkcom.medicale.model.MyCartModel;
import com.shrinkcom.medicale.model.SliderModel;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductOneActivity extends AppCompatActivity  implements
        AdapterView.OnItemSelectedListener{
    private SliderLayout sliderShow;
ImageView back;
String Vendor_id,product_id,v_id;
TextView txt_heading_three;
    Spinner spinner_select_quentity;
    String[] qn = { "1","2",
            "3", "4","5"};
    TextView txt_heading_two,txt_rate,btn_add_to_cart,txt_rate2;
    String price, discount;

    String qauntity,user_id;
    UserSession userSession ;
    TextView maintext,product_indication,tv_vendor,tv_available,tv_no_of_product,tv_minus,tv_plus;
    ArrayList<String> arraylist ;
    FrameLayout frame11;
    int quantity1 = 1;
    ImageView imageview;
    private ArrayList<SliderModel> sliderModels = new ArrayList<SliderModel>();
    String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_one);
        getSupportActionBar().hide();
        initview();
//        Sliderimages();
//        inflateImageSlider();

//        fatchcart();

        userSession = new UserSession(getApplicationContext());
        user_id = userSession.read(ConstantApi.pre_user_id,"");
        Vendor_id = getIntent().getStringExtra("vendor_id");
//        v_id = ProductActivity.vendor_id;
        product_id = getIntent().getStringExtra("product_id");
        fatchdetails(Vendor_id,product_id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        frame11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(this).build();
//
//        // OR using options to customize
//
//         readMoreOption = new ReadMoreOption.Builder(this)
//                .textLength(3, ReadMoreOption.TYPE_LINE) // OR
//                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
//                .moreLabel("MORE")
//                .lessLabel("LESS")
//                .moreLabelColor(Color.RED)
//                .lessLabelColor(Color.BLUE)
//                .labelUnderLine(true)
//                .expandAnimation(true)
//                .build();
//
//        readMoreOption.addReadMoreTo(txt_heading_three, getString(R.string.the_converse_chuck_taylore_all_star_leather_sneaker_add_a_rich_n_textured_leather_upper_onto_the_world_s_most_iconic_hight));

        spinner_select_quentity.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,qn);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_select_quentity.setAdapter(aa);
    }
  void  initview()
    {
       back = findViewById(R.id.back);
        tv_no_of_product = findViewById(R.id.tv_no_of_product11);
        imageview = findViewById(R.id.imageview);
        tv_minus = findViewById(R.id.tv_minus);
        tv_plus = findViewById(R.id.tv_plus);
        frame11 = findViewById(R.id.frame11);
        product_indication = findViewById(R.id.product_indication);
        tv_vendor = findViewById(R.id.tv_vendor);
        tv_available = findViewById(R.id.tv_available);
        sliderShow = findViewById(R.id.slider);
        txt_heading_three = findViewById(R.id.txt_heading_three);
        spinner_select_quentity = findViewById(R.id.spinner_select_quentity);
        txt_heading_two = findViewById(R.id.txt_heading_two);
        txt_rate = findViewById(R.id.txt_rate);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        txt_rate2 = findViewById(R.id.txt_rate2);
        maintext = findViewById(R.id.maintext);



        frame11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyCartFragment.class);
                startActivity(intent);
            }
        });



        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_To_cart();
            }
        });
        tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity1 ++;
                tv_no_of_product.setText(String.valueOf(quantity1));
                double grandtotal = Integer.parseInt(price) * quantity1;
                txt_rate.setText(String.valueOf(grandtotal));
//                txt_rate2.setText();

            }
        });

        tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity1>1) {
                    quantity1--;
                    tv_no_of_product.setText(String.valueOf(quantity1));
                    double grandtotal = Integer.parseInt(price) * quantity1;
                    txt_rate.setText("OMR"+" "+String.valueOf(grandtotal));
                }
//                txt_rate2.setText();

            }
        });
    }

    private void inflateImageSlider(ArrayList<String> arraylist) {
        {
            // Using Image Slider -----------------------------------------------------------------------
            sliderShow = findViewById(R.id.slider);
            //populating Image slider

//            sliderImages.add(R.drawable.dwai);
//            sliderImages.add(R.drawable.category_image);
//            sliderImages.add(R.drawable.intro_three);
//            sliderImages.add(R.drawable.otc_product);

            for (String s : arraylist) {
                Log.e("SSSSS", "" + s);
                DefaultSliderView sliderView = new DefaultSliderView(getApplicationContext());
                sliderView.image(s);
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
//    private void Sliderimages() {
//        final ProgressDialog progressDialog = new ProgressDialog(ProductOneActivity.this);
//        progressDialog.setCancelable(true);
//        progressDialog.show();
//
//        progressDialog.setContentView(R.layout.progress_layout);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.sliderUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                String resultResponse = new String(response);
//                Log.e("resultResponseabc", "--->" + resultResponse);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String status = jsonObject.getString("status");
//                    if (status.equals("1")) {
////                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jo = jsonArray.getJSONObject(i);
//                            String id = jo.getString("id");
//                            String title = jo.getString("title");
//                            String description = jo.getString("description");
//                            String image = jo.getString("image");
//                            arraylist = new ArrayList<>();
//
//                            sliderModels.add(new SliderModel(image));
//                            String strimages = "https://shrinkcom.com/pharma/" +
//                                    "" + image;
//
//                            Log.e("Gallery", strimages);
//                            arraylist.add(strimages);
//                            progressDialog.dismiss();
//
//                        }
//                        inflateImageSlider(arraylist);
//                    }else {
//                        progressDialog.dismiss();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//
//
//                }
//
//
//            }
//
//
//        },  new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//
//                error.printStackTrace();
//                Log.e("ERRORRRRR","---->"+error);
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<>();
////action=register,username,email,phone,password,profile
//                //  params.put("username", ""+username);
//
////                params.put("action", "user_login" );
//                Log.e("paramsvaluee", ">>" + params);
//                return params;
//            }};
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//
//
//        // addToRequestQueue(multipartRequest);
//    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        qauntity = spinner_select_quentity.getItemAtPosition(position).toString();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void fatchdetails(String V_id, String P_id) {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(ProductOneActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.product_detailUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("product_details");
                            JSONObject jsonObject3 = jsonObject1.getJSONObject("vendor_details");

                            String product_id = jsonObject2.getString("product_id");
                            String product_name = jsonObject2.getString("product_name");
                            String product_description = jsonObject2.getString("product_description");
                            String product_price = jsonObject2.getString("product_price");
                            String product_quantity = jsonObject2.getString("product_quantity");
                            String price_discounted = jsonObject2.getString("price_discounted");
                            String product_image = jsonObject2.getString("product_image");
                            String product_indication1 = jsonObject2.getString("product_indication");
                            String product_packing = jsonObject2.getString("product_packing");
                            String product_discount = jsonObject2.getString("product_discount");
                            String category_id = jsonObject2.getString("category_id");
                            String sub_category_id = jsonObject2.getString("sub_category_id");
                            String created_by = jsonObject2.getString("created_by");
                            String approved_by = jsonObject2.getString("approved_by");
                            String brand_id = jsonObject2.getString("brand_id");
                            String created_at = jsonObject2.getString("created_at");
                            String status1 = jsonObject2.getString("status");
                            String vendor_name = jsonObject3.getString("pharmacy_name");
                            price = jsonObject2.getString("product_price");
                            discount = jsonObject2.getString("product_discount");

                            imageUrl = jsonObject2.getString("product_image");
                            if (imageUrl.equals("")) {
                                Picasso.with(getApplicationContext())
                                        .load(R.drawable.otc_product)
                                        .into(imageview);
                            } else {
                                Picasso.with(getApplicationContext())
                                        .load("https://shrinkcom.com/pharma/" + imageUrl)
                                        .into(imageview);
                            }

                            txt_rate.setText("OMR" + " " + product_price);
                            txt_rate2.setText("OMR" + " " + product_discount);
                            txt_heading_two.setText(product_name);
                            txt_heading_three.setText(product_description);

                            tv_available.setText("In stock" + " " + product_quantity);
                            tv_vendor.setText(vendor_name);
                            product_indication.setText(product_indication1);


                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
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
                    params.put("action", "product_detail");
                    params.put("product_id", P_id);
                    params.put("vendor_id", V_id);
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

    private void add_To_cart() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(ProductOneActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.add_to_cartUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject json = new JSONObject(response);
                        String msg = json.getString("message");
                        String status = json.getString("status");
                        if (status.equals("1")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            fatchcart();
                            Intent intent = new Intent(getApplicationContext(), MyCartFragment.class);
                            startActivity(intent);
                            progressDialog.dismiss();

                        } if (status.equals("0")){
                            btn_showMessage();
                            //JSONObject resultObj = response.getJSONObject("msg");
                            progressDialog.dismiss();

                        }
                        // editor.putBoolean(Constant.IS_LOGIN, true);

                    } catch (Exception e) {
                    }
                    progressDialog.dismiss();

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
                    params.put("vendor_id", Vendor_id);
                    params.put("product_id", product_id);
                    params.put("user_id", user_id);
                    params.put("quantity", String.valueOf(quantity1));
                    params.put("action", "add_to_cart");
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

    private void fatchcart() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(ProductOneActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.cart_itemsUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("cart");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                ConstantApi.cart_count = jsonObject1.getString("cart_count");
                                maintext.setText(ConstantApi.cart_count);

                            }
                        } else {
                            maintext.setText("0");
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
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
                    params.put("user_id", "" + user_id);

//                params.put("action", "user_login" );
                    Log.e("paramsvaluee", ">>" + params);
                    return params;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            // addToRequestQueue(multipartRequest);
        }else{
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

    @Override
    protected void onResume() {
        super.onResume();
        fatchcart();
    }
    public void btn_showMessage() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(ProductOneActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.alerdialog, null);


        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        final TextView btn_ok= (TextView) mView.findViewById(R.id.btn_ok);
        final TextView btn_cancel= (TextView) mView.findViewById(R.id.btn_cancel);



        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MyPrescriptionUploadActivity.this, NavigationActivity.class);
//                startActivity(intent);
                add_To_cart_again();
                alertDialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MyPrescriptionUploadActivity.this, NavigationActivity.class);
//                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void add_To_cart_again() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(ProductOneActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  Log.e("USLLLL","===??"+RestClient.ROOT_URL+"/users_api/update_avt");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantApi.add_to_cart1Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String resultResponse = new String(response);
                    Log.e("resultResponseabc", "--->" + resultResponse);
                    try {
                        JSONObject json = new JSONObject(response);
                        String msg = json.getString("message");
                        String status = json.getString("status");
                        if (status.equals("1")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            fatchcart();
                            Intent intent = new Intent(getApplicationContext(), MyCartFragment.class);
                            startActivity(intent);
                            progressDialog.dismiss();

                        } if (status.equals("0")){
                            btn_showMessage();
                            //JSONObject resultObj = response.getJSONObject("msg");
                            progressDialog.dismiss();

                        }
                        // editor.putBoolean(Constant.IS_LOGIN, true);

                    } catch (Exception e) {
                    }
                    progressDialog.dismiss();

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
                    params.put("vendor_id", Vendor_id);
                    params.put("product_id", product_id);
                    params.put("user_id", user_id);
                    params.put("quantity", String.valueOf(quantity1));
                    params.put("action", "add_to_cart");
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

}
