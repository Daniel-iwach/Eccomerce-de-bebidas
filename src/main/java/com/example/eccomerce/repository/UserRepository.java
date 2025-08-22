package com.example.eccomerce.repository;

import com.example.eccomerce.model.UserEntity;
import com.example.eccomerce.model.enums.EUserState;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,String> {
    Optional<UserEntity>findByEmail(String email);
    List<UserEntity> findByState(EUserState state);

}
