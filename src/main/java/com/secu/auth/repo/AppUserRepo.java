package com.secu.auth.repo;

import com.secu.auth.models.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppUserRepo extends MongoRepository<AppUser, String> {
    boolean existsByUsername(String username);

    Optional<AppUser> findByUsername(String username);
}
