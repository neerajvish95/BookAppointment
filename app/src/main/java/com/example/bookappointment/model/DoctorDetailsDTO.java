package com.example.bookappointment.model;

import java.io.Serializable;
import java.util.List;


public class DoctorDetailsDTO  implements Serializable {

    public DoctorDTO doctorDTO;

    public List<DoctorRegistrationDTO> doctorRegistrationDTOs;

    public List<DoctorAwardDTO> doctorAwardDTOs;

    public List<DoctorServiceDTO> doctorServiceDTOs;

    public List<DoctorEducatioDTO> doctorEducatioDTOs;

    public List<DoctorPicturesDTO> doctorPicturesDTOs;

    public List<DoctorMembershipDTO> doctorMembershipDTOs;

    public List<DoctorTimingDTO> doctorTimingDTOs;

    public List<DoctorExperienceDTO> doctorExperienceDTOs;

    public List<DoctorClinicDTO> doctorClinicDTOs;
}
