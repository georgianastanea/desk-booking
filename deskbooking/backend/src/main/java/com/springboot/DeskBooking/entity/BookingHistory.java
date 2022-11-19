package com.springboot.DeskBooking.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookingHistory {

    @Id
    private Long id;
    private String bookingDetails;


}
