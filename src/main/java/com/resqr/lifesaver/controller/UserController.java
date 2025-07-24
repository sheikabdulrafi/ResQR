package com.resqr.lifesaver.controller;

import com.resqr.lifesaver.dto.LoginDTO;
import com.resqr.lifesaver.dto.PersonalDetails;
import com.resqr.lifesaver.model.UserModel;
import com.resqr.lifesaver.model.ResponseModel;
import com.resqr.lifesaver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Principal;


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> registerUser(@RequestBody UserModel userModel) {
        System.out.println(userModel);
        return userService.registerUser(userModel);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseModel> loginUser(@RequestBody LoginDTO login, HttpServletResponse response){
        return userService.loginUser(login, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseModel> logout(HttpServletResponse response) {
        return userService.logout(response);
    }

}
