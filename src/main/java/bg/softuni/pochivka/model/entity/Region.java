package bg.softuni.pochivka.model.entity;

import bg.softuni.pochivka.model.enums.RegionEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RegionEnum typeRegion;
    @OneToMany(targetEntity = Town.class, mappedBy = "region")
    private List<Town> towns;

    public Region() {
        this.towns = new ArrayList<>();
    }

    public Region(RegionEnum typeRegion) {
        this.typeRegion = typeRegion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RegionEnum getTypeRegion() {
        return typeRegion;
    }

    public void setTypeRegion(RegionEnum typeRegion) {
        this.typeRegion = typeRegion;
    }

    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }
}
