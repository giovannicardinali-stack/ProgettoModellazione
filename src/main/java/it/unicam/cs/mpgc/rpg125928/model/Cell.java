package it.unicam.cs.mpgc.rpg125928.model;

public class Cell {

    Occupant occupant;

    public Cell(Occupant occupant) {
        this.occupant = occupant;
    }

    public void setOccupant(Occupant occupant) { this.occupant = occupant; }

    public Occupant getOccupant() { return occupant; }
}
