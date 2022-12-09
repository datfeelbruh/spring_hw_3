package org.example.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BookingDtoRq {

    private String roomName;

    private Date fromDate;

    private Date toDate;

    private String clientName;

}
