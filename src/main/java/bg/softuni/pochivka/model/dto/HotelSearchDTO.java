package bg.softuni.pochivka.model.dto;

import bg.softuni.pochivka.model.enums.Accommodation;

import java.util.List;

public class HotelSearchDTO {
    private String name;
    private String townName;
    private List<String> accommodation;
    private List<String> category;
    private List<String> comfort;
    private String sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(List<String> accommodation) {
        this.accommodation = accommodation;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getComfort() {
        return comfort;
    }

    public void setComfort(List<String> comfort) {
        this.comfort = comfort;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
