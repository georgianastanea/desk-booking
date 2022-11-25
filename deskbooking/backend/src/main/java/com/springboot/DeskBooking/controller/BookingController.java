package com.springboot.DeskBooking.controller;


import com.springboot.DeskBooking.dto.BookingDto;
import com.springboot.DeskBooking.entity.BookingHistory;
import com.springboot.DeskBooking.repository.BookingHistoryRepository;
import com.springboot.DeskBooking.repository.BookingRepository;
import com.springboot.DeskBooking.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;


    public BookingController(BookingService bookingService, BookingRepository historyRepository, BookingHistoryRepository historyRepository1) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto booking){
        return ResponseEntity.ok(bookingService.addBooking(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody BookingDto booking){
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        bookingService.removeBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
