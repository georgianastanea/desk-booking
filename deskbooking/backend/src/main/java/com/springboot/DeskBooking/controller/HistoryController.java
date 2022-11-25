package com.springboot.DeskBooking.controller;

import com.springboot.DeskBooking.dto.BookingDto;
import com.springboot.DeskBooking.dto.BookingHistoryDto;
import com.springboot.DeskBooking.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public ResponseEntity<List<BookingHistoryDto>> getHistory(){
        return ResponseEntity.ok(historyService.getHistory());
    }
}
