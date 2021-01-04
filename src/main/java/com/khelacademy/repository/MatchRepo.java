package com.khelacademy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khelacademy.document.MatchFixture;

public interface MatchRepo extends MongoRepository<MatchFixture, String> {

}
