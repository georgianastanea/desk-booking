package com.springboot.DeskBooking.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Office {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long number;
    private boolean isAvailable;

}
