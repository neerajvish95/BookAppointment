package com.example.bookappointment.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookappointment.R;
import com.example.bookappointment.activity.DoctorDetail;
import com.example.bookappointment.model.DoctorDTO;
import com.example.bookappointment.utility.SessionManager;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private List<DoctorDTO> values;
    Activity activity;
    SessionManager sessionManager;

    public DoctorListAdapter(List<DoctorDTO> myDataset, Activity activity) {
        values = myDataset;
        this.activity = activity;
        sessionManager = new SessionManager(activity);
    }

    @Override
    public DoctorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.doctor_item_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final DoctorDTO doctorDTO = values.get(position);
        holder.doctorDTO = doctorDTO;
        holder.tvDoctorName.setText(doctorDTO.DoctorName);
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Bold.ttf");
        holder.tvDoctorName.setTypeface(face);
      /*  if (doctorDTO.Area != null && doctorDTO.City != null)
            holder.tvClinicAddress.setText(doctorDTO.Area + " , " + doctorDTO.City);*/
        if (doctorDTO.Clinics.matches("")) {
            holder.tvClinicAddress.setVisibility(View.INVISIBLE);
        } else {
            if (doctorDTO.Degree.matches("")) {
                holder.tvDoctorDegree.setVisibility(View.GONE);
            }
            String[] commaSeprator;
            if (doctorDTO.Clinics.contains(",")) {
                int counter = 0;
                for (int i = 0; i < doctorDTO.Clinics.length(); i++) {
                    if (doctorDTO.Clinics.charAt(i) == ',') {
                        counter++;
                    }
                }
                commaSeprator = doctorDTO.Clinics.split(",");
                holder.tvClinicAddress.setVisibility(View.VISIBLE);
                holder.tvClinicAddress.setText("" + commaSeprator[0] + "+ " + counter + " clinics");
            } else {
                holder.tvClinicAddress.setVisibility(View.VISIBLE);
                holder.tvClinicAddress.setText(doctorDTO.Clinics);
            }

        }

        if (doctorDTO.imageSrcMobile != null && doctorDTO.imageSrcMobile != "") {
            try {
                byte[] decodedString = Base64.decode(doctorDTO.imageSrcMobile.getBytes(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.ivDoctorImg.setImageBitmap(decodedByte);

            } catch (Exception e) {
                holder.ivDoctorImg.setImageResource(R.drawable.ic_action_profile);
            }
        } else {
            holder.ivDoctorImg.setImageResource(R.drawable.ic_action_profile);
        }

        if (String.valueOf(doctorDTO.Degree).matches("")) {
            holder.tvDoctorDegree.setVisibility(View.GONE);
        } else {
            holder.tvDoctorDegree.setVisibility(View.VISIBLE);
            holder.tvDoctorDegree.setText("" + doctorDTO.Degree);
        }

        if (String.valueOf(doctorDTO.YearsOfExperience).matches("") ||
                String.valueOf(doctorDTO.YearsOfExperience).matches("0")) {
            holder.tv_Year_exp.setVisibility(View.VISIBLE);
            holder.tv_Year_exp.setText("" + 2 + " Yr(s) Exp");

        } else {
            holder.tv_Year_exp.setVisibility(View.VISIBLE);
            holder.tv_Year_exp.setText("" + doctorDTO.YearsOfExperience + " Yr(s) Exp");
        }
        if (doctorDTO.ConsultationFees != null) {
            if (doctorDTO.ConsultationFees.matches("")) {
                holder.tvDoctorFee.setVisibility(View.VISIBLE);
                holder.tvDoctorFee.setText("150");
                holder.tvDoctorFee.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rupess_icon_updated, 0, 0, 0);

            } else {
                holder.tvDoctorFee.setVisibility(View.VISIBLE);
                holder.tvDoctorFee.setText(doctorDTO.ConsultationFees);
                holder.tvDoctorFee.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rupess_icon_updated, 0, 0, 0);
            }
        } else {
            holder.tvDoctorFee.setVisibility(View.VISIBLE);
            holder.tvDoctorFee.setText("");
            holder.tvDoctorFee.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vert_line, 0);
        }
        if (doctorDTO.Area.matches("")) {
            holder.tvDoctorArea.setVisibility(View.VISIBLE);
            holder.tvDoctorArea.setText("");
        } else {
            holder.tvDoctorArea.setVisibility(View.VISIBLE);
            holder.tvDoctorArea.setText(doctorDTO.Area);
        }
        /// android:drawableTop="@drawable/starrating_one"
        if (doctorDTO.AverageRating >= 0.0) {
            switch ((int) doctorDTO.AverageRating) {
                case 0:
                    holder.tvRatingBar.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.star_rating_updated_zero, 0, 0);
                    break;
                case 1:
                    holder.tvRatingBar.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.star_rating_update_one, 0, 0);
                    break;
                case 2:
                    holder.tvRatingBar.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.star_rating_updated_two, 0, 0);
                    break;
                case 3:
                    holder.tvRatingBar.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.star_rating_updated_three, 0, 0);
                    break;
                case 4:
                    holder.tvRatingBar.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.star_rating_updated_four, 0, 0);
                    break;
                case 5:
                    holder.tvRatingBar.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.star_rating_updated_five, 0, 0);
                    break;
            }
        }


        //if(doctorDTO.Review).matches("")){
        if (doctorDTO.Review == 0) {
        } else {
            holder.tvRatingBar.setText("\t" + doctorDTO.Review + " review(s)");
        }
        holder.main_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DoctorDetail.class);
                intent.putExtra("DOCTORINFO", doctorDTO);
                sessionManager.setDoctorDTO(doctorDTO);
                activity.startActivity(intent);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DoctorDetail.class);
                intent.putExtra("DOCTORINFO", doctorDTO);
                sessionManager.setDoctorDTO(doctorDTO);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public void add(int position, DoctorDTO item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout main_lyt;
        TextView tvDoctorName;
        TextView tvClinicAddress;
        ImageView ivDoctorImg;
        public ImageView ivBookAppoinment;
        RelativeLayout main;
        TextView tvDoctorFee;
        TextView tv_Year_exp;
        TextView tvDoctorDegree;
        TextView tvDoctorArea, tvBOOk;
        DoctorDTO doctorDTO;
        Button imageButton;
        TextView tvRatingBar;

        public ViewHolder(View v) {
            super(v);

            main_lyt = (LinearLayout) v.findViewById(R.id.main_lyt);
            tvDoctorName = (TextView) v.findViewById(R.id.doctor_name);
            tvClinicAddress = (TextView) v.findViewById(R.id.tv_clinics);
            ivDoctorImg = (ImageView) v.findViewById(R.id.imageView);
            tvDoctorDegree = (TextView) v.findViewById(R.id.doctor_degree);
            tvDoctorFee = (TextView) v.findViewById(R.id.fees);
            tv_Year_exp = (TextView) v.findViewById(R.id.tv_year_exp);
            tvDoctorArea = (TextView) v.findViewById(R.id.tvDoctorArea);
            imageButton = (Button) v.findViewById(R.id.btnBook);
            tvRatingBar = (TextView) v.findViewById(R.id.ratingBar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(activity, DoctorDetail.class);
                    intent.putExtra("DOCTORINFO", doctorDTO);
                    sessionManager.setDoctorDTO(doctorDTO);
                    activity.startActivity(intent);
                }
            });

        }
    }

}
