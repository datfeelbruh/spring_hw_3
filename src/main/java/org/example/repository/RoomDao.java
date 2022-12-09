package org.example.repository;

import org.example.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomDao extends JpaRepository<Room, String> {

}
