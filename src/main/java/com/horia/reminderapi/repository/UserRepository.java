package com.horia.reminderapi.repository;

import com.horia.reminderapi.client.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}