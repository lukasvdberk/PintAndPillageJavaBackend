package nl.duckstudios.pintandpillage.entity.buildings;

import lombok.Getter;
import lombok.Setter;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.model.ResourceType;

import javax.persistence.Entity;
import java.util.HashMap;

@Entity
public class Mine extends ResourceBuilding {


    @Getter
    @Setter
    public String name = "Mine";

    public Mine() {
        this.updateBuilding();
        super.setDescription("Produces stone for your village");
        super.setGeneratesResource(ResourceType.Stone);
    }

    public int resourcesProducedInGivenTime(Village villageToAddResources) {
        return 0;
    }

    //    private int updateResourcesPerHour() {
//        return (int)(20 + 12 * Math.pow(super.getLevel(), 1.2));
//    }
    private int updateResourcesPerHour() {
        return 1000000;
    }

    @Override
    public void updateBuilding() {
        super.setConstructionTimeSeconds(12 + ((30 + (super.getLevel() - 1) * 2L) * (long) Math.pow(super.getLevel(), 1.6)));
        super.setResourcesPerHour(this.updateResourcesPerHour());
        super.setResourcesRequiredLevelUp(new HashMap<>() {
            {
                int level = Mine.super.getLevel();
                put(ResourceType.Wood.name(), level * 25 + 25);
            }
        });
    }
}
