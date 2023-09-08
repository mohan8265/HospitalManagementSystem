package com.Mohan.HospitalService.controllers;

import com.Mohan.HospitalService.models.Doctor;
import com.Mohan.HospitalService.models.Patient;
import com.Mohan.HospitalService.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/patient")
    public ResponseEntity<String> addPatient(@RequestBody @Valid Patient patient){
        return patientService.addPatient(patient);
    }
    @GetMapping("/patients")
    public List<Patient> getPatients(){
        return patientService.getPatients();
    }
    @GetMapping("patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer id){
        return patientService.getPatientById(id);
    }
    @GetMapping("/suggested-doctor/{id}")
    public List<Doctor> getSuggestedDoctor(@PathVariable Integer id){
        return patientService.getSuggestedDoctor(id);
    }

    @PutMapping("/patient/{id}")
    public ResponseEntity<String> updateById(@PathVariable Integer id,@RequestBody @Valid Patient patient){
        return patientService.updateById(id,patient);
    }
    @DeleteMapping("/patient/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        return patientService.deleteById(id);
    }
}
