package com.resqr.lifesaver.service;

import com.resqr.lifesaver.dto.GurdainDetailsForEmergency;
import com.resqr.lifesaver.model.EmergencyHistories;
import com.resqr.lifesaver.model.ResponseModel;
import com.resqr.lifesaver.model.UserModel;
import com.resqr.lifesaver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;


    public ResponseEntity<ResponseModel> addEmergencyHistory(EmergencyHistories emergency, Principal principal) {
        if (emergency == null || emergency.getUserId() == null) {
            return ResponseEntity.badRequest().body(ResponseModel.error("Invalid emergency data"));
        }

        UserModel user = userRepo.findById(emergency.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseModel.error("User not found"));
        }

        if (!user.getHasGeneratedQR()) {
            return ResponseEntity.badRequest().body(ResponseModel.error("The user has not generated a QR code"));
        }

        if (!user.getIsQrActive()) {
            return ResponseEntity.badRequest().body(ResponseModel.error("The QR is currently disabled"));
        }

        // Append to existing emergency history list
        List<EmergencyHistories> existing = user.getEmergencyHistories();
        if (existing == null) {
            user.setEmergencyHistories(new ArrayList<EmergencyHistories>(List.of(emergency)));
        } else {
            existing.add(emergency);
            user.setEmergencyHistories(existing);
        }

        userRepo.save(user);

        //Extract Guardains Phone Numbers
        List<GurdainDetailsForEmergency> contacts = user.getGuardains().stream()
                .map(g -> GurdainDetailsForEmergency.builder()
                        .gurdainPhoneNumber(g.getPhoneNumber())
                        .gurdainName(g.getFirstName() + " " + g.getLastName())
                        .userName(user.getFirstName() + " " + user.getLastName())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ResponseModel.success("Emergency history added successfully", contacts));
    }



}
