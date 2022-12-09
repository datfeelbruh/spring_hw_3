package org.example.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.EmailGenerator;
import org.example.dto.BookingDtoRq;
import org.example.dto.BookingDtoRs;
import org.example.entities.Booking;
import org.example.entities.Client;
import org.example.mappers.BookingMapper;
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
        bookingDao.save(booking);

        if (checkBookingDaoAndClientDaoOnName(bookingDtoRq.getClientName())) {
            Client client = new Client(bookingDtoRq.getClientName(), EmailGenerator.generate());
            clientDao.save(client);
        }

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
    public Long delete(Long id) {
        bookingDao.deleteById(id);
        return id;
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingDao.findById(id);
    }

    private boolean checkBookingDaoAndClientDaoOnName(String name) {
        boolean isClientBooked = !bookingDao.findAllByClientName(name).isEmpty();
        boolean isOurClient = clientDao.findAll()
                .stream()
                .map(Client::getName).noneMatch(e -> e.equals(name));
        return isClientBooked && isOurClient;
    }
}
