package org.example.contoller;

import lombok.AllArgsConstructor;
import org.example.dto.BookingDtoRq;
import org.example.dto.BookingDtoRs;
import org.example.entities.Booking;
import org.example.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookingController {
    private BookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity createBooking(@RequestBody BookingDtoRq bookingDtoRq) {
        try {
            Long id = bookingService.createBooking(bookingDtoRq);

            return new ResponseEntity(id, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("bookings/{clientName}")
    public List<BookingDtoRs> getClientBookingsByName(@PathVariable String clientName) {
        return bookingService.getAllBookingForClientName(clientName);
    }

    @GetMapping("booking/{id}")
    public ResponseEntity getClientBookingByNumber(@PathVariable Long id) {
        try {
            BookingDtoRs bookingDtoRs = bookingService.getBookingDtoRsOnBookingId(id)
                    .orElseThrow(() -> new RuntimeException("Бронирования для данного клиента не найдены"));
            return new ResponseEntity(bookingDtoRs, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("booking/delete/{id}")
    public ResponseEntity deleteBookingById(@PathVariable Long id) {
        try {
            bookingService.getBookingById(id)
                    .orElseThrow(() -> new RuntimeException("Нет брони с таким id, удаление не произошло"));
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        bookingService.delete(id);
        return new ResponseEntity("Удалена бронь с номером: " + id, HttpStatus.OK);
    }
}
