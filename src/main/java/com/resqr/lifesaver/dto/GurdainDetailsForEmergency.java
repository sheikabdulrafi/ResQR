package com.resqr.lifesaver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GurdainDetailsForEmergency {
    private String gurdainPhoneNumber;
    private String gurdainName;
    private String userName;
}
