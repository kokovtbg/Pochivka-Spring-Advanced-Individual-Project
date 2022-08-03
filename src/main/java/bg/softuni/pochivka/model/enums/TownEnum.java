package bg.softuni.pochivka.model.enums;

public enum TownEnum {
    AHTOPOL("Ахтопол"),
    KITEN("Китен"),
    LOZENEC("Лозенец"),
    NESEBAR("Несебър"),
    POMORIE("Поморие"),
    PRIMORSKO("Приморско"),
    RAVDA("Равда"),
    SVETI_VLAS("Свети Влас"),
    SINEMOREC("Синеморец"),
    SLANCHEV_BRYAG("Слънчев Бряг"),
    SOZOPOL("Созопол"),
    CAREVO("Царево"),

    RAZLOG("Разлог"),
    BANSKO("Банско"),
    SANDANSKI("Сандански"),
    PAMPOROVO("Пампорово"),
    VELINGRAD("Велинград"),
    DEVIN("Девин"),
    BOROVEC("Боровец"),
    VELIKO_TARNOVO("Велико Търново"),
    ELENA("Елена"),
    SOFIA("София"),

    VIDIN("Видин"),
    PLOVDIV("Пловдив"),
    STARA_ZAGORA("Стара Загора"),
    HASKOVO("Хасково"),
    KARDZHALI("Кърджали"),
    RUSE("Русе");

    private String value;

    TownEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
