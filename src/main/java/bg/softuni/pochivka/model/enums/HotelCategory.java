package bg.softuni.pochivka.model.enums;

public enum HotelCategory {
    ONE_STAR("Една звезда"),
    TWO_STAR("Две звезди"),
    THREE_STAR("Три звезди"),
    FOUR_STAR("Четири звезди"),
    FIVE_STAR("Пет звезди");

    private String value;

    HotelCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
