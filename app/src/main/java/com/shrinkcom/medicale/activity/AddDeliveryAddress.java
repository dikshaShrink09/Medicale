package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shrinkcom.medicale.DBhelper.LoginDataBaseAdapter;
import com.shrinkcom.medicale.R;

public class AddDeliveryAddress extends AppCompatActivity {
    EditText et_fullname,et_street,et_city,et_zipcode,et_state,et_mobile,et_email;
    String fullname,street,city,zipcode,state,mobile,email;
    AppCompatButton btn_save;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_address);
        getSupportActionBar().hide();

        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();


        et_fullname = findViewById(R.id.et_fullname);
        et_street = findViewById(R.id.et_street);
        et_city = findViewById(R.id.et_city);
        et_zipcode = findViewById(R.id.et_zipcode);
        et_state = findViewById(R.id.et_state);
        et_mobile = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = et_fullname.getText().toString();
                street = et_street.getText().toString();
                city = et_city.getText().toString();
                zipcode = et_zipcode.getText().toString();
                state = et_state.getText().toString();
                email = et_email.getText().toString();
                mobile = et_mobile.getText().toString();

                if (fullname.equals("")){
                    Toast.makeText(AddDeliveryAddress.this, "Please Enter name", Toast.LENGTH_SHORT).show();
                }else if(street.equals("")){
                    Toast.makeText(AddDeliveryAddress.this, "Please enter street", Toast.LENGTH_SHORT).show();
                }else if(city.equals("")){
                    Toast.makeText(AddDeliveryAddress.this, "Please enter city", Toast.LENGTH_SHORT).show();
                }else if (zipcode.equals("")){
                    Toast.makeText(AddDeliveryAddress.this, "Please enter zipcode", Toast.LENGTH_SHORT).show();
                }else  if(state.equals("")){
                    Toast.makeText(AddDeliveryAddress.this, "Please enter state", Toast.LENGTH_SHORT).show();
                }else if (email.equals("")){
                    Toast.makeText(AddDeliveryAddress.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }else if (mobile.equals("")){
                    Toast.makeText(AddDeliveryAddress.this, "Please enter mobile", Toast.LENGTH_SHORT).show();
                }else {

                    loginDataBaseAdapter.insertEntry(fullname, street, city, zipcode, state, email, mobile);
                    Toast.makeText(getApplicationContext(), " Successfully Add Address ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ShowAddressList.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
    protected void onDestroy() {
// TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

}