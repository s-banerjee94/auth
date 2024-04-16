package com.secu.auth.repo;

import com.secu.auth.models.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepo extends MongoRepository<AppUser, String> {
    boolean existsByUsername(String username);
}
