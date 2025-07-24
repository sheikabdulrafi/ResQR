package com.resqr.lifesaver.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalDetails {
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;
}
