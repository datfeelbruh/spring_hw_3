package org.example.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "room_name", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private Room room;

    @Column(nullable = false)
    @NonNull
    private Date fromDate;

    @Column(nullable = false)
    @NonNull
    private Date toDate;

    @Column(nullable = false)
    @NonNull
    private String clientName;

}
