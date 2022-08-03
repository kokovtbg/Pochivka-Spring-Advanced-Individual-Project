package bg.softuni.pochivka.model.dto;

import bg.softuni.pochivka.model.enums.RoomType;
import bg.softuni.pochivka.model.enums.Season;

import java.math.BigDecimal;

public class RoomDTO {
    private RoomType roomType;
    private Season season;
    private BigDecimal price;

    public RoomDTO() {
    }

    public RoomDTO(RoomType roomType, Season season, BigDecimal price) {
        this.roomType = roomType;
        this.season = season;
        this.price = price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
