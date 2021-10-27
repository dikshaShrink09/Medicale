package com.shrinkcom.medicale.retrofit;


import com.shrinkcom.medicale.model.CommonModel;
import com.shrinkcom.medicale.model.SliderModel;
import com.shrinkcom.medicale.retrofit.profilemodel.ProfileModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    //  user_id=1,first_name=ram,last_name=test1,email=rider@gmail.com,phone=1234567890,image=bottle1.png
    @Multipart
    @POST("edit_profile")
    Call<ProfileModel> postImage(@Part MultipartBody.Part image, @Part("user_id") RequestBody user_id,
                                 @Part("full_name") RequestBody full_name,
                                 @Part("mobile_number") RequestBody mobile_number,
                                 @Part("address") RequestBody address,
                                 @Part("pincode") RequestBody pincode,
                                 @Part("action") RequestBody action);

    @Multipart
    @POST("upload_prescription")
    Call<CommonModel> postPrescription(@Part MultipartBody.Part image, @Part("user_id") RequestBody user_id,
                                       @Part("full_name") RequestBody full_name,
                                       @Part("street_address") RequestBody mobile_number,
                                       @Part("city") RequestBody address,
                                       @Part("postal_code") RequestBody pincode,
                                       @Part("state_country") RequestBody state_country,
                                       @Part("phone_number") RequestBody phone_number,
                                       @Part("email") RequestBody email);

    @POST("slider")
    Call<SliderModel> get();
}
