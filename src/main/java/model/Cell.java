package model;

public class Cell {

    Occupant occupant;

    public Cell(Occupant occupant) {
        this.occupant = occupant;
    }



    public void setOccupant(Occupant occupant) { this.occupant = occupant; }

    public Occupant getOccupant() { return occupant; }
}
