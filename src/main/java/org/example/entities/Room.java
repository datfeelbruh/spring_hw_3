package org.example.entities;

import lombok.*;
import org.example.RoomClass;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomClass roomClass;

}
