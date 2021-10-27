package com.shrinkcom.medicale.retrofit;


import androidx.multidex.BuildConfig;

import com.shrinkcom.medicale.activity.ConstantApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static Retrofit retrofit = null;

    private RetrofitFactory() {
    }

    public static Retrofit getRetrofit() {

        if (retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if(BuildConfig.DEBUG)
            {
                //log information interceptor
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                //Set the Debug Log mode
                builder.addInterceptor(loggingInterceptor);
            }

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient okHttpClient =  builder
                    .readTimeout(3, TimeUnit.MINUTES)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();




            retrofit = new Retrofit.Builder().baseUrl(ConstantApi.BaseURl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                    .build();
        }
        return retrofit;


    }
}
