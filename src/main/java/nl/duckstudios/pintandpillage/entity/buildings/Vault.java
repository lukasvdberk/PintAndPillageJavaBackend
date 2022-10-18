package nl.duckstudios.pintandpillage.entity.buildings;

import lombok.Getter;
import lombok.Setter;
import nl.duckstudios.pintandpillage.model.ResourceType;

import java.util.HashMap;

public class Vault extends Building {
    @Getter
    @Setter
    public String name = "Vault";

    public Vault() {
        this.updateBuilding();
    }

    @Override
    public void updateBuilding() {
        this.setConstructionTimeGivenLevel((this.getLevel() * 20) + 10);
        this.setResourcesRequiredAtGivenLevel(this.getLevel());
    }

    private void setConstructionTimeGivenLevel(int level) {
        long TIME_PER_LEVEL = 20;
        int MINIMUM_TIME = 10;

        this.setConstructionTimeSeconds((TIME_PER_LEVEL * level) + MINIMUM_TIME);
    }

    private void setResourcesRequiredAtGivenLevel(int level) {
        super.setResourcesRequiredLevelUp(new HashMap<>() {
            {
                put(ResourceType.Wood.name(), (level * 10) + 25);
                put(ResourceType.Stone.name(), (level * 25) + 25);
            }
        });
    }
}
