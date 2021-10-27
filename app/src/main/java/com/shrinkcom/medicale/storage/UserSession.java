package com.shrinkcom.medicale.storage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.activity.LoginActivity;

import java.util.HashMap;

public class UserSession {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";

    // First time logic Check
    public static final String FIRST_TIME = "firsttime";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Mobile number (make variable public to access from outside)
    public static final String KEY_MOBiLE = "mobile";

    // user avatar (make variable public to access from outside)
    public static final String KEY_PHOTO = "photo";

    // number of items in our cart
    public static final String KEY_CART = "cartvalue";

    // number of items in our wishlist
    public static final String KEY_WISHLIST = "wishlistvalue";
    public static final String Key_userID = "wishlistvalue";

    // check first time app launch
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    // Constructor
    public UserSession(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */
    public void createLoginSession(String user_id,String name, String email, String mobile, String photo,String Pincode, String Address){
        // Storing login value as TRUE
        editor.putBoolean(ConstantApi.IS_LOGGEDIN, true);

        // Storing name in pref
        editor.putString(ConstantApi.pre_first, name);

        // Storing email in pref
        editor.putString(ConstantApi.pre_email, email);

        // Storing phone number in pref
        editor.putString(ConstantApi.pre_contact, mobile);
        editor.putString(ConstantApi.pre_user_id, user_id);

        // Storing image url in pref
        editor.putString(ConstantApi.image, photo);
        editor.putString(ConstantApi.pre_pincode, Pincode);
        editor.putString(ConstantApi.pre_address, Address);

        // commit changes
        editor.commit();
    }

    public  void checkliked(String liked){
        editor.putString(ConstantApi.liked, liked);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user phone number
        user.put(KEY_MOBiLE, pref.getString(KEY_MOBiLE, null));

        // user avatar
        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null)) ;

        user.put(Key_userID, pref.getString(Key_userID, null)) ;

        // return user
        return user;
    }
    public  String read(String key, String defValue) {

        return pref.getString(key, defValue);
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.putBoolean(ConstantApi.IS_LOGGEDIN,false);
        editor.remove(ConstantApi.pre_user_id);
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(ConstantApi.IS_LOGGEDIN, false);
    }

    public int getCartValue(){
        return pref.getInt(KEY_CART,0);
    }

    public int getWishlistValue(){
        return pref.getInt(KEY_WISHLIST,0);
    }

    public Boolean  getFirstTime() {
        return pref.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean n){
        editor.putBoolean(FIRST_TIME,n);
        editor.commit();
    }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
