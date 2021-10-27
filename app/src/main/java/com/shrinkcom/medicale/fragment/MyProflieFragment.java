package com.shrinkcom.medicale.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.activity.ConstantApi;
import com.shrinkcom.medicale.activity.ProfileActivity;
import com.shrinkcom.medicale.storage.UserSession;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProflieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProflieFragment extends Fragment {
AppCompatButton btn_edit_profile;
View view;
ImageView img_myprofile;
TextView tv_user_name,tv_username,tv_mobile_no,tv_pin_no,tv_address;
UserSession userSession;
String name, username,phone,ImageURL,pincode,address;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyProflieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProflieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProflieFragment newInstance(String param1, String param2) {
        MyProflieFragment fragment = new MyProflieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_proflie, container, false);

        btn_edit_profile = view.findViewById(R.id.btn_edit_profile);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_username = view.findViewById(R.id.tv_username);
        tv_mobile_no = view.findViewById(R.id.tv_mobile_no);
        tv_pin_no = view.findViewById(R.id.tv_pin_no);
        tv_address = view.findViewById(R.id.tv_address);
        img_myprofile = view.findViewById(R.id.img_myprofile);

        userSession = new UserSession(getContext());
        username = userSession.read(ConstantApi.pre_email,"");
        name = userSession.read(ConstantApi.pre_first,"");
        phone = userSession.read(ConstantApi.pre_contact,"");
        ImageURL = userSession.read(ConstantApi.image,"");

        pincode = userSession.read(ConstantApi.pre_pincode,"");
        address = userSession.read(ConstantApi.pre_address,"");

        tv_user_name .setText(name);
        tv_username.setText(username);
        tv_mobile_no.setText(phone);
        tv_address.setText(address);
        tv_pin_no.setText(pincode);


        Log.d("imageurl", "initiUI: "+ImageURL);
        if (!ImageURL.isEmpty()){
            Glide.with(getContext()).load("https://shrinkcom.com/pharma/" +ImageURL).into(img_myprofile);
        }else {
            Glide.with(getContext()).load(img_myprofile.getDrawable()).into(img_myprofile);
        }

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });
   return  view; }
}