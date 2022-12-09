package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingDtoRq {

    private String roomName;

    private LocalDate fromDate;

    private LocalDate toDate;

    @JsonProperty("client")
    private Client client;
    @Getter
    @Setter
    public static class Client {
        @JsonProperty("name")
        private String clientName;

        @JsonProperty("email")
        private String email;
    }
}
