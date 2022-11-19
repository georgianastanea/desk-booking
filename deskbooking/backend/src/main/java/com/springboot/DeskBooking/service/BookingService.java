package com.springboot.DeskBooking.service;

import com.springboot.DeskBooking.entity.AppUser;
import com.springboot.DeskBooking.entity.Booking;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking addBooking(Booking booking){
        bookingRepository.save(booking);
        return booking;
    }

    public void removeBooking(Long id){
        try {
            bookingRepository.findById(id);
        }catch (CrudOperationException e){
            System.out.println("Booking does not exist");
        }
        bookingRepository.deleteById(id);
    }

    public Booking getBookingById(Long id){
        return bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });
    }

    public Booking updateBooking(Long id, Booking newBooking){
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });

        booking.setUser(newBooking.getUser());
        booking.setDate(newBooking.getDate());
        booking.setOffice(newBooking.getOffice());
        bookingRepository.save(booking);

        return booking;
    }

    public List<Booking> getAllBookings(){
        return (List<Booking>) bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(AppUser user){
        List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
        return bookings.stream().filter(booking -> booking.getUser().equals(user)).collect(Collectors.toList());

    }
}
