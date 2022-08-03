package bg.softuni.pochivka.model.enums;

public enum ComfortEnum {
    POOL("открит басейн"),
    RESTAURANT("ресторант"),
    FITNESS("фитнес"),
    PARKING("паркинг"),
    CHILDREN_CORNER("детски кът"),
    BARBEQUE("барбекю"),
    TELEVISION("телевизор"),
    KITCHEN("кухня"),
    INTERNET("интернет"),
    BATHROOM("баня"),
    VC("тоалетна"),
    MICROWAVE("микровълнова"),
    LAUNDRY("пералня"),
    TERRACE("тераса"),
    VIEW("изглед"),
    COFFEE_MACHINE("кафе машина");

    private String value;

    ComfortEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
