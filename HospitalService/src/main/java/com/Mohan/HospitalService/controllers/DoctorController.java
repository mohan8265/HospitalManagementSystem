package com.Mohan.HospitalService.controllers;

import com.Mohan.HospitalService.models.Doctor;
import com.Mohan.HospitalService.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/doctor")
    public ResponseEntity<String> addDoctor(@RequestBody @Valid Doctor doctor){
        return doctorService.addDoctor(doctor);
    }
    @GetMapping("/doctors")
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }
    @PutMapping("doctor/{id}")
    public ResponseEntity<String> updateById(@PathVariable Integer id,@RequestBody @Valid Doctor doctor){
        return doctorService.updateById(id,doctor);
    }
    @DeleteMapping("doctor/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        return doctorService.deleteById(id);
    }
}
