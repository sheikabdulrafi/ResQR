package com.resqr.lifesaver.controller;

import com.resqr.lifesaver.dto.PersonalDetails;
import com.resqr.lifesaver.model.GuardainModel;
import com.resqr.lifesaver.model.MedicalHistory;
import com.resqr.lifesaver.model.ResponseModel;
import com.resqr.lifesaver.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserDetailsController {
    @Autowired
    private UserService userService;

    @PostMapping("/verify")
    public ResponseEntity<ResponseModel> verifyToken(Principal principal){
        return userService.verifyToken(principal);
    }

    @PostMapping("/personal/update")
    public ResponseEntity<ResponseModel> updatePersonalDetails(@RequestBody PersonalDetails updatePersonal, Principal principal){
        return userService.updatePersonalDetails(updatePersonal, principal);
    }

    @PostMapping("/guardain/update")
    public ResponseEntity<ResponseModel> updateGuardains(@RequestBody List<GuardainModel> guardains, Principal principal){
        return userService.updateGuardains(guardains, principal);
    }

    @PostMapping("/medicalhistory/update")
    public ResponseEntity<ResponseModel> updateMediacalHistories(@RequestBody List<MedicalHistory> medicalHistories, Principal principal){
        return userService.updateMedicalHistories(medicalHistories, principal);
    }

}
