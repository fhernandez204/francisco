package com.sp.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sp.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByActive(boolean active);

  List<User> findByNameContainingIgnoreCase(String name);
  
  List<User> findByEmailContainingIgnoreCase(String email);
}
