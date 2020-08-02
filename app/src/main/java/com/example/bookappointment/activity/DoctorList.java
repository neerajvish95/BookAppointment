package com.example.bookappointment.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookappointment.APIinterfAce.DoctorApi;
import com.example.bookappointment.Adapter.DoctorListAdapter;
import com.example.bookappointment.R;
import com.example.bookappointment.RetrofitBuilder.APIError;
import com.example.bookappointment.RetrofitBuilder.ErrorUtils;
import com.example.bookappointment.RetrofitBuilder.RetrofitClientInstance;
import com.example.bookappointment.model.DoctorDTO;
import com.example.bookappointment.model.DoctorDetailsDTO;
import com.example.bookappointment.model.DoctorListDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorList extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    RetrofitClientInstance retrofitClientInstance;
    DoctorApi doctorApi;
    APIError apiError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        retrofitClientInstance = new RetrofitClientInstance();
        doctorApi = retrofitClientInstance.getDoctorApi();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<DoctorDTO> input = new ArrayList<>();

//        https:
//run.mocky.io/v3/5f25ad05-6eba-4453-8f2f-df56607e4ef3   ---detil


//        https:
//run.mocky.io/v3/22d99dd2-45ad-48e8-8ac4-da838a8a02fe  --- list


        getDoctorList();
//        getDoctorinfo();
    }

    public ProgressDialog mProgressDialog;

    protected void showProgressDialog() {
        mProgressDialog = new ProgressDialog(DoctorList.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide();
                mProgressDialog.dismiss();
            }
        }
    }

    public void getDoctorList() {
        showProgressDialog();
        Call<DoctorListDTO> call = doctorApi.doctorlist();

        call.enqueue(new Callback<DoctorListDTO>() {
            @Override
            public void onResponse(final Call<DoctorListDTO> call, final Response<DoctorListDTO> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    DoctorListDTO doctorListDTO = response.body();
                    List<DoctorDTO> items = doctorListDTO.doctorDTOs;

                    mAdapter = new DoctorListAdapter(items, DoctorList.this);
                    recyclerView.setAdapter(mAdapter);
//                    Toast.makeText(DoctorList.this, "Testing", Toast.LENGTH_SHORT).show();
                    ;

                } else {
                    apiError = ErrorUtils.parseError(response);

                }
            }

            @Override
            public void onFailure(final Call<DoctorListDTO> call, final Throwable t) {
                hideProgressDialog();

                apiError = new APIError.Builder().defaultError().build();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
//            case R.id.action_home:
//                navigateToHome();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getDoctorinfo() {
        Call<DoctorDetailsDTO> call = doctorApi.doctorinfo();

        call.enqueue(new Callback<DoctorDetailsDTO>() {
            @Override
            public void onResponse(final Call<DoctorDetailsDTO> call, final Response<DoctorDetailsDTO> response) {
                if (response.isSuccessful()) {
//                    List<ClipData.Item> items = response.body();
//                    Toast.makeText(DoctorList.this, "Testing", Toast.LENGTH_SHORT).show();
                    ;

                } else {
                    apiError = ErrorUtils.parseError(response);

                }
            }

            @Override
            public void onFailure(final Call<DoctorDetailsDTO> call, final Throwable t) {
                apiError = new APIError.Builder().defaultError().build();
            }
        });
    }
}
