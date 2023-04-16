package pl.javastart.jjdind49_katibar_zad25_1;

public enum Category {
    HOME("obowiązki domowe"),
    WORK("praca"),
    ENTERTAINMENT("czas wolny"),
    OTHER("inne");

    private final String pl;

    Category(String pl) {
        this.pl = pl;
    }

    public String getPl() {
        return pl;
    }
}
