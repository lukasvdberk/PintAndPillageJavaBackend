package nl.duckstudios.pintandpillage.entity.travels;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Travel {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private long id;

    @Getter
    @Setter
    private String name = getClass().getName();

    @Getter
    @Setter
    private LocalDateTime timeOfArrival;

    @Getter
    @Setter
    private LocalTime travelTime;

    @Getter
    @Setter
    private LocalTime travelTimeLeft;
}
