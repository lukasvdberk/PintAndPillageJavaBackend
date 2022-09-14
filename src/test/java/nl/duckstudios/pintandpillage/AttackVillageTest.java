package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.Exceptions.AttackingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.VillageUnit;
import nl.duckstudios.pintandpillage.entity.production.*;
import nl.duckstudios.pintandpillage.service.CombatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Story: Vijandige dorpen op de wereldkaart aanvallen zodat ik resources van deze dorpen kan stelen.
@ExtendWith(MockitoExtension.class)
@Tag("AttackVillageRequirementTest")
public class AttackVillageTest {
    CombatService combatService;
    List<VillageUnit> attackingVillage;

    @BeforeEach
    void setupAttackingVillage() {
        this.combatService = new CombatService();
        this.attackingVillage = new ArrayList<>();
    }

    @Test
    void should_HaveEnoughUnitsToDefendVillage_WhenStartAnAttack() {
        // Arrange
        VillageUnit attackingVillageUnits = new VillageUnit();

        int amountAttackingUnit = 0;
        attackingVillageUnits.setUnit(new Axe());
        attackingVillageUnits.setAmount(amountAttackingUnit);

        attackingVillage.add(attackingVillageUnits);

        int amountDefendingUnit = 100;
        Village defendingVillage = new Village(); // village with no unit
        defendingVillage.addUnit(new Axe(), amountDefendingUnit); // with no units


        // Act
        this.combatService.checkHasEnoughUnitsToAttack(this.attackingVillage, defendingVillage);

        // Assert

        // nothing to assert. The test passes if the act function does not throw an exception
    }

    @Test
    void should_throwExceptionBecauseNotEnoughUnitsToAttack_WhenStartAnAttack() {
        // Arrange
        VillageUnit attackingVillageUnits = new VillageUnit();

        int amountAttackingUnit = 0;
        attackingVillageUnits.setUnit(new Axe());
        attackingVillageUnits.setAmount(amountAttackingUnit);

        attackingVillage.add(attackingVillageUnits);

        int amountDefendingUnit = 50;
        Village attackingVillage = new Village(); // village with no unit
        attackingVillage.addUnit(new Axe(), amountDefendingUnit); // with no units


        // Act
        AttackingConditionsNotMetException thrown = assertThrows(AttackingConditionsNotMetException.class,
                () -> this.combatService.checkHasEnoughUnitsToAttack(this.attackingVillage, attackingVillage)
        );

        // Assert
        assertThat(thrown).isInstanceOf(AttackingConditionsNotMetException.class)
                .hasMessageContaining("Not enough ");
    }

    @Test
    void should_throwExceptionBecauseNotEnoughUnitsToAttackWithMultipleVillageUnits_WhenCheckingForEnoughAttacks() {
        // Arrange
        VillageUnit attackingSpears = new VillageUnit();

        int amountAttackingUnit = 0;
        attackingSpears.setUnit(new Axe());
        attackingSpears.setAmount(amountAttackingUnit);

        attackingVillage.add(attackingSpears);

        attackingSpears.setUnit(new Bow());
        attackingSpears.setAmount(amountAttackingUnit);

        attackingVillage.add(attackingSpears);

        int amountDefendingUnit = 50;
        Village attackingVillage = new Village(); // village with no unit
        attackingVillage.addUnit(new Axe(), amountDefendingUnit); // with no units


        // Act
        AttackingConditionsNotMetException thrown = assertThrows(AttackingConditionsNotMetException.class,
                () -> this.combatService.checkHasEnoughUnitsToAttack(this.attackingVillage, attackingVillage)
        );

        // Assert
        assertThat(thrown).isInstanceOf(AttackingConditionsNotMetException.class)
                .hasMessageContaining("Not enough ");
    }

