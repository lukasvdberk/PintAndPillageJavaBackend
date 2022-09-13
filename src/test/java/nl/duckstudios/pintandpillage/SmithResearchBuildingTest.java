package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.Exceptions.BuildingConditionsNotMetException;
import nl.duckstudios.pintandpillage.Exceptions.ResearchConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.ResearchBuilding;
import nl.duckstudios.pintandpillage.entity.buildings.Smith;
import nl.duckstudios.pintandpillage.entity.researching.Research;
import nl.duckstudios.pintandpillage.mocks.MockedResearch;
import nl.duckstudios.pintandpillage.model.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("ResearchBuilding")
public class SmithResearchBuildingTest {
    Smith smithBuildingUnderTesting;

    MockedResearch researchToTestWith;

    @Mock
    Village villageMock;


    int baseSecondsToResearch = 1;
    private MockedResearch createDefaultResearch(int levelToSet) {
        int requiredWood = 100;
        int requiredStone = 100;
        int requiredBeer = 100;

        return new MockedResearch(baseSecondsToResearch, levelToSet, requiredWood, requiredStone, requiredBeer);
    }

    @BeforeEach
    void setupResearchBuildingUnderTesting() {
        this.villageMock = new Village();

        this.smithBuildingUnderTesting = new Smith();
        this.smithBuildingUnderTesting.setVillage(this.villageMock);

        this.researchToTestWith = this.createDefaultResearch(0);
    }

    @Test
    void should_NotBeAbleToStartResearch_WhenResearchInProcess() {
        // Arrange
        this.smithBuildingUnderTesting.startResearch(this.researchToTestWith);

        // Act

        // try to start another research which should not be started
        int setResearchLevel = 0; // so we don't get a error about that
        MockedResearch newResearch = this.createDefaultResearch(setResearchLevel);
        ResearchConditionsNotMetException thrown = assertThrows(ResearchConditionsNotMetException.class,
                () -> this.smithBuildingUnderTesting.startResearch(newResearch)
        );

        // Assert
        Research expectedResearch = this.researchToTestWith;
        Research actualActiveResearch = this.smithBuildingUnderTesting.getCurrentResearch();

        assertThat(thrown.getMessage(), is("A research is already in progress"));
        assertThat(actualActiveResearch, is(expectedResearch));
        assertTrue(this.smithBuildingUnderTesting.isResearchInProgress());
    }

    @Test
    void should_NotBeAbleToStartResearch_WhenLevelRequirementForResearchIsNotMet() {
        // Act
        // try to start another research which should not be started
        int requiredLevel = 5;
        MockedResearch researchUnderTesting = this.createDefaultResearch(requiredLevel);

        int smithBuildingLevel = 1;
        this.smithBuildingUnderTesting.setLevel(smithBuildingLevel);
        ResearchConditionsNotMetException thrown = assertThrows(ResearchConditionsNotMetException.class,
                () -> this.smithBuildingUnderTesting.startResearch(researchUnderTesting)
        );

        // Assert
        assertThat(thrown.getMessage(), is("Research building level is not high enough"));
    }

    @Test
    void should_SetResearchEqualToCurrentResearch_WhenStartResearch() {
        // Act
        this.smithBuildingUnderTesting.startResearch(this.researchToTestWith);

        // Assert
        Research expectedResearch = this.researchToTestWith;
        Research actualActiveResearch = this.smithBuildingUnderTesting.getCurrentResearch();

        assertThat(actualActiveResearch, is(expectedResearch));
        assertTrue(this.smithBuildingUnderTesting.isResearchInProgress());
    }

    @Test
    void should_SetTheCorrectResearchTime_WhenStartingResearch() {
        // Arrange
        LocalDateTime expectedTimeToBuild = LocalDateTime.now().plusSeconds(baseSecondsToResearch);

        // Act
        this.smithBuildingUnderTesting.startResearch(this.researchToTestWith);

        // Assert
        LocalDateTime actualTimeLeftToBuild = this.smithBuildingUnderTesting.getCurrentResearchFinishTime();

        assertThat(actualTimeLeftToBuild, is(expectedTimeToBuild));
        assertTrue(this.smithBuildingUnderTesting.isResearchInProgress());
    }


    @Test
    void should_SubtractResources_WhenResearchStarted() {
        // Arrange
        int expectedWoodLeftAfterResearch = 400;
        int expectedStoneLeftAfterResearch = 400;
        int expectedBeerLeftAfterResearch = 400;

        // Act
        this.smithBuildingUnderTesting.startResearch(this.researchToTestWith);

        // Assert
        Map<String, Integer> actualResources = this.villageMock.getVillageResources();
        int actualWoodResourcesLeft = actualResources.get(ResourceType.Wood.name());
        int actualStoneResourcesLeft = actualResources.get(ResourceType.Stone.name());
        int actualBeerResourcesLeft = actualResources.get(ResourceType.Beer.name());

        assertThat(actualWoodResourcesLeft, is(expectedWoodLeftAfterResearch));
        assertThat(actualStoneResourcesLeft, is(expectedStoneLeftAfterResearch));
        assertThat(actualBeerResourcesLeft, is(expectedBeerLeftAfterResearch));
    }
}
