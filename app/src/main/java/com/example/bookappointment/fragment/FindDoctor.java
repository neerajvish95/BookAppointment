package com.example.bookappointment.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.bookappointment.APIinterfAce.DoctorApi;
import com.example.bookappointment.Adapter.AdapterCityList;
import com.example.bookappointment.R;
import com.example.bookappointment.RetrofitBuilder.APIError;
import com.example.bookappointment.RetrofitBuilder.ErrorUtils;
import com.example.bookappointment.RetrofitBuilder.RetrofitClientInstance;
import com.example.bookappointment.activity.DoctorList;
import com.example.bookappointment.model.AutoCompleteResultDTO;
import com.example.bookappointment.model.AutoCompleteResultListDTO;
import com.example.bookappointment.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindDoctor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindDoctor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RetrofitClientInstance retrofitClientInstance;
    DoctorApi doctorApi;
    APIError apiError;
    AdapterCityList dataAdapter;

    // TODO: Rename and change types and number of parameters
    public static FindDoctor newInstance(String param1, String param2) {
        FindDoctor fragment = new FindDoctor();
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
        View view = inflater.inflate(R.layout.find_doctor, container, false);

        retrofitClientInstance = new RetrofitClientInstance();
        doctorApi = retrofitClientInstance.getDoctorApi();

        Button btn_serch = (Button) view.findViewById(R.id.btn_serch);
        btn_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DoctorList.class));
            }
        });

        EditText edt_city = (EditText) view.findViewById(R.id.edt_city);
        edt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcity(edt_city);

            }
        });
        EditText edt_area = (EditText) view.findViewById(R.id.edt_area);
        edt_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getArea(edt_area);
            }
        });

        return view;
    }

    public void getcity(EditText editText) {
        showProgressDialog();
        Call<AutoCompleteResultListDTO> call = doctorApi.getcity();

        call.enqueue(new Callback<AutoCompleteResultListDTO>() {
            @Override
            public void onResponse(final Call<AutoCompleteResultListDTO> call, final Response<AutoCompleteResultListDTO> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    AutoCompleteResultListDTO autoCompleteResultDTOs = response.body();
                    List<AutoCompleteResultDTO> cityList = new ArrayList<AutoCompleteResultDTO>();


                    cityList.addAll(autoCompleteResultDTOs.autoCompleteSearchDTOs);
                    final AlertDialog sort = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Light_NoTitleBar).create();
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View sortView = inflater.inflate(R.layout.dialog_lyt, null);
                    sort.setView(sortView);
                    sort.show();

                    ListView cityView = (ListView) sortView.findViewById(R.id.city_list);
                    final EditText Search_text = (EditText) sortView.findViewById(R.id.textenter);
                    final ImageView close = (ImageView) sortView.findViewById(R.id.close);
                    close.setVisibility(View.GONE);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Search_text.setText("");
                            /**show list */
                        }
                    });
                    ImageView back = (ImageView) sortView.findViewById(R.id.arrow);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sort.dismiss();
                        }
                    });

                    Search_text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            SessionManager sessionManager = new SessionManager(getActivity());
                            if (s.length() == 0) {
                                close.setVisibility(View.GONE);
                                dataAdapter = new AdapterCityList(sessionManager, getActivity(), cityList, sort, editText);
                                cityView.setAdapter(dataAdapter);
                            } else if (s.length() > 1) {
                                close.setVisibility(View.VISIBLE);
                                dataAdapter = new AdapterCityList(sessionManager, getActivity(), dataAdapter.filterlist(s.toString()), sort, editText);
                                cityView.setAdapter(dataAdapter);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    Search_text.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                switch (keyCode) {
                                    case KeyEvent.KEYCODE_DPAD_CENTER:
                                    case KeyEvent.KEYCODE_DPAD_DOWN:
                                    case KeyEvent.KEYCODE_ENTER:
                                        if (Search_text.getText().toString() != null) {

                                            return true;
                                        }
                                    default:
                                        break;
                                }
                            }
                            return false;
                        }
                    });
                    SessionManager sessionManager = new SessionManager(getActivity());

                    dataAdapter = new AdapterCityList(sessionManager, getActivity(), cityList, sort, editText);
                    cityView.setAdapter(dataAdapter);
                    hideProgressDialog();
                } else {
                    apiError = ErrorUtils.parseError(response);

                }
            }

            @Override
            public void onFailure(final Call<AutoCompleteResultListDTO> call, final Throwable t) {
                hideProgressDialog();

                apiError = new APIError.Builder().defaultError().build();
            }
        });
    }

    public void getArea(EditText editText) {
        showProgressDialog();
        Call<AutoCompleteResultListDTO> call = doctorApi.getArea();

        call.enqueue(new Callback<AutoCompleteResultListDTO>() {
            @Override
            public void onResponse(final Call<AutoCompleteResultListDTO> call, final Response<AutoCompleteResultListDTO> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    AutoCompleteResultListDTO autoCompleteResultDTOs = response.body();
                    List<AutoCompleteResultDTO> cityList = new ArrayList<AutoCompleteResultDTO>();


                    cityList.addAll(autoCompleteResultDTOs.autoCompleteSearchDTOs);
                    final AlertDialog sort = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Light_NoTitleBar).create();
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View sortView = inflater.inflate(R.layout.dialog_lyt, null);
                    sort.setView(sortView);
                    sort.show();

                    ListView cityView = (ListView) sortView.findViewById(R.id.city_list);
                    final EditText Search_text = (EditText) sortView.findViewById(R.id.textenter);
                    final ImageView close = (ImageView) sortView.findViewById(R.id.close);
                    close.setVisibility(View.GONE);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Search_text.setText("");
                            /**show list */
                        }
                    });
                    ImageView back = (ImageView) sortView.findViewById(R.id.arrow);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sort.dismiss();
                        }
                    });

                    Search_text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            SessionManager sessionManager = new SessionManager(getActivity());
                            if (s.length() == 0) {
                                close.setVisibility(View.GONE);
                                dataAdapter = new AdapterCityList(sessionManager, getActivity(), cityList, sort, editText);
                                cityView.setAdapter(dataAdapter);
                            } else if (s.length() > 1) {
                                close.setVisibility(View.VISIBLE);
                                dataAdapter = new AdapterCityList(sessionManager, getActivity(), dataAdapter.filterlist(s.toString()), sort, editText);
                                cityView.setAdapter(dataAdapter);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    Search_text.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                switch (keyCode) {
                                    case KeyEvent.KEYCODE_DPAD_CENTER:
                                    case KeyEvent.KEYCODE_DPAD_DOWN:
                                    case KeyEvent.KEYCODE_ENTER:
                                        if (Search_text.getText().toString() != null) {

                                            return true;
                                        }
                                    default:
                                        break;
                                }
                            }
                            return false;
                        }
                    });
                    SessionManager sessionManager = new SessionManager(getActivity());

                    dataAdapter = new AdapterCityList(sessionManager, getActivity(), cityList, sort, editText);
                    cityView.setAdapter(dataAdapter);
                    hideProgressDialog();
                } else {
                    apiError = ErrorUtils.parseError(response);

                }
            }

            @Override
            public void onFailure(final Call<AutoCompleteResultListDTO> call, final Throwable t) {
                hideProgressDialog();

                apiError = new APIError.Builder().defaultError().build();
            }
        });
    }

    public ProgressDialog mProgressDialog;

    protected void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
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
}
