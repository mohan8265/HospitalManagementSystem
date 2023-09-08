package com.Mohan.HospitalService.services;

import com.Mohan.HospitalService.models.Doctor;
import com.Mohan.HospitalService.repositories.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepo doctorRepo;


    public ResponseEntity<String> addDoctor(Doctor doctor) {
        String city = doctor.getDoctorCity().toLowerCase();
        String speciality = doctor.getDoctorSpeciality().toLowerCase();

        if(isCityValid(city)){
            if(isSpecialityValid(speciality)){
                doctorRepo.save(doctor);
                return new ResponseEntity<>("Doctor added successfully", HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>("We are not dealing with this specialization",HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>("Doctor city should be Delhi, Noida or Faridabad",HttpStatus.BAD_REQUEST);
        }
    }
    public boolean isCityValid(String city){
        if(!(city.equals("delhi") || city.equals("noida") || city.equals("faridabad"))){
            return false;
        }else {
            return true;
        }
    }
    public boolean isSpecialityValid(String speciality){
        if(!(speciality.equals("orthopedic") || speciality.equals("gynecology") || speciality.equals("dermatology")
        || speciality.equals("ent"))){
            return false;
        }else {
            return true;
        }
    }

    public List<Doctor> getDoctors() {
        return doctorRepo.findAll();
    }

    public ResponseEntity<String> updateById(Integer id, Doctor doctor) {
        if(!doctorRepo.existsById(id)){
            return new ResponseEntity<>("Not a valid id ",HttpStatus.NOT_FOUND);
        }
        Doctor existingDoctor = doctorRepo.findById(id).get();


        if(doctor.getDoctorCity() != null){
            if(isCityValid(doctor.getDoctorCity())){
                existingDoctor.setDoctorCity(doctor.getDoctorCity());
            }else {
                return new ResponseEntity<>("Doctor city should be Delhi, Noida or Faridabad",HttpStatus.BAD_REQUEST);
            }
        }

        if(doctor.getDoctorSpeciality() != null){
            if(isSpecialityValid(doctor.getDoctorSpeciality())){
                existingDoctor.setDoctorSpeciality(doctor.getDoctorSpeciality());
            }else{
                return new ResponseEntity<>("We are not dealing with this specialization",HttpStatus.BAD_REQUEST);
            }
        }

        if(doctor.getDoctorName() != null){
            existingDoctor.setDoctorName(doctor.getDoctorName());
        }

        if(doctor.getDoctorEmail() != null){
            existingDoctor.setDoctorEmail(doctor.getDoctorEmail());
        }

        if(doctor.getDoctorPhoneNumber() != null){
            existingDoctor.setDoctorPhoneNumber(doctor.getDoctorPhoneNumber());
        }
        doctorRepo.save(existingDoctor);
        return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Integer id) {
        if(!doctorRepo.existsById(id)){
            return new ResponseEntity<>("Id doesn't exist",HttpStatus.NOT_FOUND);
        }

        doctorRepo.deleteById(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }
}
