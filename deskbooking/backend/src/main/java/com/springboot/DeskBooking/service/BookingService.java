package com.springboot.DeskBooking.service;

import com.springboot.DeskBooking.entity.Booking;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.BookingRepository;
import com.springboot.DeskBooking.repository.OfficeRepository;
import com.springboot.DeskBooking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final OfficeRepository officeRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, OfficeRepository officeRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.officeRepository = officeRepository;
    }

    public Booking addBooking(Booking booking) {
        userRepository.findById(booking.getUserId()).orElseThrow(() -> {
            throw new CrudOperationException("User is not registered!");
        });

        officeRepository.findById(booking.getOfficeId()).orElseThrow(() -> {
            throw new CrudOperationException("Invalid office!");
        });

        bookingRepository.save(booking);
        return booking;
    }

    public void removeBooking(Long id) {
        bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });

        bookingRepository.deleteById(id);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });
    }

    public Booking updateBooking(Long id, Booking newBooking) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });

        booking.setUserId(newBooking.getUserId());
        booking.setDate(newBooking.getDate());
        booking.setOfficeId(newBooking.getOfficeId());
        bookingRepository.save(booking);

        return booking;
    }

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

//    public List<Booking> getBookingsByUser(AppUser user){
//        List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
//        return bookings.stream().filter(booking -> booking.getUser().equals(user)).collect(Collectors.toList());
//
//    }
}
