package nl.duckstudios.pintandpillage.entity.researching;

import nl.duckstudios.pintandpillage.model.ResearchType;
import nl.duckstudios.pintandpillage.model.ResourceType;

import javax.persistence.Entity;
import java.time.LocalTime;
import java.util.HashMap;

@Entity
public class SpearResearch extends Research {

    public SpearResearch() {
        super.setResearchName(ResearchType.Spear);
        super.setBaseSecondsToResearch(10);
        super.setBuildingLevelRequirement(1 + (super.getResearchLevel() * 2));
        super.setResourcesRequiredToResearch(new HashMap<>() {
            {
                int level = SpearResearch.super.getResearchLevel();
                put(ResourceType.Wood.name(), 80 * level);
                put(ResourceType.Stone.name(), 150 + 50 * level);
                put(ResourceType.Beer.name(), 150 + 50 * level);
            }
        });
        super.setSecondsToResearch(LocalTime.of(0, 0, 0).plusSeconds(super.getBaseSecondsToResearch()));
    }
}
