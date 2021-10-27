package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.storage.UserSession;

public class SpleshActivity extends AppCompatActivity {
    Handler handler;
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);

        userSession = new UserSession(getApplicationContext());
        Toast.makeText(this, "124", Toast.LENGTH_SHORT).show();
         getSupportActionBar().hide();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userSession.isLoggedIn()){
                    Intent intent=new Intent(SpleshActivity.this,NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(SpleshActivity.this,IntroOneActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);

    }


}
