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
public class VillageLog extends LogLine {

    @Getter
    @Setter
    private String type = LogTypes.VILLAGE_LOG.name();

    public VillageLog(LocalDateTime timestamp, String message, Village village) {
        super(timestamp, message, village);
    }
}
