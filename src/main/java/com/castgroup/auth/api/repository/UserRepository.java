package com.castgroup.auth.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.castgroup.auth.api.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);
}
