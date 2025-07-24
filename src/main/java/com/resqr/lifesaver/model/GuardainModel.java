package com.resqr.lifesaver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuardainModel {
    @Id
    @Builder.Default
    private String guardainId = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String email;
    private String phoneNumber;
    private Boolean isPrimary;
    private Boolean hasPdfAccess;
}
