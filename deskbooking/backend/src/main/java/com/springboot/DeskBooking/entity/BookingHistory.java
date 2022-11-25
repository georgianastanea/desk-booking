package com.springboot.DeskBooking.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bookingDetails;

}
