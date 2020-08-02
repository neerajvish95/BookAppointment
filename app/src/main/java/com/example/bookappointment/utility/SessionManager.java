package com.example.bookappointment.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bookappointment.model.DoctorAwardDTO;
import com.example.bookappointment.model.DoctorDTO;
import com.example.bookappointment.model.DoctorEducatioDTO;
import com.example.bookappointment.model.DoctorExperienceDTO;
import com.example.bookappointment.model.DoctorServiceDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SessionManager {

    private SharedPreferences mPref;
    private Context mContext;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SessionInfo";
    private static final String isLoggedIn = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_SID = "sid";
    public static final String KEY_FBTOKEN = "fbToken";
    private static final String isFBLogIn = "fbLogIn";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_PWD = "password";
    private static final String KEY_CONNECTED_HOST = "ConnectedHost";
    private static final String KEY_FBID = "fbId";
    private static final String KEY_FirstTime = "firsttime";
    private static final String KEY_APPLICATIONDTO = "applicationDTO";
    private static SharedPreferences.Editor editor;

    public SessionManager(Context context) {

        mContext = context;
        mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = mPref.edit();
    }

    public void createLoginSession(String name, String password, boolean isFirst) {
        logoutUser();
        Gson gson = new Gson();
        String json = gson.toJson(password);

        SharedPreferences.Editor editorLogin = mPref.edit();
        editorLogin.putBoolean(isLoggedIn, true);
        editorLogin.putString(KEY_NAME, name);
        editorLogin.putString(KEY_PWD, password);
        editorLogin.putString(KEY_APPLICATIONDTO, json);
        editorLogin.commit();
    }

    public String getFbToken() {

        return mPref.getString(KEY_FBTOKEN, "");
    }

    public boolean getIsFirstTime() {
        return mPref.getBoolean(KEY_FirstTime, false);
    }

    public long getExpireTime() {

        return mPref.getLong(KEY_EXPIRES, -1);
    }

    public String getSid() {

        return mPref.getString(KEY_SID, "");
    }

    public void setSID(String sid) {

        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(KEY_SID, sid);
        editor.commit();
    }

    public String getPassword() {

        return mPref.getString(KEY_PWD, "");
    }

    public String getName() {

        return mPref.getString(KEY_NAME, "");
    }

    public String getHost() {

        return mPref.getString(KEY_CONNECTED_HOST, "");
    }

    public String getEmail() {

        return mPref.getString(KEY_EMAIL, "");
    }

    public void logoutUser() {

        SharedPreferences.Editor editor = mPref.edit();
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {

        return mPref.getBoolean(isLoggedIn, false);
    }

    public String getApplication() {
        return mPref.getString(KEY_APPLICATIONDTO, "");
    }

    public <T> void setObject(String key, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        set(key, json);
    }

    public void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public Object getList(String key) {

        return mPref.getString(key, "");
    }


    //-------------------------------


    public DoctorDTO getDoctorDTO() {

        Gson gson = new Gson();
        String json = mPref.getString("DoctorDTO", "");

        return gson.fromJson(json, DoctorDTO.class);
    }

    public void setDoctorDTO(DoctorDTO detailDoctorEducation) {

        Gson gson = new Gson();
        String json = gson.toJson(detailDoctorEducation);
        editor.putString("DoctorDTO", json);
        editor.commit();


    }
    //-------------------------------


    public List<DoctorServiceDTO> getDetailDoctorServices() {

        Gson gson = new Gson();
        String json = mPref.getString("ServiceDTO", "");

        Type type = new TypeToken<List<DoctorServiceDTO>>() {
        }.getType();
        List<DoctorServiceDTO> students = gson.fromJson(json, type);

        return students;
    }

    public void setDetailDoctorServices(List<DoctorServiceDTO> detailDoctorEducation) {

        Gson gson = new Gson();
        String json = gson.toJson(detailDoctorEducation);
        editor.putString("ServiceDTO", json);
        editor.commit();


    }

//-------------------------------


    public List<DoctorAwardDTO> getDetailDoctorAwards() {

        Gson gson = new Gson();
        String json = mPref.getString("DoctorAwards", "");

        Type type = new TypeToken<List<DoctorAwardDTO>>() {
        }.getType();
        List<DoctorAwardDTO> students = gson.fromJson(json, type);

        return students;
    }

    public void setDetailDoctorAwards(List<DoctorAwardDTO> detailDoctorEducation) {

        Gson gson = new Gson();
        String json = gson.toJson(detailDoctorEducation);
        editor.putString("DoctorAwards", json);
        editor.commit();


    }

//    ------------------------------


    public List<DoctorEducatioDTO> getDetailDoctorEducation() {

        Gson gson = new Gson();
        String json = mPref.getString("DoctorEducation", "");

        Type type = new TypeToken<List<DoctorEducatioDTO>>() {
        }.getType();
        List<DoctorEducatioDTO> students = gson.fromJson(json, type);

        return students;
    }

    public void setDetailDoctorEducation(List<DoctorEducatioDTO> detailDoctorEducation) {

        Gson gson = new Gson();
        String json = gson.toJson(detailDoctorEducation);
        editor.putString("DoctorEducation", json);
        editor.commit();


    }
//    ------------------------------


    public List<DoctorExperienceDTO> getDetailDoctorExperience() {

        Gson gson = new Gson();
        String json = mPref.getString("DoctorExperience", "");

        Type type = new TypeToken<List<DoctorExperienceDTO>>() {
        }.getType();
        List<DoctorExperienceDTO> students = gson.fromJson(json, type);

        return students;
    }

    public void setDetailDoctorExperience(List<DoctorExperienceDTO> detailDoctorEducation) {

        Gson gson = new Gson();
        String json = gson.toJson(detailDoctorEducation);
        editor.putString("DoctorExperience", json);
        editor.commit();


    }


}
