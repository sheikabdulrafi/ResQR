package com.resqr.lifesaver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeactivateQrCode {
    private Boolean isQrActive;
    private LocalDateTime qrReactivationTime;
}
