package com.springboot.DeskBooking.service;

import com.springboot.DeskBooking.dto.BookingDto;
import com.springboot.DeskBooking.entity.AppUser;
import com.springboot.DeskBooking.entity.Booking;
import com.springboot.DeskBooking.entity.Office;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.BookingRepository;
import com.springboot.DeskBooking.repository.OfficeRepository;
import com.springboot.DeskBooking.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

    public BookingDto addBooking(BookingDto bookingDto) {
        AppUser user = userRepository.findById(bookingDto.getUserId()).orElseThrow(() -> {
            throw new CrudOperationException("User is not registered!");
        });

        Office office = officeRepository.findById(bookingDto.getOfficeId()).orElseThrow(() -> {
            throw new CrudOperationException("Invalid office!");
        });

        Booking booking = Booking.builder().user(user).office(office).date(bookingDto.getDate()).build();
        bookingRepository.save(booking);
        bookingDto.setId(booking.getId());
        return bookingDto;
    }

    public void removeBooking(Long id) {
        bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });

        bookingRepository.deleteById(id);
    }

    public BookingDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });
        return new ModelMapper().map(booking,BookingDto.class);
    }

    public BookingDto updateBooking(Long id, BookingDto newBooking) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });

        AppUser user = userRepository.findById(newBooking.getUserId()).orElseThrow(() -> {
                    throw new CrudOperationException("User does not exist");
                });
        Office office = officeRepository.findById(newBooking.getOfficeId()).orElseThrow(() -> {
            throw new CrudOperationException("Invalid office!");
        });


        booking.setUser(user);
        booking.setDate(newBooking.getDate());
        booking.setOffice(office);
        bookingRepository.save(booking);
        newBooking.setId(booking.getId());

        return newBooking;
    }

    public List<BookingDto> getAllBookings() {
        Iterable<Booking> iterableBookings = bookingRepository.findAll();
        List<BookingDto> bookings = new ArrayList<>();

        iterableBookings.forEach(booking -> bookings.add(BookingDto.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .officeId(booking.getOffice().getId())
                .date(booking.getDate())
                .build()));

        return bookings;
    }

//    public List<Booking> getBookingsByUser(AppUser user){
//        List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
//        return bookings.stream().filter(booking -> booking.getUser().equals(user)).collect(Collectors.toList());
//
//    }
}
