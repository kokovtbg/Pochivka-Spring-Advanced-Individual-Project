package bg.softuni.pochivka.model.enums;

public enum Accommodation {
    ONLY_BED("OB - само нощувка(Only Bed)"),
    BED_BREAKFAST("BB - нощувка и закуска(Bed and Breakfast)"),
    HALF_BOARD("HB - нощувка със закуска и вечеря/полупансион(Half Board)"),
    FULL_BOARD("FB - нощувка със закуска, обяд и вечеря/пълен пансион(Full Board)"),
    ALL_INCLUSIVE("AI - ол инклузив(All Inclusive)");

    private String value;

    Accommodation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
