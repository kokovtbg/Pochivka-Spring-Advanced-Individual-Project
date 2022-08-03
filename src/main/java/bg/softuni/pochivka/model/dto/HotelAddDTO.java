package bg.softuni.pochivka.model.dto;


import bg.softuni.pochivka.model.enums.RoomType;
import bg.softuni.pochivka.model.enums.Season;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HotelAddDTO {
    @NotBlank(message = "Името е задължително")
    @Size(max = 45, message = "Хотелът не може да има по-дълго от 45 символа име")
    private String name;
    @Size(max = 67, message = "Уебсайтът трябва да бъде с максимално 67 символа")
    private String website;
    @NotBlank(message = "Хотелът трябва да има информация")
    @Size(min = 15, message = "Информацията е с минимум 15 символа")
    private String information;
    private String category;
    @Pattern(regexp = "^([^no]\\w+)$", message = "Хотелът трябва да има тип на настаняване")
    private String accommodation;
    @NotBlank(message = "Хотелът трябва да има поне една снимка")
    private String images;
    private List<String> comforts;
    private List<RoomDTO> rooms;
    @Pattern(regexp = "^([^no]\\w+)$", message = "Хотелът трябва да има град")
    private String town;

    public HotelAddDTO() {
        this.rooms = new ArrayList<>();
        RoomType[] roomTypes = RoomType.values();
        Season[] seasons = Season.values();
        for (RoomType roomType : roomTypes) {
            for (Season season : seasons) {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setRoomType(roomType);
                roomDTO.setSeason(season);
                this.rooms.add(roomDTO);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<String> getComforts() {
        return comforts;
    }

    public void setComforts(List<String> comforts) {
        this.comforts = comforts;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
