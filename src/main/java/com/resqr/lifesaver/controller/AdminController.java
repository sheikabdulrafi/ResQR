package com.resqr.lifesaver.controller;

import com.resqr.lifesaver.model.EmergencyHistories;
import com.resqr.lifesaver.model.ResponseModel;
import com.resqr.lifesaver.service.AdminService;
import com.resqr.lifesaver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/emergency")
    public ResponseEntity<ResponseModel> addEmergencyHistory(@RequestBody EmergencyHistories emergency, Principal principal){
        return adminService.addEmergencyHistory(emergency, principal);
    }
}
