package com.example.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.sample.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
