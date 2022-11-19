package com.springboot.DeskBooking.service;

import com.springboot.DeskBooking.dto.BookingHistoryDto;
import com.springboot.DeskBooking.entity.BookingHistory;
import com.springboot.DeskBooking.repository.BookingHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    private final BookingHistoryRepository historyRepository;

    public HistoryService(BookingHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<BookingHistoryDto> getHistory() {

        Iterable<BookingHistory> iterableBookings = historyRepository.findAll();
        List<BookingHistoryDto> history = new ArrayList<>();

        iterableBookings.forEach(booking -> history.add(new ModelMapper().map(booking, BookingHistoryDto.class)));

        return history;
    }
}
