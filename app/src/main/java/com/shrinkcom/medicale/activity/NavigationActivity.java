package com.shrinkcom.medicale.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.fragment.DashboardFragment;
import com.shrinkcom.medicale.fragment.MyCartFragment;
import com.shrinkcom.medicale.fragment.MyOrderOneFragment;
import com.shrinkcom.medicale.fragment.MyProflieFragment;
import com.shrinkcom.medicale.fragment.OrderHistoryFragment;
import com.shrinkcom.medicale.fragment.SupportFragment;
import com.shrinkcom.medicale.fragment.WishListFragment;
import com.shrinkcom.medicale.notiifcation.Config;
import com.shrinkcom.medicale.storage.UserSession;
import com.shrinkcom.medicale.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
 //   private AppBarConfiguration mAppBarConfiguration;
    public static int currentHome = -1;
 public   static BottomNavigationView navigation;
    ImageView imv_menu,imv_search;
    NavigationView navigationView;
    ImageView imageView;
    TextView tv_tittle,mainuser;
    String username,user_id;
    boolean doubleBackToExitPressedOnce = false;
    int get_postion=0;
    UserSession userSession;
    String ImageURL;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
       // getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tv_tittle =findViewById(R.id.tv_tittle);

        userSession = new UserSession(getApplicationContext());
        username = userSession.read(ConstantApi.pre_first,"");
        user_id = userSession.read(ConstantApi.pre_user_id,"");
         ImageURL= userSession.read(ConstantApi.image,"");
        pref = getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };


        Log.d("username>>>>>",username);
        Toast.makeText(this, "username>"+username, Toast.LENGTH_SHORT).show();

        //getSupportActionBar().hide();
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);

        //toolbar.setLogo(R.drawable.ic_toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);


        imageView =headerView.findViewById(R.id.imageView);
        mainuser = headerView.findViewById(R.id.mainuser);

        mainuser.setText(username);

        Log.d("imageurl", "initiUI: "+ImageURL);
        if (!ImageURL.isEmpty()){
            Glide.with(getApplicationContext()).load("https://shrinkcom.com/pharma/" +ImageURL).into(imageView);
        }else {
            Glide.with(getApplicationContext()).load(getDrawable(R.drawable.person)).into(imageView);
        }

        imv_menu = findViewById(R.id.imv_menu);
