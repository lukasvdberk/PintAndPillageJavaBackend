package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.Coord;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Lumberyard;
import nl.duckstudios.pintandpillage.entity.buildings.ResourceBuilding;
import nl.duckstudios.pintandpillage.mocks.MockedResourceBuilding;
import nl.duckstudios.pintandpillage.model.ResourceType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

//Test: Een Viking houtverwerking kunnen bouwen zodat ik meer hout resource in mijn stad krijg.
@ExtendWith(MockitoExtension.class)
@Tag("ResourceBuilding")
public class ResourcesBuildingTest {
    @Mock
    private Village villageMock;

    public MockedResourceBuilding mockedResourceBuildingUnderTesting;

    private MockedResourceBuilding setupMockedResourceBuilding(Coord coord) {
        MockedResourceBuilding resourceBuilding = new MockedResourceBuilding();
        resourceBuilding.setVillage(villageMock);
        resourceBuilding.setLevel(1);
        resourceBuilding.setPosition(coord);
        resourceBuilding.updateBuilding();
        resourceBuilding.setUnderConstruction(false);
        resourceBuilding.setLastCollected(LocalDateTime.now());

        return resourceBuilding;
    }

    void setupLumberYardUnderTesting(){
        this.villageMock = new Village();

        // so when we want to collect resources we are not limited
        int storageLimit = 1000000000;
        this.villageMock.setResourceLimit(storageLimit);

        this.mockedResourceBuildingUnderTesting = this.setupMockedResourceBuilding(new Coord(1, 4));
    }

    @Test
    void should_IncreaseProductionResourceProduction_WhenBuild() {
        this.setupLumberYardUnderTesting();
        // Arrange
        int expectedResourcesPerHour = 532; // 500 extra because that is the starting value

        // Act

        // reset to an hour back, so we can collect an hour of resources
        LocalDateTime hourSystemTimeBack = LocalDateTime.now().minusHours(1);
        this.mockedResourceBuildingUnderTesting.setLastCollected(hourSystemTimeBack);
        this.mockedResourceBuildingUnderTesting.collectResources();

        // Assert
        Map<String, Integer> currentResources =  this.villageMock.getVillageResources();
        int actualResources = currentResources.get(MockedResourceBuilding.resourceName);

        assertThat(actualResources, is(expectedResourcesPerHour));
    }

    @Test
    void should_IncreaseProductionResourceProduction_WhenWaitingMultipleHours() {
        // Arrange
        this.setupLumberYardUnderTesting();
        int expectedResourcesPerHour = 596; // 500 extra because that is the starting value

        // Act

        // collect 3 hours of resources
        LocalDateTime hourSystemTimeBack = LocalDateTime.now().minusHours(3);
        this.mockedResourceBuildingUnderTesting.setLastCollected(hourSystemTimeBack);
        this.mockedResourceBuildingUnderTesting.collectResources();

        // Assert
        Map<String, Integer> currentResources =  this.villageMock.getVillageResources();
        int actualResources = currentResources.get(MockedResourceBuilding.resourceName);

        assertThat(actualResources, is(expectedResourcesPerHour));
    }

    @Test
    void should_IncreaseProductionWoodProduction_WhenWaitingMultipleHoursWithHigherLevel() {
        // Arrange
        this.setupLumberYardUnderTesting();

        int buildingLevel = 10;
        this.mockedResourceBuildingUnderTesting.setLevel(buildingLevel); // upgrade to level 10
        this.mockedResourceBuildingUnderTesting.updateBuilding();
        this.mockedResourceBuildingUnderTesting.setUnderConstruction(false);
        int expectedResourcesPerHour = 1130; // 500 extra because that is the starting value

        // Act

        // collect 3 hours of resources
        LocalDateTime hourSystemTimeBack = LocalDateTime.now().minusHours(3);
        this.mockedResourceBuildingUnderTesting.setLastCollected(hourSystemTimeBack);
        this.mockedResourceBuildingUnderTesting.collectResources();

        // Assert
        Map<String, Integer> currentResources =  this.villageMock.getVillageResources();
        int actualResources = currentResources.get(MockedResourceBuilding.resourceName);

        assertThat(actualResources, is(expectedResourcesPerHour));
    }

    @Test
    void should_IncreaseProductionWoodProduction_WithMultipleLumberyards() {
        // Arrange
        this.setupLumberYardUnderTesting();
        int expectedResourcesPerHour = 788; // 500 extra because that is the starting value

        // setup 2 more lumberyards to increase production
        ResourceBuilding secondMockedResourceBuilding = this.setupMockedResourceBuilding(new Coord(2, 2));
        ResourceBuilding thirdMockedResourceBuilding = this.setupMockedResourceBuilding(new Coord(9, 4));

        // Act

        // collect 3 hours of resources
        LocalDateTime hourSystemTimeBack = LocalDateTime.now().minusHours(3);
        this.mockedResourceBuildingUnderTesting.setLastCollected(hourSystemTimeBack);
        this.mockedResourceBuildingUnderTesting.collectResources();

        secondMockedResourceBuilding.setLastCollected(hourSystemTimeBack);
        secondMockedResourceBuilding.collectResources();

        thirdMockedResourceBuilding.setLastCollected(hourSystemTimeBack);
        thirdMockedResourceBuilding.collectResources();

        // Assert
        Map<String, Integer> currentResources = this.villageMock.getVillageResources();
        int actualResources = currentResources.get(MockedResourceBuilding.resourceName);

        assertThat(actualResources, is(expectedResourcesPerHour));
    }

    @Test
    void should_NotIncreaseWoodProduction_WhenNotBuild() {
        this.villageMock = new Village();
        int expectedResourcesPerHour = 500; // default wood

        // Act

        // collect 3 hours of resources, even though  we don't have any resources
        this.villageMock.updateVillageState();

        // Assert
        Map<String, Integer> currentResources = this.villageMock.getVillageResources();
        int actualResources = currentResources.get(MockedResourceBuilding.resourceName);

        assertThat(actualResources, is(expectedResourcesPerHour));
    }
}
