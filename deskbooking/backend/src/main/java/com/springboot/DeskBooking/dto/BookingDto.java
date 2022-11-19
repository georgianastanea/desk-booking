package com.springboot.DeskBooking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BookingDto {

    private Long id;
    private Long userId;
    private Long officeId;
    private String date;

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", userId=" + userId +
                        ", officeId=" + officeId +
                        ", date=" + date;
    }
}
