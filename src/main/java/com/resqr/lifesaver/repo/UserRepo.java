package com.resqr.lifesaver.repo;

import com.resqr.lifesaver.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<UserModel, String> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    UserModel findByEmail(String email);
}
