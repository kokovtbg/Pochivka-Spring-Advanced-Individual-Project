package bg.softuni.pochivka.model.entity;

import bg.softuni.pochivka.model.enums.TownEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TownEnum name;
    @ManyToOne(optional = false)
    private Region region;
    @OneToMany(targetEntity = Hotel.class, mappedBy = "town")
    private List<Hotel> hotels;

    public Town() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TownEnum getName() {
        return name;
    }

    public void setName(TownEnum name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
