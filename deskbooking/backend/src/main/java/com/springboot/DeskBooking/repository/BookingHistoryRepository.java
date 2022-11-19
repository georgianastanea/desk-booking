package com.springboot.DeskBooking.repository;

import com.springboot.DeskBooking.entity.Booking;
import com.springboot.DeskBooking.entity.BookingHistory;
import org.springframework.data.repository.CrudRepository;

public interface BookingHistoryRepository extends CrudRepository<BookingHistory, Long> {
}