    @Test
    void should_HaveEnoughShipsToAttack_WhenStartAnAttack() {
        // Arrange
        List<VillageUnit> attackingVillage = new ArrayList<>();
        int amountAttackingUnit = 100;

        VillageUnit attackingVillageUnits = new VillageUnit();
        attackingVillageUnits.setUnit(new Bow());
        attackingVillageUnits.setAmount(amountAttackingUnit);

        int amountOfShips = amountAttackingUnit / 50; // 50 is the amount of a transport shiop


        VillageUnit attackingVillageShips = new VillageUnit();
        attackingVillageShips.setUnit(new TransportShip());
        attackingVillageShips.setAmount(amountOfShips);

        attackingVillage.add(attackingVillageUnits);
        attackingVillage.add(attackingVillageShips);

        // Act
        this.combatService.checkHasEnoughShipsToSendUnits(attackingVillage);

        // Assert

        // nothing to assert. The test passes if the act function does not throw an exception
    }

    @Test
    void should_throwExceptionBecauseNotEnoughShipsToAttack_WhenStartAnAttack() {
        // Arrange
        List<VillageUnit> attackingVillage = new ArrayList<>();
        int amountAttackingUnit = 100;

        VillageUnit attackingVillageUnits = new VillageUnit();
        attackingVillageUnits.setUnit(new Bow());
        attackingVillageUnits.setAmount(amountAttackingUnit);

        int amountOfShips = 1; // not enough ships, we need at least two

        VillageUnit attackingVillageShips = new VillageUnit();
        attackingVillageShips.setUnit(new TransportShip());
        attackingVillageShips.setAmount(amountOfShips);

        attackingVillage.add(attackingVillageUnits);
        attackingVillage.add(attackingVillageShips);

        // Act
        AttackingConditionsNotMetException thrown = assertThrows(AttackingConditionsNotMetException.class,
                () -> this.combatService.checkHasEnoughShipsToSendUnits(attackingVillage)
        );

        // Assert
        assertThat(thrown).isInstanceOf(AttackingConditionsNotMetException.class)
                .hasMessageContaining("Not enough ship capacity for this attack");
    }

    @Test
    void should_BeAbleToAttackVillageWithNoUnits_WhenYouHaveUnits() {
        // Arrange
        List<VillageUnit> attackingVillage = new ArrayList<>();
        int amountAttackingUnit = 100;

        VillageUnit attackingVillageUnits = new VillageUnit();
        attackingVillageUnits.setUnit(new Axe());
        attackingVillageUnits.setAmount(amountAttackingUnit);

        int shipCapacity = 50;
        int amountOfShips = amountAttackingUnit / shipCapacity;

        VillageUnit attackingVillageShips = new VillageUnit();
        attackingVillageShips.setUnit(new TransportShip());
        attackingVillageShips.setAmount(amountOfShips);

        attackingVillage.add(attackingVillageUnits);
        attackingVillage.add(attackingVillageShips);

        // Act
        this.combatService.checkHasEnoughShipsToSendUnits(attackingVillage);

        // Assert

        // nothing to assert. The test passes if the act function does not throw an exception
    }

    @Test
    void should_BeAbleToAttackVillageWithNoShips_WhenAreCheckingForEnoughShips() {
        // Arrange
        List<VillageUnit> attackingVillage = new ArrayList<>();
        int amountAttackingUnit = 50;

        VillageUnit attackingVillageUnits = new VillageUnit();
        attackingVillageUnits.setUnit(new Bow());
        attackingVillageUnits.setAmount(amountAttackingUnit);

        int amountOfShips = 0; // not enough ships, we need at least two

        VillageUnit attackingVillageShips = new VillageUnit();
        attackingVillageShips.setUnit(new TransportShip());
        attackingVillageShips.setAmount(amountOfShips);

        attackingVillage.add(attackingVillageUnits);
        attackingVillage.add(attackingVillageShips);

        // Act
        AttackingConditionsNotMetException thrown = assertThrows(AttackingConditionsNotMetException.class,
                () -> this.combatService.checkHasEnoughShipsToSendUnits(attackingVillage)
        );

        // Assert
        assertThat(thrown).isInstanceOf(AttackingConditionsNotMetException.class)
                .hasMessageContaining("Not enough ship capacity for this attack");
    }
}
