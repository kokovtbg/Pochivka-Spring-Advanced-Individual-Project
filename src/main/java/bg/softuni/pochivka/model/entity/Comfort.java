package bg.softuni.pochivka.model.entity;

import bg.softuni.pochivka.model.enums.ComfortEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comforts")
public class Comfort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ComfortEnum name;
    @ManyToMany(targetEntity = Hotel.class, mappedBy = "comforts")
    private List<Hotel> hotels;

    public Comfort() {
    }

    public Comfort(ComfortEnum name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ComfortEnum getName() {
        return name;
    }

    public void setName(ComfortEnum name) {
        this.name = name;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
