package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.buildings.Vault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VaultTest {
    @Test
    void should_vaultDescriptionBeVault_WhenBuildingIsVault() {
        // Arrange
        Vault vaultUnderTesting = new Vault();

        // Act
        String expected = "Vault";
        String actual = vaultUnderTesting.getName();

        // Assert
        assertThat(expected, is(actual));
    }

    @Test
    void should_setRequiredBuildingTime_WhenVaultIsCreated() {
        // Arrange
        Vault vaultUnderTesting = new Vault();

        // Act
        LocalTime expected = LocalTime.of(0, 3, 30);
        LocalTime actual = vaultUnderTesting.getConstructionTime();

        // Assert
        assertThat(expected, is(actual));
    }

    @Test
    void should_setRequiredWoodResources_WhenVaultIsUpdate() {
        // Arrange
        Vault vaultUnderTesting = new Vault();
        vaultUnderTesting.setLevel(3);
        vaultUnderTesting.updateBuilding();

        // Act
        LocalTime expected = LocalTime.of(0, 23, 30);
        LocalTime actual = vaultUnderTesting.getConstructionTime();

        // Assert
        assertThat(expected, is(actual));
    }

    @Test
    void should_setRequiredWoodResources_WhenVaultIsCreated() {
        // Arrange
        Vault vaultUnderTesting = new Vault();

        // Act
        int expectedWoodResourcesRequired = 25;
        Map<String, Integer> requiredResources = vaultUnderTesting.getResourcesRequiredLevelUp();
        int actualRequiredWood = requiredResources.get("Wood");

        // Assert
        assertThat(actualRequiredWood, is(expectedWoodResourcesRequired));
    }

    @Test
    void should_setRequiredStoneResources_WhenVaultIsCreated() {
        // Arrange
        Vault vaultUnderTesting = new Vault();

        // Act
        int expectedStoneResourcesRequired = 25;
        Map<String, Integer> requiredResources = vaultUnderTesting.getResourcesRequiredLevelUp();
        int actualRequiredWood = requiredResources.get("Stone");

        // Assert
        assertThat(expectedStoneResourcesRequired, is(actualRequiredWood));
    }

    @Test
    void should_setRequiredWoodResources_WhenVaultIsUpdated() {
        // Arrange
        Vault vaultUnderTesting = new Vault();
        vaultUnderTesting.setLevel(3);
        vaultUnderTesting.updateBuilding();

        // Act
        int expectedWoodResourcesRequired = 55;
        Map<String, Integer> requiredResources = vaultUnderTesting.getResourcesRequiredLevelUp();
        int actualRequiredWood = requiredResources.get("Wood");

        // Assert
        assertThat(actualRequiredWood, is(expectedWoodResourcesRequired));
    }

    @Test
    void should_setRequiredStoneResources_WhenVaultIsUpdated() {
        // Arrange
        Vault vaultUnderTesting = new Vault();
        vaultUnderTesting.setLevel(3);
        vaultUnderTesting.updateBuilding();

        // Act
        int expectedStoneResourcesRequired = 100;
        Map<String, Integer> requiredResources = vaultUnderTesting.getResourcesRequiredLevelUp();
        int actualRequiredStone = requiredResources.get("Stone");

        // Assert
        assertThat(actualRequiredStone, is(expectedStoneResourcesRequired));
    }
}
