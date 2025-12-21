package dhmosiabytes;

public enum TypeOptions{
    TAKTIKOS(1 , "Τακτικός Προυπολογισμός"),
    DHMOSION_EPENDYSEON(2, "Προϋπολογισμός Δημοσίων Επενδύσεων");
    private final int typeCode;
    private final String typeDescription;

    TypeOptions(final int givenTypeCode, final String givenTypeDescription) {
        this.typeCode = givenTypeCode;
        this.typeDescription = givenTypeDescription;
    }

    public int getTypeCode() {
        return this.typeCode;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public static TypeOptions TypeOption(final int typeCode) {
        for (TypeOptions typeOpt : TypeOptions.values()) {
            if (typeOpt.getTypeCode() == typeCode) {
                return typeOpt;
            }
        }
        return null;
    }
}
