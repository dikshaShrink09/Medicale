package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.storage.UserSession;

import java.util.Locale;

public class SelectLanguageActivty extends AppCompatActivity {
    ImageView back;

    SessionManager sessionManager;
    AppCompatButton btn_english, btn_arabic;
    String currentLanguage = "en", currentLang;
    Locale myLocale;
    final CharSequence[] values = {"English", "Arabic"};
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language_activty);

        back = findViewById(R.id.back);
        getSupportActionBar().hide();

        currentLanguage = getIntent().getStringExtra(currentLang);
        userSession = new UserSession(getApplicationContext());
        sessionManager = new SessionManager(this);

        btn_english = findViewById(R.id.btn_english);
        btn_arabic = findViewById(R.id.btn_arabic);


        btn_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateViews("en");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btn_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews("ar");

            }
        });
    }

    private void updateViews(String languageCode) {
        sessionManager.setLanguage(languageCode);
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.locale = myLocale;
        resources.updateConfiguration(conf, dm);
        String userid = userSession.read(ConstantApi.pre_user_id, "");
        Log.e("useridread", ">>>>>" + userid);
        if (userid.equals(" ") || userid.isEmpty()) {
            startActivity(new Intent(SelectLanguageActivty.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else {
            startActivity(new Intent(SelectLanguageActivty.this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

    }
}