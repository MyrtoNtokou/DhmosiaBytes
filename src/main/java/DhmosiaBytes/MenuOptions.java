package dhmosiabytes;

/**
 * Enum containing the options of the application's main menu.
 */
public enum MenuOptions {

    /** Option to display the national budget. */
    SHOW_BUDGET(1, "Εμφάνιση Κρατικού Προϋπολογισμού"),

    /** Option to summarize data. */
    SUMMARY(2, "Εμφάνιση Προϋπολογισμού Υπουργείων"),

    /** Option to edit budget details. */
    EDIT_BUDGET(3, "Τροποποίηση Στοιχείων Προϋπολογισμού"),

    /** Option to aggrigate data. */
    AGGRIGATE(4, "Συγκεντρωτικά Στοιχεία"),

    /** Option to compare budgets from different years. */
    COMPARISON(5, "Σύγκριση Κρατικού Προϋπολογισμού Διαφορετικών Ετών"),

    /** Option to display charts. */
    GRAPHS(6, "Γραφήματα"),

    /** Option to exit the application. */
    EXIT(0, "Έξοδος");

    /** Code of the menu option. */
    private final int code;

    /** Description of the menu option. */
    private final String description;

    /**
     * Creates a MenuOptions object with a code and description.
     *
     * @param givenCode the numeric code of the option
     * @param givenDescription the description of the option
     */
    MenuOptions(final int givenCode, final String givenDescription) {
        this.code = givenCode;
        this.description = givenDescription;
    }

    /**
     * Returns the code of the menu option.
     *
     * @return the code of the option
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Returns the description of the menu option.
     *
     * @return the description of the option
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the MenuOptions corresponding to a specific code.
     *
     * @param code the option code
     * @return the corresponding MenuOptions or null if not found
     */
    public static MenuOptions fromCode(final int code) {
        for (MenuOptions opt : MenuOptions.values()) {
            if (opt.getCode() == code) {
                return opt;
            }
        }
        return null;
    }
}
