package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shrinkcom.medicale.Adapter.AddressListAdapter;
import com.shrinkcom.medicale.DBhelper.GetUserModal;
import com.shrinkcom.medicale.DBhelper.LoginDataBaseAdapter;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.model.AddressListModel;

import java.util.ArrayList;
import java.util.List;

public class ShowAddressList extends AppCompatActivity {
    AddressListAdapter addressListAdapter;
    List<AddressListModel> addressListModels = new ArrayList<>();
    RecyclerView recycler_address;
    TextView tv_more;
    LoginDataBaseAdapter loginDataBaseAdapter;
    String name,street,phone,code,city,email,state,id;
    List<GetUserModal> contacts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_address_list);

        getSupportActionBar().hide();

        recycler_address = findViewById(R.id.recycler_address);
        tv_more = findViewById(R.id.tv_more);


        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddDeliveryAddress.class);
                startActivity(intent);

            }
        });
        fatchdata();
    }
    public void fatchdata(){

//            contacts.clear();
            loginDataBaseAdapter=new LoginDataBaseAdapter(this);
            loginDataBaseAdapter=loginDataBaseAdapter.open();
            contacts = loginDataBaseAdapter.getAllContacts();
            for (GetUserModal cn : contacts) {
                id = String.valueOf(cn.get_id());
                name = cn.getFullname();
                city = cn.getCity();
                street = cn.getStreet();
                phone = cn.getPhone_number();
                code= cn.getZipcode();
                email = cn.getEmail();
                state = cn.getState();

                Log.d("name>",name);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recycler_address.setLayoutManager(linearLayoutManager);
//                recycler_address.setHasFixedSize(true);

                addressListModels.add(new AddressListModel(id,name,street,phone,code,city,email,state));
                addressListAdapter = new AddressListAdapter(addressListModels,getApplicationContext());
                recycler_address.setAdapter(addressListAdapter);

        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        fatchdata();
//    }
}