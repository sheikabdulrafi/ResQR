package com.resqr.lifesaver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Users")
public class UserModel {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;

    private String password;
    private String jwt;

    private Boolean hasGeneratedQR;
    private Boolean isQrActive;
    private LocalDateTime qrReactivationTime;

    private List<GuardainModel> guardains;
    private List<MedicalHistory> medicalHistories;

}
