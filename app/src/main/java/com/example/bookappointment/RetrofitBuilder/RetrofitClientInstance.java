package com.example.bookappointment.RetrofitBuilder;


import android.content.Context;
import android.util.Log;

import com.example.bookappointment.APIinterfAce.DoctorApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    public static final String URL = "https://run.mocky.io/v3/";

    private DoctorApi doctorApi;


    public RetrofitClientInstance() {
        try {

            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().addHeader("User-Agent", "Retrofit-Sample-App").build();
                    return chain.proceed(newRequest);
                }
            };

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(interceptor);
            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();


            doctorApi = retrofit.create(DoctorApi.class);

        } catch (Exception ex) {
            Log.e("retrofit", ex.getMessage());
        }
    }


    public DoctorApi getDoctorApi() {
        return doctorApi;
    }


}