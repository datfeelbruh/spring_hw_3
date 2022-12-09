package org.example.repository;

import org.example.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingDao extends JpaRepository<Booking, Integer> {
    @Query(value = "select * from booking b " +
            "where b.room_name = :roomName " +
            "and b.from_date <= :to " +
            "and b.to_date >= :from",
            nativeQuery = true)
    List<Booking> checkRoomBusy(@Param("roomName") String roomName, @Param("from") LocalDate from, @Param("to") LocalDate to);
    List<Booking> findAllByClientName(String clientName);
    Optional<Booking> findById(Long bookingId);

    @Query(value = "select c.email from booking b " +
            "join client c on b.client_name = c.name " +
            "where c.email = :email limit 1",
            nativeQuery = true)
    String findEmailExistedClient(@Param("email") String email);
    void deleteById(Long bookingId);
}
