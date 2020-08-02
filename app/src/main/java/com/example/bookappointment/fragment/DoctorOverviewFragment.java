package com.example.bookappointment.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookappointment.R;
import com.example.bookappointment.model.DoctorAwardDTO;
import com.example.bookappointment.model.DoctorDTO;
import com.example.bookappointment.model.DoctorEducatioDTO;
import com.example.bookappointment.model.DoctorExperienceDTO;
import com.example.bookappointment.model.DoctorServiceDTO;
import com.example.bookappointment.utility.SessionManager;
import com.example.bookappointment.utility.TabsListener;

import java.util.ArrayList;
import java.util.List;


public class DoctorOverviewFragment extends Fragment implements TabsListener {
    private TabsListener tabsListener;
    public DoctorDTO receivedDTO;
    public LinearLayout ll_experience, ll_Award, ll_Services, ll_Education;
    public CardView cv_experience, cv_Services, cv_Award, cc_Education;
    SessionManager sessionManager;


    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();

    public static DoctorOverviewFragment newInstance() {
        return new DoctorOverviewFragment();
    }

    public DoctorOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof TabsListener) {
            tabsListener = (TabsListener) context;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.doctor_overview, container, false);

        sessionManager = new SessionManager(getActivity());

        receivedDTO = sessionManager.getDoctorDTO();
        initViewSetData(view);

        return view;
    }

    public void initViewSetData(View view) {

        cv_experience = (CardView) view.findViewById(R.id.cv_experience);
        cv_Services = (CardView) view.findViewById(R.id.cv_Services);
        cc_Education = (CardView) view.findViewById(R.id.cv_Education);
        cv_Award = (CardView) view.findViewById(R.id.cv_Award);

        ll_experience = (LinearLayout) view.findViewById(R.id.ll_experience);
        ll_Award = (LinearLayout) view.findViewById(R.id.ll_Award);
        ll_Services = (LinearLayout) view.findViewById(R.id.ll_Services);
        ll_Education = (LinearLayout) view.findViewById(R.id.ll_Education);


        if (sessionManager.getDetailDoctorAwards() == null) {
            cv_Award.setVisibility(View.GONE);

        } else {
            cv_Award.setVisibility(View.VISIBLE);

            List<DoctorAwardDTO> doctorAwardDTOS = sessionManager.getDetailDoctorAwards();

            for (DoctorAwardDTO doctorAwardDTO : doctorAwardDTOS) {
                View child = getLayoutInflater().inflate(R.layout.text_bullet_row, null);
                TextView text = child.findViewById(R.id.text);
                TextView subtext = child.findViewById(R.id.subtext);

                text.setText(doctorAwardDTO.AwardName);
                subtext.setText(doctorAwardDTO.AwardDescription + " - " + doctorAwardDTO.YearofAward);
                ll_Award.addView(child);
            }

        }
        if (sessionManager.getDetailDoctorExperience() == null) {
            cv_experience.setVisibility(View.GONE);

        } else {
            cv_experience.setVisibility(View.VISIBLE);

            List<DoctorExperienceDTO> doctorExperienceDTOS = sessionManager.getDetailDoctorExperience();

            for (DoctorExperienceDTO doctorExperienceDTO : doctorExperienceDTOS) {
                View child = getLayoutInflater().inflate(R.layout.text_bullet_row, null);
                TextView text = child.findViewById(R.id.text);
                TextView subtext = child.findViewById(R.id.subtext);

                text.setText(doctorExperienceDTO.RoleName);
                subtext.setText(doctorExperienceDTO.OrganizationName + " : " + doctorExperienceDTO.FromYear + " - " + doctorExperienceDTO.ToYear);
                ll_experience.addView(child);
            }

        }
        if (sessionManager.getDetailDoctorEducation() == null) {
            cc_Education.setVisibility(View.GONE);

        } else {
            cc_Education.setVisibility(View.VISIBLE);

            List<DoctorEducatioDTO> doctorEducatioDTOS = sessionManager.getDetailDoctorEducation();

            for (DoctorEducatioDTO doctorEducatioDTO : doctorEducatioDTOS) {
                View child = getLayoutInflater().inflate(R.layout.text_bullet_row, null);
                TextView text = child.findViewById(R.id.text);
                TextView subtext = child.findViewById(R.id.subtext);

                text.setText(doctorEducatioDTO.DegreeName);
                subtext.setText(doctorEducatioDTO.CollegeName + " - " + doctorEducatioDTO.YearOfPassing);
                ll_Education.addView(child);
            }

        }
        if (sessionManager.getDetailDoctorServices() == null) {
            cv_Services.setVisibility(View.GONE);

        } else {
            cv_Services.setVisibility(View.VISIBLE);

            List<DoctorServiceDTO> doctorServiceDTOS = sessionManager.getDetailDoctorServices();

            for (DoctorServiceDTO doctorServiceDTO : doctorServiceDTOS) {
                View child = getLayoutInflater().inflate(R.layout.text_bullet_row, null);
                TextView text = child.findViewById(R.id.text);
                TextView subtext = child.findViewById(R.id.subtext);
                TextView iii = child.findViewById(R.id.iii);

                text.setText(doctorServiceDTO.ServiceName);
                subtext.setVisibility(View.GONE);
                iii.setVisibility(View.GONE);
                ll_Services.addView(child);
            }

        }

    }


    MapPagerAdapter pagerAdapter;

    public class MapPagerAdapter extends FragmentStatePagerAdapter {

        public MapPagerAdapter(FragmentManager fm) {
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


    @Override
    public void onTabAdded() {
        pagerAdapter.addTab(DoctorOverviewFragment.newInstance(), "Tab " + (tabTitles.size() + 1));
    }

    @Override
    public void onTabRemoved() {
        pagerAdapter.removeTab(1);
    }


}