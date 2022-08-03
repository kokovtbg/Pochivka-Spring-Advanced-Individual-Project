package bg.softuni.pochivka.model.enums;

public enum Season {
    STRONG_SUMMER("силен летен"),
    STRONG_WINTER("силен зимен"),
    WEAK_SUMMER("слаб летен"),
    WEAK_WINTER("слаб зимен"),
    OTHER("друг");

    private String value;

    Season(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
