package dhmosiabytes;

import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.RESET;

/**
 * Represents the types of budgets available.
 */
public enum BudgetType {
    /** Represents the regular (operational) budget. */
    TAKTIKOS(1, "Τακτικός Προυπολογισμός"),

    /** Represents the public investments budget. */
    DHMOSION_EPENDYSEON(2, "Προϋπολογισμός Δημοσίων Επενδύσεων");

    /** Numeric code identifying the budget type. */
    private final int typeCode;

    /** Human-readable description of the budget type. */
    private final String typeDescription;

    /**
     * Constructs a BudgetType with the specified code and description.
     *
     * @param code the numeric code of the budget type
     * @param description the human-readable description
     */
    BudgetType(final int code, final String description) {
        typeCode = code;
        typeDescription = description;
    }

     /**
     * Returns the numeric code of the budget type.
     *
     * @return the type code
     */
    public int getTypeCode() {
        return typeCode;
    }
     /**
     * Returns the description of the budget type.
     *
     * @return the type description
     */
    public String getTypeDescription() {
        return typeDescription;
    }
     /**
     * Returns the BudgetType corresponding to the given code.
     *
     * @param typeCode the code to look up
     * @return the matching BudgetType
     * @throws IllegalArgumentException if no matching type is found
     */
    public static BudgetType fromCode(final int typeCode) {
        for (BudgetType typeOpt : BudgetType.values()) {
            if (typeOpt.getTypeCode() == typeCode) {
                return typeOpt;
            }
        }
        throw new IllegalArgumentException(RED + "Μη έγκυρη επιλογή: "
                                            + typeCode + RESET);
    }
}
