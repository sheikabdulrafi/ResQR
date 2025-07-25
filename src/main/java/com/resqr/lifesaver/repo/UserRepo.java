package com.resqr.lifesaver.repo;

import com.resqr.lifesaver.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepo extends MongoRepository<UserModel, String> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    UserModel findByEmail(String email);

    @Query("{ 'isQrActive': false, 'qrReactivationTime': { $lte: ?0 } }")
    List<UserModel> findInactiveUsersDueForReactivation(LocalDateTime now);
}
