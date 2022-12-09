package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class BookingDtoRs {

    private String roomName;

    private Date fromDate;

    private Date toDate;

    private String clientName;
}
