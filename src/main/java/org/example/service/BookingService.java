package org.example.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.dto.BookingDtoRq;
import org.example.dto.BookingDtoRs;
import org.example.entities.Booking;
import org.example.entities.Client;
import org.example.mappers.BookingMapper;
import org.example.mappers.ClientMapper;
import org.example.repository.BookingDao;
import org.example.repository.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookingService {
    private BookingDao bookingDao;
    private BookingMapper bookingMapper;
    private ClientMapper clientMapper;
    private ClientDao clientDao;
    @Transactional
    @SneakyThrows
    public Long createBooking(BookingDtoRq bookingDtoRq) {
        List<Booking> busyBookings = bookingDao.checkRoomBusy(
                bookingDtoRq.getRoomName(),
                bookingDtoRq.getFromDate(),
                bookingDtoRq.getToDate()
        );
        Booking booking = bookingMapper.mapFromDto(bookingDtoRq);
        if (!busyBookings.isEmpty()) {
            throw new RuntimeException("Номер уже забронирован");
        }

        if (isNewClient(bookingDtoRq) || isSameEmailAndName(bookingDtoRq)) {
            Client client = clientMapper.mapFromDto(bookingDtoRq.getClient().getClientName(),
                    bookingDtoRq.getClient().getEmail());

            clientDao.save(client);
        } else {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        bookingDao.save(booking);

        return booking.getId();
    }

    public List<BookingDtoRs> getAllBookingForClientName(String clientName) {
        return bookingDao.findAllByClientName(clientName)
                .stream()
                .map(booking -> bookingMapper.mapToResponse(booking))
                .collect(Collectors.toList());
    }

    public Optional<BookingDtoRs> getBookingDtoRsOnBookingId(Long bookingNumber) {
        return bookingDao.findById(bookingNumber).map(e -> bookingMapper.mapToResponse(e));
    }

    @Transactional
    public void delete(Long id) {
        bookingDao.deleteById(id);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingDao.findById(id);
    }

    private boolean isNewClient(BookingDtoRq bookingDtoRq) {
        if (clientDao.findByEmail(bookingDtoRq.getClient().getEmail()) == null) {
            return bookingDao.findEmailExistedClient(bookingDtoRq.getClient().getEmail()) == null;
        }
        return false;
    }

    private boolean isSameEmailAndName(BookingDtoRq bookingDtoRq) {
        String clientName = clientDao.findAll()
                .stream()
                .filter(e -> e.getEmail().equals(bookingDtoRq.getClient().getEmail()))
                .map(Client::getName)
                .collect(Collectors.joining());

        return bookingDtoRq.getClient().getClientName().equals(clientName);
    }
}
