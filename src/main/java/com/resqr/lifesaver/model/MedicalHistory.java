package com.resqr.lifesaver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalHistory {
    private String condition;
    private String medication;
    private String doctorName;
    private String hospitalName;
    private String hospitalContact;
    private String notes;
}