//todo receiving postion of medicin category
       get_postion= getIntent().getIntExtra("position",0);

       if (get_postion==1)
       {
           tv_tittle.setText(R.string.otc_product);

           loadFragment(new MedicineCategoryActivity());
       }

  else if (get_postion==5)
       {
           loadFragment(new SingleProductActivity());
           tv_tittle.setText(R.string.product_name);
       }


     else
       {
           loadFragment(new DashboardFragment());
       }


        imv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!drawer.isDrawerOpen(Gravity.LEFT)) drawer.openDrawer(Gravity.LEFT);
                    else drawer.closeDrawer(Gravity.RIGHT);


            }
        });



        navigationView.setNavigationItemSelectedListener(this);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        int id = R.id.navigation__my_cart;







    }



    @SuppressLint("SetTextI18n")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_dash:
                loadFragment(new DashboardFragment());
                Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show();
                tv_tittle.setText(R.string.dashboard);

              /*  Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);*/
                break;


            case R.id.nav_My_order:
                loadFragment(new MyOrderOneFragment());
                Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show();
                tv_tittle.setText(R.string.dashboard);


                break;
            case R.id.nav_wish:
                loadFragment(new WishListFragment());

                tv_tittle.setText(R.string.wish_list);

                break;
            case R.id.navigation_home:
                loadFragment(new DashboardFragment());
                //Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show();
                tv_tittle.setText(R.string.dashboard);

                break;
            case R.id.navigation_medicine:

                loadFragment(new MedicineCategoryActivity());
                Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show();
                tv_tittle.setText(R.string.product);
               /* Intent intent = new Intent(this,MyPrescriptionUploadActivity.class);
                startActivity(intent);*/
                break;
            case R.id.navigation_prescription:
                Intent intent = new Intent(this,MyPrescriptionUploadActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_notification:
                loadFragment(new MyOrderOneFragment());
               // Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show();
                tv_tittle.setText(R.string.product);
                break;
            case R.id.nav_profile:
                loadFragment(new MyProflieFragment());
                Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show();
                tv_tittle.setText(R.string.profile);
                break;
                case R.id.nav_history:
                loadFragment(new OrderHistoryFragment());
                    tv_tittle.setText(R.string.order_history);
                break;
            case R.id.nav_changepassword:
                Intent intent1 = new Intent(this,ChangePasswordActivity.class);
                startActivity(intent1);
                tv_tittle.setText(R.string.change_password);
                break;
            case R.id.navigation__my_cart:
                loadFragment(new MyCartFragment());
                tv_tittle.setText(R.string.my_cart);
//                int menuItemId = navigation.getMenu().getItem(4).getItemId();
//                BadgeDrawable badge = navigation.getOrCreateBadge(menuItemId);
//                badge.setBadgeGravity(BadgeDrawable.TOP_END);
//                badge.setNumber(2);
                break;
            case R.id.nav_support:
                loadFragment(new SupportFragment());

                tv_tittle.setText(R.string.contactus);
                break;
                case R.id.nav_logout:
//                loadFragment(new SupportFragment());
//                userSession.logoutUser();
                    editor.putBoolean(ConstantApi.IS_LOGGEDIN,false);
                    editor.remove(ConstantApi.pre_user_id);
                    editor.commit();
                    // After logout redirect user to Login Activity
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    // Closing all the Activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // Add new Flag to start new Activity
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // Staring Login Activity
                    startActivity(i);
                    finish();
                tv_tittle.setText(R.string.logout);
                break;

            case R.id.nav_languagechange:
                Intent intent11 = new Intent(this,SelectLanguageActivty.class);
                startActivity(intent11);
                break;
//                tv_tittle.setText("Change Password");

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {

        int seletedItemId = navigation.getSelectedItemId();
        if (R.id.navigation_home != seletedItemId) {
            navigation.setSelectedItemId(R.id.navigation_home);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
    public static void showBadge(Context context,  @IdRes int itemId, String value) {
        removeBadge(navigation, itemId);
        BottomNavigationItemView itemView = navigation.findViewById(R.id.navigation__my_cart);
        View badge = LayoutInflater.from(context).inflate(R.layout.component_tabbar_badge, navigation, false);

        TextView text = badge.findViewById(R.id.notificationsBadgeTextView);
        text.setText(value);
        itemView.addView(badge);
    }

    public static void removeBadge(BottomNavigationView bottomNavigationView, @IdRes int itemId) {
        BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);
        if (itemView.getChildCount() == 3) {
            itemView.removeViewAt(2);
        }
    }
    public void fatchcart() {
        if (APPUTILS.isNetworkConnected(getApplicationContext())) {

            final ProgressDialog progressDialog = new ProgressDialog(NavigationActivity.this);
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

                                String cart_id = jo.getString("cart_id");
                                String product_id = jo.getString("product_id");
                                String user_id = jo.getString("user_id");
                                String product_count = jo.getString("product_count");
                                String product_name = jo.getString("product_name");
                                String product_description = jo.getString("product_description");
                                String product_price = jo.getString("product_price");
                                String product_quantity = jo.getString("product_quantity");
                                String price_discounted = jo.getString("price_discounted");
                                String product_image = jo.getString("product_image");
                                String product_indication = jo.getString("product_indication");
                                String product_packing = jo.getString("product_packing");
                                String product_discount = jo.getString("product_discount");
                                String category_id = jo.getString("category_id");
                                String sub_category_id = jo.getString("sub_category_id");

                                String created_by = jo.getString("created_by");
                                String approved_by = jo.getString("approved_by");
                                String brand_id = jo.getString("brand_id");
                                String created_at = jo.getString("created_at");
                                String statuss = jo.getString("status");
                                String subtotal = jsonObject1.getString("total");
                                ConstantApi.cart_count = jsonObject1.getString("cart_count");

                                showBadge(getApplicationContext(), R.id.navigation__my_cart, ConstantApi.cart_count);


//                            txt_subtotal.setText("$"+" "+subtotal);
//                            txt_total.setText("$"+" "+subtotal);
//                            myCartModelArrayList.add(new MyCartModel(cart_id,product_id,user_id,product_count,product_name,
//                                    product_description,product_price,product_quantity,product_image,price_discounted,product_indication,
//                                    product_packing,product_discount,category_id,sub_category_id,created_by,approved_by,brand_id,created_at,statuss));
//
//
////                            pharmacyListModels.add(new PharmacyListModel(
////                                    id,first_name,pharmacy_name,logo,country,state,city,locality,mobile,vendor_type,vendor_id,last_name,pincode,
////                                    delivery_address,statuss,subscription_status));
//                            myCartAdapter = new MyCartAdapter(getContext(), myCartModelArrayList);
//
//                            recycler_my_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//                            recycler_my_cart.setItemAnimator(new DefaultItemAnimator());
//                            recycler_my_cart.setAdapter(myCartAdapter);


                            }
                        } else {
                            showBadge(getApplicationContext(), R.id.navigation__my_cart, "0");

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
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("TAG", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)){

        }
        // txtRegId.setText("Firebase Reg Id: " + regId);
        else{

        }
        //txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        fatchcart();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

}