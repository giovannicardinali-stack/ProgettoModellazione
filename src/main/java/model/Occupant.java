package model;

public abstract class Occupant {

    private String name;
    private boolean isSolid;


    public Occupant(String name, boolean solid) {
        this.name = name;
        this.isSolid = solid;
    }


}
