package com.Mohan.HospitalService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;
    @Length(min = 3, message = "Name should have atleast 3 characters")
    private String doctorName;
    @Length(max = 20, message = "City name can't be more than 20 characters")
    private String doctorCity;
    @Email
    private String doctorEmail;
    @Length(min = 10, message = "Enter a valid Phone Number")
    private String doctorPhoneNumber;
    private String doctorSpeciality;


}
