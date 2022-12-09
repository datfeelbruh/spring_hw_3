package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingDtoRs {

    private String roomName;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String clientName;

    private String clientEmail;
}
