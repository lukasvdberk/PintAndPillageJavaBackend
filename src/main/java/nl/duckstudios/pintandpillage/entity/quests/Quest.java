package nl.duckstudios.pintandpillage.entity.quests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;


public class Quest {
    @Getter
    @Setter
    private int questId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private boolean isCompleted = false;

}
