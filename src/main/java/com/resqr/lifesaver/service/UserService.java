package com.resqr.lifesaver.service;

import com.resqr.lifesaver.dto.LoginDTO;
import com.resqr.lifesaver.dto.PersonalDetails;
import com.resqr.lifesaver.model.GuardainModel;
import com.resqr.lifesaver.model.MedicalHistory;
import com.resqr.lifesaver.model.UserModel;
import com.resqr.lifesaver.model.ResponseModel;
import com.resqr.lifesaver.repo.UserRepo;
import com.resqr.lifesaver.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<ResponseModel> registerUser(UserModel userModel) {
        if (userModel.getEmail() == null || userModel.getPhoneNumber() == null || userModel.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.error("Email, Phone Number, and Password are required."));
        }

        if (userRepo.existsByEmail(userModel.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseModel.error("Email already exists."));
        }

        if (userRepo.existsByPhoneNumber(userModel.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseModel.error("Phone number already exists."));
        }

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        UserModel savedUser = userRepo.save(userModel);
        savedUser.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseModel.success("User registered successfully.", savedUser));
    }

    public ResponseEntity<ResponseModel> loginUser(LoginDTO login, HttpServletResponse response) {
        if (login.getEmail() == null || login.getEmail().isBlank() || login.getPassword() == null || login.getPassword().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.error("All fields Required."));
        }

        if(!userRepo.existsByEmail(login.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.error("Invalid email or password."));
        }

        UserModel user = userRepo.findByEmail(login.getEmail());

        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.error("Invalid email or password."));
        }
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        // ✅ Set JWT as HttpOnly cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);

        response.addCookie(cookie);

        // ✅ Send basic user info (without password or token)
        user.setPassword(null);
        user.setJwt(null); // optional: don't send back
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseModel.success("Login successful", user));
    }

    public ResponseEntity<ResponseModel> updatePersonalDetails(PersonalDetails newDetails, Principal principal) {
        String id = principal.getName();

        UserModel user = userRepo.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseModel.error("User not found"));
        }

        if(newDetails.getFirstName() == null || newDetails.getLastName() == null || newDetails.getGender() == null || newDetails.getEmail() == null ||
        newDetails.getPhoneNumber() == null || newDetails.getDateOfBirth() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.error("All fileds required"));
        }

        user.setFirstName(newDetails.getFirstName());
        user.setLastName(newDetails.getLastName());
        user.setGender(newDetails.getGender());
        user.setEmail(newDetails.getEmail());
        user.setPhoneNumber(newDetails.getPhoneNumber());
        user.setDateOfBirth(newDetails.getDateOfBirth());

        user = userRepo.save(user);
        user.setPassword(null);
        user.setJwt(null);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseModel.success("Personal details updated successfully", user));
    }

    public ResponseEntity<ResponseModel> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Deletes cookie immediately
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseModel.success("Logged out successfully"));
    }

    public ResponseEntity<ResponseModel> verifyToken(Principal principal) {
        String id = principal.getName();

        UserModel user = userRepo.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseModel.error("Invalid or expired token"));
        }

        user.setPassword(null);
        user.setJwt(null);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseModel.success("Valid Token", user));
    }

    public ResponseEntity<ResponseModel> updateGuardains(List<GuardainModel> newguardains, Principal principal) {
        String id = principal.getName();

        UserModel user = userRepo.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseModel.error("Invalid or expired token"));
        }

        if (newguardains == null || newguardains.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseModel.error("Guardian list cannot be empty"));
        }

        int primary = 0, pdf = 0;
        for(GuardainModel newGurdain:newguardains){
            if(newGurdain.getIsPrimary()){
                primary++;
            } if (newGurdain.getHasPdfAccess()){
                pdf++;
            }
        }

        if(primary < 1 || pdf < 1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.error("Atleast one or more must have primary / pdf access"));
        }


        user.setGuardains(newguardains);
        UserModel updatedUser = userRepo.save(user);
        updatedUser.setPassword(null);
        updatedUser.setJwt(null);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseModel.success("Guardinas updated Successfully", updatedUser));
    }

    public ResponseEntity<ResponseModel> updateMedicalHistories(List<MedicalHistory> medicalHistories, Principal principal) {
        String id = principal.getName();

        UserModel user = userRepo.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseModel.error("Token is invalid / expired"));
        }

        if(medicalHistories == null || medicalHistories.isEmpty()){
            return ResponseEntity.badRequest().body(ResponseModel.error("Required all fileds"));
        }

        for(MedicalHistory medicalHistory: medicalHistories){
            if(medicalHistory.getMedication() == null || medicalHistory.getMedication().isBlank()
            || medicalHistory.getCondition() == null || medicalHistory.getCondition().isBlank()
            || medicalHistory.getDoctorName() == null || medicalHistory.getDoctorName().isBlank()
            || medicalHistory.getHospitalName() ==null || medicalHistory.getHospitalName().isBlank()
            || medicalHistory.getHospitalContact() == null || medicalHistory.getHospitalContact().isBlank()
            || medicalHistory.getNotes() == null || medicalHistory.getNotes().isBlank()){
                return ResponseEntity.badRequest().body(ResponseModel.error("Required all fileds"));
            }
        }
        user.setMedicalHistories(medicalHistories);
        UserModel updatedUser = userRepo.save(user);
        updatedUser.setPassword(null);
        updatedUser.setJwt(null);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseModel.success("Medical History updated Successfully", updatedUser));
    }
}
