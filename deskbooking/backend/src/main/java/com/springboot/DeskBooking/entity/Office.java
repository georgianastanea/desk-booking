package com.springboot.DeskBooking.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Office {
    @Id
    private Long id;
    private Long number;
    private boolean isAvailable;

    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
