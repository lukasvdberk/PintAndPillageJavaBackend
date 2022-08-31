package nl.duckstudios.pintandpillage.entity.travels;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import nl.duckstudios.pintandpillage.entity.VillageUnit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CombatTravel extends Travel {

    @Getter
    @Setter
    @OneToMany(mappedBy = "combatTravel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VillageUnit> travelingUnits = new ArrayList<>();
}
