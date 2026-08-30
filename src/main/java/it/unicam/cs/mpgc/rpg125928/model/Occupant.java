package it.unicam.cs.mpgc.rpg125928.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Occupant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected String name;
    protected boolean isSolid;

    @Embedded
    protected Coordinates coordinates;


    public Occupant(String name, boolean solid) {
        this.name = name;
        this.isSolid = solid;
    }

    public Occupant() {}

    public boolean isSolid() {
        return isSolid;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}