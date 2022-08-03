package bg.softuni.pochivka.model.entity;

import bg.softuni.pochivka.model.enums.Accommodation;
import bg.softuni.pochivka.model.enums.ComfortEnum;
import bg.softuni.pochivka.model.enums.HotelCategory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    private String website;
    @Column(nullable = false, columnDefinition = "text")
    private String information;
    @Enumerated(EnumType.STRING)
    private HotelCategory category;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Accommodation accommodation;
    @Column(nullable = false)
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> images;
    @ManyToMany
    private List<Comfort> comforts;
    @OneToMany(targetEntity = Room.class, mappedBy = "hotel")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Room> rooms;
    @ManyToOne(optional = false)
    private User owner;
    @ManyToOne(optional = false)
    private Town town;

    public Hotel() {
        this.images = new ArrayList<>();
        this.comforts = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public HotelCategory getCategory() {
        return category;
    }

    public void setCategory(HotelCategory category) {
        this.category = category;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Comfort> getComforts() {
        return comforts;
    }

    public void setComforts(List<Comfort> comforts) {
        this.comforts = comforts;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
