package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.Exceptions.ResearchConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// Story: De smederij upgraden zodat deze research kan doen naar betere krijgers en schepen.
@ExtendWith(MockitoExtension.class)
@Tag("SmithResearchBuildingTest")
public class ResearchBuildingTest {
    Smith researchBuildingUnderTesting;

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

        this.researchBuildingUnderTesting = new Smith();
        this.researchBuildingUnderTesting.setVillage(this.villageMock);

        this.researchToTestWith = this.createDefaultResearch(0);
    }

    @Test
    void should_NotBeAbleToStartResearch_WhenResearchInProcess() {
        // Arrange
        this.researchBuildingUnderTesting.startResearch(this.researchToTestWith);

        // Act

        // try to start another research which should not be started
        int setResearchLevel = 0; // so we don't get a error about that
        MockedResearch newResearch = this.createDefaultResearch(setResearchLevel);
        ResearchConditionsNotMetException thrown = assertThrows(ResearchConditionsNotMetException.class,
                () -> this.researchBuildingUnderTesting.startResearch(newResearch)
        );

        // Assert
        Research expectedResearch = this.researchToTestWith;
        Research actualActiveResearch = this.researchBuildingUnderTesting.getCurrentResearch();

        assertThat(thrown.getMessage(), is("A research is already in progress"));
        assertThat(actualActiveResearch, is(expectedResearch));
        assertTrue(this.researchBuildingUnderTesting.isResearchInProgress());
    }

    @Test
    void should_NotBeAbleToStartResearch_WhenLevelRequirementForResearchIsNotMet() {
        // Act
        // try to start another research which should not be started
        int requiredLevel = 5;
        MockedResearch researchUnderTesting = this.createDefaultResearch(requiredLevel);

        int smithBuildingLevel = 1;
        this.researchBuildingUnderTesting.setLevel(smithBuildingLevel);
        ResearchConditionsNotMetException thrown = assertThrows(ResearchConditionsNotMetException.class,
                () -> this.researchBuildingUnderTesting.startResearch(researchUnderTesting)
        );

        // Assert
        assertThat(thrown.getMessage(), is("Research building level is not high enough"));
    }

    @Test
    void should_SetResearchEqualToCurrentResearch_WhenStartResearch() {
        // Act
        this.researchBuildingUnderTesting.startResearch(this.researchToTestWith);

        // Assert
        Research expectedResearch = this.researchToTestWith;
        Research actualActiveResearch = this.researchBuildingUnderTesting.getCurrentResearch();

        assertThat(actualActiveResearch, is(expectedResearch));
        assertTrue(this.researchBuildingUnderTesting.isResearchInProgress());
    }

    @Test
    void should_SetTheCorrectResearchTime_WhenStartingResearch() {
        // Arrange
        LocalDateTime expectedTimeToBuild = LocalDateTime.now().plusSeconds(baseSecondsToResearch);

        // Act
        this.researchBuildingUnderTesting.startResearch(this.researchToTestWith);

        // Assert
        LocalDateTime actualTimeLeftToBuild = this.researchBuildingUnderTesting.getCurrentResearchFinishTime();

        assertThat(actualTimeLeftToBuild, is(expectedTimeToBuild));
        assertTrue(this.researchBuildingUnderTesting.isResearchInProgress());
    }


    @Test
    void should_SubtractResources_WhenResearchStarted() {
        // Arrange
        int expectedWoodLeftAfterResearch = 400;
        int expectedStoneLeftAfterResearch = 400;
        int expectedBeerLeftAfterResearch = 400;

        // Act
        this.researchBuildingUnderTesting.startResearch(this.researchToTestWith);

        // Assert
        Map<String, Integer> actualResources = this.villageMock.getVillageResources();
        int actualWoodResourcesLeft = actualResources.get(ResourceType.Wood.name());
        int actualStoneResourcesLeft = actualResources.get(ResourceType.Stone.name());
        int actualBeerResourcesLeft = actualResources.get(ResourceType.Beer.name());

        assertThat(actualWoodResourcesLeft, is(expectedWoodLeftAfterResearch));
        assertThat(actualStoneResourcesLeft, is(expectedStoneLeftAfterResearch));
        assertThat(actualBeerResourcesLeft, is(expectedBeerLeftAfterResearch));
    }

    @Test
    void should_NotSubtractResources_WhenResearchNotStarted() {
        // Arrange
        int expectedWoodLeftAfterResearch = 500;
        int expectedStoneLeftAfterResearch = 500;
        int expectedBeerLeftAfterResearch = 500;

        // Act
        MockedResearch researchUnderTesting = new MockedResearch(baseSecondsToResearch, 0, 800, 800, 800);

        // Assert
        ResearchConditionsNotMetException thrown = assertThrows(ResearchConditionsNotMetException.class,
                () -> this.researchBuildingUnderTesting.startResearch(researchUnderTesting)
        );

        // Assert
        assertThat(thrown.getMessage(), is("Not enough resources available"));


        // assert that you still have all your resources
        Map<String, Integer> actualResources = this.villageMock.getVillageResources();
        int actualWoodResourcesLeft = actualResources.get(ResourceType.Wood.name());
        int actualStoneResourcesLeft = actualResources.get(ResourceType.Stone.name());
        int actualBeerResourcesLeft = actualResources.get(ResourceType.Beer.name());

        assertThat(actualWoodResourcesLeft, is(expectedWoodLeftAfterResearch));
        assertThat(actualStoneResourcesLeft, is(expectedStoneLeftAfterResearch));
        assertThat(actualBeerResourcesLeft, is(expectedBeerLeftAfterResearch));
    }
}
