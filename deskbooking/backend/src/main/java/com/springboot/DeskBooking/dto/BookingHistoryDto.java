package com.springboot.DeskBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingHistoryDto {

    private Long id;

    private Long userId;

    private Long officeNumber;

    private String date;
}
