package com.example.bookappointment.model;

import java.io.Serializable;


public class DoctorPicturesDTO  implements Serializable
         {
    public String DoctorPhotoID;

    public String DenteeDoctorGUID;

    public String Title;

    public String Description;

    public String Tags;

    public int DocumentID;

    public String PicturePath;

    public boolean IsDeleted;

    public String LastUpdatedOn;

    public String LastUpdatedBy;


    public byte[] FileContent;


    public String FileContentBaseString;

    public String UploadedFileName;

}
