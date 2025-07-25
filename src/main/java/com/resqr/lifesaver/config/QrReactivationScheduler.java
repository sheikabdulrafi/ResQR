package com.resqr.lifesaver.config;

import com.resqr.lifesaver.model.UserModel;
import com.resqr.lifesaver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class QrReactivationScheduler {

    @Autowired
    private UserRepo userRepo;

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void reactivateQRs() {
        LocalDateTime now = LocalDateTime.now();

        // Fetch only relevant users
        List<UserModel> users = userRepo.findInactiveUsersDueForReactivation(now);

        for (UserModel user : users) {
            user.setIsQrActive(true);
            user.setQrReactivationTime(null);
            userRepo.save(user);
        }
    }
}