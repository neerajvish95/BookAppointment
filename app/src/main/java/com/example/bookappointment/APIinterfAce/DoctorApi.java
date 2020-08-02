package com.example.bookappointment.APIinterfAce;

import com.example.bookappointment.model.AutoCompleteResultListDTO;
import com.example.bookappointment.model.DoctorDetailsDTO;
import com.example.bookappointment.model.DoctorListDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DoctorApi {

    @GET("22d99dd2-45ad-48e8-8ac4-da838a8a02fe")
    Call<DoctorListDTO> doctorlist();

    @GET("5f25ad05-6eba-4453-8f2f-df56607e4ef3")
    Call<DoctorDetailsDTO> doctorinfo();

    @GET("20fd4617-894c-4bc6-bead-8cb01c37f016")
    Call<AutoCompleteResultListDTO> getcity();

    @GET("d1e13106-04e7-4911-bcd3-1ea1b3581345")
    Call<AutoCompleteResultListDTO> getArea();

//    @GET("country/get/all")
//    Call<Data> getResults();
//
//    @GET("country/get/iso2code/{alpha2_code}")
//    Call<Data> getByAlpha2Code(@Path("alpha2_code") String alpha2Code);
}
