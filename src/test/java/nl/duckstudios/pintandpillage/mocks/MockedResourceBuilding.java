package nl.duckstudios.pintandpillage.mocks;

import lombok.Getter;
import lombok.Setter;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Lumberyard;
import nl.duckstudios.pintandpillage.entity.buildings.ResourceBuilding;
import nl.duckstudios.pintandpillage.model.ResourceType;

import java.util.HashMap;

/**
 *
 */
public class MockedResourceBuilding extends ResourceBuilding {

    @Getter
    @Setter
    public String name = "MockedResourceBuilding";

    public static String resourceName = ResourceType.Wood.name();
    public MockedResourceBuilding() {
        this.updateBuilding();
        super.setDescription("Produces something for your village");
        super.setGeneratesResource(ResourceType.Wood);
    }

    private int updateResourcesPerHour() {
        return (int)(20 + 12 * Math.pow(super.getLevel(), 1.2));
    }


    @Override
    public void updateBuilding() {
        super.setResourcesPerHour(this.updateResourcesPerHour());
        super.setResourcesRequiredLevelUp(new HashMap<>() {
            {
                int level = MockedResourceBuilding.super.getLevel();
                put(resourceName, level * 25 + 25);
            }
        });
    }
}
