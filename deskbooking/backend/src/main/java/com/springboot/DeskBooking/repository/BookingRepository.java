package com.springboot.DeskBooking.repository;

import com.springboot.DeskBooking.entity.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
