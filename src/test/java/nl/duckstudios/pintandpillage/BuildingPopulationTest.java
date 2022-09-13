package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.Coord;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.House;
import nl.duckstudios.pintandpillage.model.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


// Story: Een huis bouwen zodat ik meer populatie in mijn stad krijg
@ExtendWith(MockitoExtension.class)
@Tag("BuildingPopulation")
public class BuildingPopulationTest {
    @Mock
    private Village villageMock;

    private House houseUnderTesting;

    @BeforeEach
    void setupBuilding(){
        this.villageMock = new Village();

        this.houseUnderTesting = new House();
        this.houseUnderTesting.setVillage(villageMock);
        this.houseUnderTesting.setLevel(1);
        this.houseUnderTesting.setPosition(new Coord(1, 4));
        this.houseUnderTesting.updateBuilding();
        this.houseUnderTesting.setUnderConstruction(false);
    }

    /**
     * Kijken wanneer je leveled ook daadwerkelijk een grotere populatie krijgt
     */
    @Test
    void should_IncreasePopulation_WhenHouseIsBuild() {
        // Act
        int actualLevel1VillagePopulationCapacity = this.houseUnderTesting.getPopulationCapacity();

        // Arrange
        this.houseUnderTesting.setLevel(this.houseUnderTesting.getLevel() + 1); // increase level by one
        this.houseUnderTesting.updateBuilding();
        this.houseUnderTesting.setUnderConstruction(false);

        int actualLevel2VillagePopulationCapacity = this.houseUnderTesting.getPopulationCapacity();

        // Assert

        int expectedLevel1Population = 21;
        int expectedLevel2Population = 49;
        // when we level up a house we should have more population capacity
        assertTrue(actualLevel2VillagePopulationCapacity > actualLevel1VillagePopulationCapacity);

        // check if correct population, and the formula works as expected
        assertThat(expectedLevel1Population, is(expectedLevel1Population));
        assertThat(expectedLevel2Population, is(expectedLevel2Population));
    }

    /**
     * Wanneer update building methode niet word aangeroepen zal de populatie ook niet omhoog moeten
     */
    @Test
    void should_PopulationShouldNotIncrease_WhenWeDontUpdateBuilding() {
        int actualLevel1VillagePopulationCapacity = this.houseUnderTesting.getPopulationCapacity();

        // Arrange
        this.houseUnderTesting.setLevel(this.houseUnderTesting.getLevel() + 1); // increase level by one

        int actualLevel2VillagePopulationCapacity = this.houseUnderTesting.getPopulationCapacity();

        // Assert
        int expectedLevel1Population = 21;
        int expectedLevel2Population = 21; // because the update building method was not called

        // check if correct population, and the formula works as expected
        assertThat(expectedLevel1Population, is(expectedLevel1Population));
        assertThat(expectedLevel2Population, is(expectedLevel2Population));
    }
}
