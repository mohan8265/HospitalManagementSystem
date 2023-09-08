package com.Mohan.HospitalService.services;

import com.Mohan.HospitalService.models.Doctor;
import com.Mohan.HospitalService.models.Patient;
import com.Mohan.HospitalService.repositories.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private DoctorService doctorService;

    public ResponseEntity<String> addPatient(Patient patient) {
        String city = patient.getPatientCity().toLowerCase();
        String symptom = patient.getPatientSymptom().toLowerCase();

        if(isCitValid(city)){
            if(isSymptomValid(symptom)){
                patientRepo.save(patient);
                return new ResponseEntity<>("patient added successfully", HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>("There isn’t any doctor present at your location for your symptom", HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("We are still waiting to expand to your location",HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isCitValid(String city){
        if(!(city.equals("delhi") || city.equals("noida") || city.equals("faridabad"))){
            return false;
        }else {
            return true;
        }
    }

    private boolean isSymptomValid(String symptom){
        if(!(symptom.equals("arthritis") || symptom.equals("back pain") ||symptom.equals("tissue injuries") ||
                symptom.equals("dysmenorrhea") || symptom.equals("skin infection") ||symptom.equals("skin burn") ||symptom.equals("ear pain"))){
            return false;
        }else {
            return true;
        }
    }

    public List<Patient> getPatients() {
        return patientRepo.findAll();
    }


    public ResponseEntity<Patient> getPatientById(Integer id) {
        Patient patient = patientRepo.findById(id).orElse(null);
        if(patient == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(patient,HttpStatus.OK);
        }
    }

    public List<Doctor> getSuggestedDoctor(Integer id) {

        if (!patientRepo.existsById(id)){
            throw new IllegalStateException("not a valid id");
        }
        Patient existingPatient = patientRepo.findById(id).get();

        String symptoms = existingPatient.getPatientSymptom().toLowerCase();
        List<Doctor> doctorList = doctorService.getDoctors();
        List<Doctor> ans = new ArrayList<>();

        if(symptoms.equals("arthritis") || symptoms.equals("back pain") || symptoms.equals("tissue injuries")){
            for(Doctor doctor:doctorList){
                if(doctor.getDoctorSpeciality().toLowerCase().equals("orthopedic")){
                    ans.add(doctor);
                }
            }
        }

        else if(symptoms.equals("dysmenorrhea")){
            for(Doctor doctor:doctorList){
                if(doctor.getDoctorSpeciality().toLowerCase().equals("gynecology")){
                    ans.add(doctor);
                }
            }
        }

         else if(symptoms.equals("skin infection") || symptoms.equals("skin burn")){
            for(Doctor doctor:doctorList){
                if(doctor.getDoctorSpeciality().toLowerCase().equals("dermatology")){
                    ans.add(doctor);
                }
            }
        }

         else if(symptoms.equals("ear pain")){
            for(Doctor doctor:doctorList){
                if(doctor.getDoctorSpeciality().toLowerCase().equals("ent")){
                    ans.add(doctor);
                }
            }
        }
         return ans;
    }

    public ResponseEntity<String> updateById(Integer id, Patient patient) {
        if(!patientRepo.existsById(id)){
            return new ResponseEntity<>("given id doesn't exist",HttpStatus.NOT_FOUND);
        }

        Patient existingPatient = patientRepo.findById(id).get();

        if(patient.getPatientCity() != null){
            if(isCitValid(patient.getPatientCity())){
                existingPatient.setPatientCity(patient.getPatientCity());
            }else {
                return new ResponseEntity<>("We are still waiting to expand to your location",HttpStatus.BAD_REQUEST);
            }
        }
        if (patient.getPatientSymptom() != null){
            if(isSymptomValid(patient.getPatientSymptom())){
                existingPatient.setPatientSymptom(patient.getPatientSymptom());
            }else {
                return new ResponseEntity<>("There isn’t any doctor present at your location for your symptom", HttpStatus.BAD_REQUEST);
            }
        }
        if(patient.getPatientName() != null){
            existingPatient.setPatientName(patient.getPatientName());
        }
        if(patient.getPatientEmail() != null){
            existingPatient.setPatientEmail(patient.getPatientEmail());
        }
        if(patient.getPatientPhoneNumber() != null){
            existingPatient.setPatientPhoneNumber(patient.getPatientPhoneNumber());
        }

        patientRepo.save(existingPatient);
        return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Integer id) {
        if(!patientRepo.existsById(id)){
            return new ResponseEntity<>("provide a valid id",HttpStatus.NOT_FOUND);
        }else {
            patientRepo.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }
    }
}
