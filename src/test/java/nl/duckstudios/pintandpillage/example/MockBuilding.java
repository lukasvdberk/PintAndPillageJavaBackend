package nl.duckstudios.pintandpillage.example;

import nl.duckstudios.pintandpillage.entity.buildings.Building;
import nl.duckstudios.pintandpillage.helper.ResourceManager;
import org.apache.catalina.Manager;

public class MockBuilding extends Building
{

    @Override
    public void updateBuilding() {
        super.setConstructionTimeSeconds(10);
    }

    public void setResourceManager(ResourceManager resourceManager){
        super.resourceManager = resourceManager;
    }
}
