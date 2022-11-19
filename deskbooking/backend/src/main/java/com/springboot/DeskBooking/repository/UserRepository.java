package com.springboot.DeskBooking.repository;

import com.springboot.DeskBooking.entity.AppUser;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
}
