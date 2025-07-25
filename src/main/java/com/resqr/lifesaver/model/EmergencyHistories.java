package com.resqr.lifesaver.model;

import com.resqr.lifesaver.dto.GuardainResponses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmergencyHistories {

    @Id
    @Builder.Default
    private String historyId = UUID.randomUUID().toString();
    private String passerByPhoneNumber;
    private String userId;
    @Builder.Default
    String tokenForPasserBy = UUID.randomUUID().toString();

    @Builder.Default
    private LocalDateTime scannedTime = LocalDateTime.now();
    private Boolean isQrOnline;
    private String message;

    private List<GuardainResponses> guardainResponses;
}
