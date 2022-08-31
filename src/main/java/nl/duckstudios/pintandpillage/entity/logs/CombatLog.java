package nl.duckstudios.pintandpillage.entity.logs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.model.LogTypes;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class CombatLog extends LogLine {
    @Getter
    @Setter
    private String type = LogTypes.COMBAT_LOG.name();

    public CombatLog(LocalDateTime timestamp, String message, Village village) {
        super(timestamp, message, village);
    }
}
