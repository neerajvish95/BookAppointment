package com.example.bookappointment.Adapter;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookappointment.R;
import com.example.bookappointment.model.AutoCompleteResultDTO;
import com.example.bookappointment.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class AdapterCityList extends BaseAdapter {
    private List<AutoCompleteResultDTO> doctorlist;
    private List<AutoCompleteResultDTO> mFilteredData = new ArrayList<>();

    private Activity context;
    private EditText editText;
    private AlertDialog dialog;
    private SessionManager sessionManager;

    public AdapterCityList(SessionManager a, Activity context, List<AutoCompleteResultDTO> exInfo, AlertDialog mdialog, EditText editText) {
        this.context = context;
        this.doctorlist = exInfo;
        this.dialog = mdialog;
        this.editText = editText;
        this.sessionManager = a;
    }

    @Override
    public int getCount() {
        return doctorlist.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorlist.get(position);
    }


    @Override
    public long getItemId(int position) {
        return doctorlist.indexOf(position);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderExam holderExam = null;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.title_row, null);
            holderExam = new ViewHolderExam();
            holderExam.textView_DrName = (TextView) convertView.findViewById(R.id.textView_name);
            convertView.setTag(holderExam);
        } else {
            holderExam = (ViewHolderExam) convertView.getTag();
        }
        if (doctorlist != null) {
            if (!doctorlist.isEmpty()) {
                AutoCompleteResultDTO doctor = (AutoCompleteResultDTO) getItem(position);
                if (doctor != null) {
                    //  if(holderExam.textView_DrName != null)
                    holderExam.textView_DrName.setText(doctor.Text);
                }
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteResultDTO doctor = (AutoCompleteResultDTO) getItem(position);
                editText.setText(doctor.Text);
                dialog.dismiss();
               /* ((AppContext) globalcontext).setProviderID(doctor.ProviderID);
                ((AppContext) globalcontext).setProviderName(doctor.ProviderName);*/
                // drawerLayout.closeDrawer(lv);
            }
        });
        return convertView;
    }

    private class ViewHolderExam {
        private TextView textView_DrName;
    }


    public List<AutoCompleteResultDTO> filterlist(String name) {

        doctorlist.size();

        mFilteredData.clear();
        if (name.length() == 0) {
            mFilteredData.addAll(doctorlist);
        } else {
            for (AutoCompleteResultDTO dto : doctorlist) {
                if ((dto.Text.toLowerCase()).contains(name)) {
                    mFilteredData.add(dto);
                }
            }
        }
        notifyDataSetChanged();

        return mFilteredData;
    }

}
