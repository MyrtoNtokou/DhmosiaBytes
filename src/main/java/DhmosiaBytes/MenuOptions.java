package dhmosiabytes;

/**
 * Enum containing the options of the application's main menu.
 * Some options may have role-dependent behavior.
 */
public enum MenuOptions {

    /** Option to display the national budget. */
    SHOW_BUDGET(1),

    /** Option to summarize data. */
    SUMMARY(2),

    /** Option whose meaning depends on the user's role
     * (edit, request, or comparison).
     */
    ACTION_3(3),

    /** Option to aggrigate data. */
    AGGREGATE(4),

    /** Option to compare budgets from different years. */
    COMPARISON(5),

    /** Option to display charts. */
    GRAPHS(6),

    /** Option to exit the application. */
    EXIT(0);

    /** Code of the menu option. */
    private final int code;

    /**
     * Creates a MenuOptions object with a code and description.
     *
     * @param givenCode the numeric code of the option
     */
    MenuOptions(final int givenCode) {
        this.code = givenCode;
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
