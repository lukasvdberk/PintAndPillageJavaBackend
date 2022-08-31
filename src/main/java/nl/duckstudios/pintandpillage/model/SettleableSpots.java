package nl.duckstudios.pintandpillage.model;

import nl.duckstudios.pintandpillage.entity.Coord;

import java.util.ArrayList;
import java.util.List;

public class SettleableSpots {

    public int amountOfJarlsNeeded;
    public List<Coord> validPositions;

    public SettleableSpots() {
        this.validPositions = new ArrayList<>();
    }
}
