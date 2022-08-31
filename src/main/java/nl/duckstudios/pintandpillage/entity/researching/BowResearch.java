package nl.duckstudios.pintandpillage.entity.researching;

import nl.duckstudios.pintandpillage.model.ResearchType;
import nl.duckstudios.pintandpillage.model.ResourceType;

import javax.persistence.Entity;
import java.time.LocalTime;
import java.util.HashMap;

@Entity
public class BowResearch extends Research {

    public BowResearch() {
        super.setResearchName(ResearchType.Bow);
        super.setBaseSecondsToResearch(13200);
        super.setBuildingLevelRequirement(8 + (super.getResearchLevel() * 2));
        super.setResourcesRequiredToResearch(new HashMap<>() {
            {
                int level = BowResearch.super.getResearchLevel();
                put(ResourceType.Wood.name(), 4100 * (level + 1));
                put(ResourceType.Stone.name(), 4100 * (level + 1));
                put(ResourceType.Beer.name(), 4100 * (level + 1));
            }
        });
        super.setSecondsToResearch(LocalTime.of(0, 0, 0).plusSeconds(super.getBaseSecondsToResearch()));
    }
}
