package bg.softuni.pochivka.model.dto;

import bg.softuni.pochivka.model.enums.Accommodation;
import bg.softuni.pochivka.model.enums.HotelCategory;

import java.math.BigDecimal;
import java.util.List;

public class HotelViewDTO {
    private long id;
    private String name;
    private String website;
    private String information;
    private HotelCategory category;
    private Accommodation accommodation;
    private List<String> images;
    private List<ComfortDTO> comforts;
    private List<RoomDTO> rooms;
    private UserHotelDTO owner;
    private TownDTO town;
    private BigDecimal lowestPrice;


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

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public TownDTO getTown() {
        return town;
    }

    public void setTown(TownDTO town) {
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public List<ComfortDTO> getComforts() {
        return comforts;
    }

    public void setComforts(List<ComfortDTO> comforts) {
        this.comforts = comforts;
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

    public UserHotelDTO getOwner() {
        return owner;
    }

    public void setOwner(UserHotelDTO owner) {
        this.owner = owner;
    }
}
