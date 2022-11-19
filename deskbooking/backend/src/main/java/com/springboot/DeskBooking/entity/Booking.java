package com.springboot.DeskBooking.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "appUser_id")
    private Long userId;

    @Column(name = "office_id")
    private Long officeId;

    private String date;



}
