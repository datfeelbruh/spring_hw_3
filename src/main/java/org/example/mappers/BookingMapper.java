package org.example.mappers;

import lombok.SneakyThrows;
import org.example.dto.BookingDtoRq;
import org.example.dto.BookingDtoRs;
import org.example.entities.Booking;
import org.example.entities.Client;
import org.example.entities.Room;
import org.example.repository.RoomDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BookingMapper {
    @Autowired
    RoomDao roomDao;
    @Autowired
    ClientMapper clientMapper;
    @SneakyThrows
    public Booking mapFromDto(BookingDtoRq bookingDtoRq) {

        Room room = roomDao.findById(bookingDtoRq.getRoomName())
                .orElseThrow(() -> new RuntimeException("В отеле нет такого номера для бронирования"));

        Client client = clientMapper.mapFromDto(bookingDtoRq.getClient().getClientName(),
                bookingDtoRq.getClient().getEmail());

        return new Booking(room, bookingDtoRq.getFromDate(), bookingDtoRq.getToDate(), client);
    }

    public BookingDtoRs mapToResponse(Booking booking) {
        return new BookingDtoRs(booking.getRoom().getName(), booking.getFromDate(), booking.getToDate(),
                booking.getClient().getName(), booking.getClient().getEmail());
    }
}
