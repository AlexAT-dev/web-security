package com.alexat.websecurity.user;


/*
@author   AlexAT
@project   websecurity
@class  UserRepository
@version  1.0.0
@since 02.05.2025 - 17.54
*/

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}