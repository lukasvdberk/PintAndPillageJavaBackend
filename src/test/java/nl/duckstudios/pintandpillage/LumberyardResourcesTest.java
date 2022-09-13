package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.Coord;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Lumberyard;
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
@Tag("LumberyardResourcesTest")
public class LumberyardResourcesTest {
    @Mock
    private Village villageMock;

    public Lumberyard lumberyardTesting;

    private Lumberyard setupLumberYard(Coord coord) {
        Lumberyard newLumberYard = new Lumberyard();
        newLumberYard.setVillage(villageMock);
        newLumberYard.setLevel(1);
        newLumberYard.setPosition(coord);
        newLumberYard.updateBuilding();
        newLumberYard.setUnderConstruction(false);
        newLumberYard.setLastCollected(LocalDateTime.now());

        return newLumberYard;
    }

    void setupLumberYardUnderTesting(){
        this.villageMock = new Village();

        // so when we want to collect resources we are not limited
        int storageLimit = 1000000000;
        this.villageMock.setResourceLimit(storageLimit);

        this.lumberyardTesting = this.setupLumberYard(new Coord(1, 4));
    }

    @Test
    void should_IncreaseProductionWoodProduction_WhenBuild() {
        this.setupLumberYardUnderTesting();
        // Arrange
        int expectedResourcesPerHour = 1000500; // 500 extra because that is the starting value

        // Act

        // reset to an hour back, so we can collect an hour of resources
        LocalDateTime hourSystemTimeBack = LocalDateTime.now().minusHours(1);
        this.lumberyardTesting.setLastCollected(hourSystemTimeBack);
        this.lumberyardTesting.collectResources();

        // Assert
        Map<String, Integer> currentResources =  this.villageMock.getVillageResources();
        int actualResources = currentResources.get(ResourceType.Wood.name());

        assertThat(actualResources, is(expectedResourcesPerHour));
    }

    @Test
    void should_IncreaseProductionWoodProduction_WhenWaitingMultipleHours() {
        // Arrange
        this.setupLumberYardUnderTesting();
        int expectedResourcesPerHour = 3000500; // 500 extra because that is the starting value

        // Act

        // collect 3 hours of resources
        LocalDateTime hourSystemTimeBack = LocalDateTime.now().minusHours(3);
        this.lumberyardTesting.setLastCollected(hourSystemTimeBack);
        this.lumberyardTesting.collectResources();

        // Assert
        Map<String, Integer> currentResources =  this.villageMock.getVillageResources();
        int actualResources = currentResources.get(ResourceType.Wood.name());

        assertThat(actualResources, is(expectedResourcesPerHour));
    }

    @Test
    void should_IncreaseProductionWoodProduction_WithMultipleLumberyards() {
        // Arrange
        this.setupLumberYardUnderTesting();
        int expectedResourcesPerHour = 9000500; // 500 extra because that is the starting value

        // setup 2 more lumberyards to increase production
        Lumberyard secondLumberYard = this.setupLumberYard(new Coord(2, 2));
        Lumberyard thirdLumberYard = this.setupLumberYard(new Coord(9, 4));

        // Act

        // collect 3 hours of resources
        LocalDateTime hourSystemTimeBack = LocalDateTime.now().minusHours(3);
        this.lumberyardTesting.setLastCollected(hourSystemTimeBack);
        this.lumberyardTesting.collectResources();

        secondLumberYard.setLastCollected(hourSystemTimeBack);
        secondLumberYard.collectResources();

        thirdLumberYard.setLastCollected(hourSystemTimeBack);
        thirdLumberYard.collectResources();

        // Assert
        Map<String, Integer> currentResources = this.villageMock.getVillageResources();
        int actualResources = currentResources.get(ResourceType.Wood.name());

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
        int actualResources = currentResources.get(ResourceType.Wood.name());

        assertThat(actualResources, is(expectedResourcesPerHour));
    }
}
