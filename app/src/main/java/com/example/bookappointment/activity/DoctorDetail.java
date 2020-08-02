package com.example.bookappointment.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.palette.graphics.Palette;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.bookappointment.APIinterfAce.DoctorApi;
import com.example.bookappointment.R;
import com.example.bookappointment.RetrofitBuilder.APIError;
import com.example.bookappointment.RetrofitBuilder.ErrorUtils;
import com.example.bookappointment.RetrofitBuilder.RetrofitClientInstance;
import com.example.bookappointment.fragment.DoctorOverviewFragment;
import com.example.bookappointment.model.DoctorAwardDTO;
import com.example.bookappointment.model.DoctorDTO;
import com.example.bookappointment.model.DoctorDetailsDTO;
import com.example.bookappointment.model.DoctorEducatioDTO;
import com.example.bookappointment.model.DoctorExperienceDTO;
import com.example.bookappointment.model.DoctorMembershipDTO;
import com.example.bookappointment.model.DoctorRegistrationDTO;
import com.example.bookappointment.model.DoctorServiceDTO;
import com.example.bookappointment.utility.SessionManager;
import com.example.bookappointment.utility.TabsListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoctorDetail extends AppCompatActivity implements TabsListener {
    DoctorDTO doctorDTO;

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    ImageView doctorImageView;
    TextView tvDoctorName, tv_doctoreDegreeService;
    //private String[] tabs = { "Overview", "Photos", "Feedback" };
    ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();
    //private MyPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    AppBarLayout appBarLayout;
    Toolbar topToolBar;
    LinearLayout linearLayoutBookAppointMent;
    Button requestAppBtn, addReviewBtn;
    APIError apiError;
    RetrofitClientInstance retrofitClientInstance;
    DoctorApi doctorApi;

    SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_doctorinfo_lyt);
        setContentView(R.layout.activity_doctor_detail);

        sessionManager = new SessionManager(this);

        doctorDTO = (DoctorDTO) getIntent().getSerializableExtra("DOCTORINFO");
        retrofitClientInstance = new RetrofitClientInstance();
        doctorApi = retrofitClientInstance.getDoctorApi();
        toolbarInit();
        initView();


        fetchDocotrDetail();

        dynamicToolbarColor();
        toolbarTextAppernce();
        //  initTabView();
        //fetchFreshDoctorInfo();

        //initReplaceFirstFrag();

    }

    private void webApiInit() {

    }

    public void fetchDocotrDetail() {
        showProgressDialog();
        Call<DoctorDetailsDTO> call = doctorApi.doctorinfo();

        call.enqueue(new Callback<DoctorDetailsDTO>() {
            @Override
            public void onResponse(final Call<DoctorDetailsDTO> call, final Response<DoctorDetailsDTO> response) {
                if (response.isSuccessful()) {
                    DoctorDetailsDTO doctorDetailsDTO = response.body();
                    hideProgressDialog();

                    if (doctorDetailsDTO.doctorDTO != null) {
                        StringBuilder builder = new StringBuilder();
                        if (doctorDetailsDTO.doctorDTO.AddressNo1 != null) {
                            builder.append(doctorDetailsDTO.doctorDTO.AddressNo1);
                        }
                        if (doctorDetailsDTO.doctorDTO.AddressNo2 != null) {
                            builder.append(" , ");
                            builder.append(doctorDetailsDTO.doctorDTO.AddressNo2);
                        }
                        if (doctorDetailsDTO.doctorDTO.Area != null) {
                            builder.append(" , ");
                            builder.append(doctorDetailsDTO.doctorDTO.Area);
                        }
                        if (doctorDetailsDTO.doctorDTO.City != null) {
                            builder.append(" , ");
                            builder.append(doctorDetailsDTO.doctorDTO.City);
                        }
                        if (doctorDetailsDTO.doctorDTO.State != null) {
                            builder.append(" , ");
                            builder.append(doctorDetailsDTO.doctorDTO.State);
                        }
                        if (doctorDetailsDTO.doctorDTO.Country != null) {
                            builder.append(" , ");
                            builder.append(doctorDetailsDTO.doctorDTO.Country);
                        }
                        if (doctorDetailsDTO.doctorDTO.Pincode != null) {
                            builder.append(" , ");
                            builder.append(doctorDetailsDTO.doctorDTO.Pincode);
                        }

                    }

                    // Service
                    if (doctorDetailsDTO.doctorServiceDTOs != null && doctorDetailsDTO.doctorServiceDTOs.size() > 0) {
                        List<DoctorServiceDTO> serviceDTOs = new ArrayList<DoctorServiceDTO>();

                        serviceDTOs.addAll(doctorDetailsDTO.doctorServiceDTOs);
                        sessionManager.setDetailDoctorServices(serviceDTOs);

                    }

                    //Education
                    if (doctorDetailsDTO.doctorEducatioDTOs != null && doctorDetailsDTO.doctorEducatioDTOs.size() > 0) {
                        List<DoctorEducatioDTO> educatioDTOs = new ArrayList<DoctorEducatioDTO>();

                        educatioDTOs.addAll(doctorDetailsDTO.doctorEducatioDTOs);

                        sessionManager.setDetailDoctorEducation(educatioDTOs);


                    }

                    //Experience

                    if (doctorDetailsDTO.doctorExperienceDTOs != null && doctorDetailsDTO.doctorExperienceDTOs.size() > 0) {
                        List<DoctorExperienceDTO> experienceDTO = new ArrayList<DoctorExperienceDTO>();
                        experienceDTO.addAll(doctorDetailsDTO.doctorExperienceDTOs);

                        sessionManager.setDetailDoctorExperience(experienceDTO);


                    }

                    //Awards

                    if (doctorDetailsDTO.doctorAwardDTOs != null && doctorDetailsDTO.doctorAwardDTOs.size() > 0) {
                        List<DoctorAwardDTO> awardDTOs = new ArrayList<DoctorAwardDTO>();

                        awardDTOs.addAll(doctorDetailsDTO.doctorAwardDTOs);
                        sessionManager.setDetailDoctorAwards(awardDTOs);


                    }

                    //Registration
                    List<DoctorRegistrationDTO> registrationDTOs = new ArrayList<DoctorRegistrationDTO>();
                    if (doctorDetailsDTO.doctorRegistrationDTOs != null && doctorDetailsDTO.doctorRegistrationDTOs.size() > 0) {
                        registrationDTOs.addAll(doctorDetailsDTO.doctorRegistrationDTOs);
                        StringBuilder builder = new StringBuilder();
                        for (DoctorRegistrationDTO registrationDTO : registrationDTOs) {
                            builder.append(registrationDTO.RegistrationNumber);
                            builder.append(" - ");
                            builder.append(registrationDTO.RegistrationCouncilName);
                            //   builder.append(" , ");
                            builder.append("\n");
                        }

                    }

                    //MembershipData
                    List<DoctorMembershipDTO> membershipDTOs = new ArrayList<DoctorMembershipDTO>();
                    if (doctorDetailsDTO.doctorMembershipDTOs != null && doctorDetailsDTO.doctorMembershipDTOs.size() > 0) {
                        membershipDTOs.addAll(doctorDetailsDTO.doctorMembershipDTOs);
                        StringBuilder builder = new StringBuilder();
                        for (DoctorMembershipDTO membershipDTO : membershipDTOs) {
                            builder.append(membershipDTO.MembershipName);
                            builder.append(" - Since ");
                            builder.append(membershipDTO.MembershipSince);
                            //  builder.append(" , ");
                            builder.append("\n");
                        }

                    }

                    //clinic data
                /*List<DoctorClinicDTO> doctorClinicDTOs = new ArrayList<DoctorClinicDTO>();
                if (doctorDetailsDTO.doctorClinicDTOs != null && doctorDetailsDTO.doctorClinicDTOs.size() > 0) {
                    doctorClinicDTOs.addAll(doctorDetailsDTO.doctorClinicDTOs);
                    appContext.setDoctorClinicDTOs(doctorClinicDTOs);

                }*/

                    //pictureData
//                List<DoctorPicturesDTO> pictureDTOs = new ArrayList<DoctorPicturesDTO>();
//                if (doctorDetailsDTO.doctorPicturesDTOs != null && doctorDetailsDTO.doctorPicturesDTOs.size() > 0) {
//                    pictureDTOs.addAll(doctorDetailsDTO.doctorPicturesDTOs);
//                }

                    hideProgressDialog();

                    initTabView();

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

    public ProgressDialog mProgressDialog;

    protected void showProgressDialog() {
        mProgressDialog = new ProgressDialog(DoctorDetail.this);
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


    public void initView() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // linearLayoutBookAppointMent = (LinearLayout) findViewById(R.id.linearLayoutBookAppointMent);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        doctorImageView = (ImageView) findViewById(R.id.img_userPic);
        tvDoctorName = (TextView) findViewById(R.id.doctor_profile_name);
        tv_doctoreDegreeService = (TextView) findViewById(R.id.doctor_degree_cours_name);

        if (doctorDTO.imageSrcMobile != null) {
            try {
                byte[] decodedString = Base64.decode(doctorDTO.imageSrcMobile.getBytes(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                doctorImageView.setImageBitmap(decodedByte);

            } catch (Exception e) {
                doctorImageView.setImageResource(R.drawable.ic_action_profile);
            }
        } else {
            doctorImageView.setImageResource(R.drawable.ic_action_profile);
        }

        doctorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadPhoto(200, 200);

            }
        });
        tvDoctorName.setText(doctorDTO.DoctorName);

//        if (!Helper.hasValidValue(doctorDTO.Degree).matches("")) {
//            tv_doctoreDegreeService.setVisibility(View.VISIBLE);
//            tv_doctoreDegreeService.setText(doctorDTO.Degree);
//
//        } else {
//            tv_doctoreDegreeService.setVisibility(View.GONE);
//        }


        requestAppBtn = (Button) findViewById(R.id.requestAppointment);

        requestAppBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(DoctorDetail.this, ActivityClinicList.class);
//                intent.putExtra("DOCTORINFO", doctorDTO);
//                startActivity(intent);

            }
        });


    }

    private void toolbarInit() {
        topToolBar = (Toolbar) findViewById(R.id.tool_bar);
        topToolBar.setTitle("Doctor Info");
        topToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void loadPhoto(int width, int height) {

        byte[] decodedString = Base64.decode(doctorDTO.imageSrcMobile.getBytes(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //  doctorImageView.setImageBitmap(decodedByte);

        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        // LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        // View layout = inflater.inflate(R.layout.custom_fullimage_dialog,(ViewGroup)findViewById(R.id.layout_root));
        View layout = inflater.inflate(R.layout.custom_fullimage_dialog, null);

        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageBitmap(decodedByte);
        image.setImageDrawable(image.getDrawable());

        imageDialog.setView(layout);


        imageDialog.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>OK</font>"), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });


        imageDialog.create();
        AlertDialog a = imageDialog.create();
        a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        a.show();
    }

    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.progrm);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));
//                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
//                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }


    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    public void initTabView() {

        fragmentList.add(DoctorOverviewFragment.newInstance());


        tabTitles.add("Overview");


        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Setup the Tabs
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // This method ensures that tab selection events update the ViewPager and page changes update the selected tab.
        tabLayout.setupWithViewPager(viewPager);

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

//    private void navigateToHome() {
//        //  Intent intent = new Intent(DoctorDetail.this, ActivityDoctorList.class);
//        Intent intent = new Intent(DoctorDetail.this, ActivitySearch.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }


    @Override
    public void onTabAdded() {
        pagerAdapter.addTab(DoctorOverviewFragment.newInstance(), "Tab " + (tabTitles.size() + 1));
    }

    @Override
    public void onTabRemoved() {
        pagerAdapter.removeTab(1);
    }

    MyPagerAdapter pagerAdapter;

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return fragmentList.get(pos);
        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }

        // This is called when notifyDataSetChanged() is called. Without this, getItem() is not triggered
        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }

        public void addTab(Fragment fragment, String tabTitle) {
            fragmentList.add(fragment);
            tabTitles.add(tabTitle);
            notifyDataSetChanged();
        }

        public void removeTab(int tabPosition) {
            if (!fragmentList.isEmpty()) {
                fragmentList.remove(tabPosition);
                tabTitles.remove(tabPosition);
                notifyDataSetChanged();
            }
        }
    }
}