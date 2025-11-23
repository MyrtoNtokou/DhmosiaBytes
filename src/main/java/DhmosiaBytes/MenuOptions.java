/**
 * Enum containing the options of the application's main menu.
 */
public enum MenuOptions {
    
    /** Option to display the national budget. */
    SHOW_BUDGET(1, "Εμφάνιση Κρατικού Προϋπολογισμού"),
    
    /** Option to edit budget details. */
    EDIT_BUDGET(2, "Αλλαγή στοιχείων"),
    
    /** Option to summarize data. */
    SUMMARY(3, "Κατηγοριοποίηση στοιχείων"), //εδώ μπορεί να μπει έξτρα επιλογή ανά έτη, ανά χώρα, ανά υπουργείο
    
    /** Option to display charts. */
    GRAPHS(4, "Γραφήματα"),

    /** Option to exit the application. */
    EXIT(5, "Έξοδος");

    /** Code of the menu option. */
    private final int code;

    /** Description of the menu option. */
    private final String description;

    /**
     * Creates a MenuOptions object with a code and description.
     *
     * @param code the numeric code of the option
     * @param description the description of the option
     */
    private MenuOptions(int code, String description) {
        this.code = code;
        this.description = description;
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
    public static MenuOptions fromCode(int code) {
        for (MenuOptions opt : MenuOptions.values()) {
            if (opt.getCode() == code) {
                return opt;
            }
        }
        return null;
    }
}
