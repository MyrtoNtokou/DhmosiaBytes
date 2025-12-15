package dhmosiabytes;

public enum BudgetType{
    TAKTIKOS(1 , "Τακτικός Προυπολογισμός"),
    DHMOSION_EPENDYSEON(2, "Προϋπολογισμός Δημοσίων Επενδύσεων");
    private final int typeCode;
    private final String typeDescription;

    BudgetType(final int code, final String description) {
        typeCode = code;
        typeDescription = description;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public static BudgetType fromCode(final int typeCode) {
        for (BudgetType typeOpt : BudgetType.values()) {
            if (typeOpt.getTypeCode() == typeCode) {
                return typeOpt;
            }
        }
        throw new IllegalArgumentException("Μη έγκυρη επιλογή: " + typeCode);
    }
}
