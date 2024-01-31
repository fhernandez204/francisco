package com.sp.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sp.user.model.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

  List<Phone> findByNumberContainingIgnoreCase(String number);
}
