package bg.softuni.pochivka.model.enums;

public enum RoomType {
    SINGLE("единична"),
    DOUBLE("двойна"),
    TRIPLE("тройна"),
    QUAD("четворна"),
    APARTMENT("апартамент");

    private String value;

    RoomType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
