package nl.duckstudios.pintandpillage.mocks;

import nl.duckstudios.pintandpillage.entity.researching.AxeResearch;
import nl.duckstudios.pintandpillage.entity.researching.Research;
import nl.duckstudios.pintandpillage.model.ResearchType;
import nl.duckstudios.pintandpillage.model.ResourceType;

import java.time.LocalTime;
import java.util.HashMap;


public class MockedResearch extends Research {
    public MockedResearch(int baseSecondsToResearch, int levelToSet, int requiredWood, int requiredStone, int requiredBeer) {
        super.setResearchName(ResearchType.None);
        super.setBaseSecondsToResearch(baseSecondsToResearch);
        super.setBuildingLevelRequirement(levelToSet + (super.getResearchLevel() * 2));
        super.setResourcesRequiredToResearch(new HashMap<>() {
            {
                int level = MockedResearch.super.getResearchLevel();
                put(ResourceType.Wood.name(), requiredWood * (level + 1));
                put(ResourceType.Stone.name(), requiredStone * (level + 1));
                put(ResourceType.Beer.name(), requiredBeer * (level + 1));
            }
        });
        super.setSecondsToResearch(LocalTime.of(0, 0, 0).plusSeconds(super.getBaseSecondsToResearch()));
    }


}
