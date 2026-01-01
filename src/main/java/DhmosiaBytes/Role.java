package dhmosiabytes;

/**
 * Enum representing the roles of users.
 * Each role has specific access rights.
 */
public enum Role {

    /**
     * Represents the Prime Minister role.
     * Can view the compared budgets, but cannot comment or edit.
     * Only one Governance exists.
     */
    PRIME_MINISTER(1, "Κυβέρνηση", true, false, false, true),

    /**
     * Represents the Parliament role.
     * Can view the compared budgets, but cannot comment or edit.
     * There is only one account as Parliament.
     */
    PARLIAMENT(2, "Κοινοβούλιο", true, false, false, true),

    /**
     * Represents the Finance Minister role.
     * Cannot view the compared budgets (not through the main menu),
     * and cannot comment. They can only edit content.
     * Only one Finance Ministry exists.
     */
    FINANCE_MINISTER(3, "Υπουργείο Οικονομικών", false, false, true, true),

    /**
     * Represents a Ministry other than the Finance.
     * Cannot view th compared budgets (not through the main menu) or edit,
     * but they can submit comments.
     * There are can be many Ministries.
     */
    OTHER_MINISTRY(4, "Άλλο Υπουργείο", false, true, false, false);

    /** The numeric code identifying the user role. */
    private final int code;

    /** The name/description of the user role. */
    private final String usersRole;

    /** Flag indicating whether the user can view the compared budgets. */
    private final boolean canViewComparison;

    /** Flag indicating whether the user can comment on content. */
    private final boolean canComment;

    /** Flag indicating whether the user can edit content. */
    private final boolean canEdit;

    /** Flag indicating whether there is only one user with this role. */
    private final boolean thereIsOnlyOne;

    /**
     * Creates the constant objects of the Role enum.
     *
     * @param roleCode the user's role
     * @param userRole the name of the role
     * @param view whether the user has view access
     * @param comment whether the user can comment
     * @param edit whether the user can edit
     * @param onlyOne whether there is only one user with this role or not
     */
    Role(final int roleCode, final String userRole,
    final boolean view, final boolean comment, final boolean edit,
    final boolean onlyOne) {
        this.code = roleCode;
        this.usersRole = userRole;
        this.canViewComparison = view;
        this.canComment = comment;
        this.canEdit = edit;
        this.thereIsOnlyOne = onlyOne;
    }

    /**
     * Returns the code of the user's role.
     *
     * @return the role code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Returns the name of the user's role.
     *
     * @return the role name
     */
    public String getUsersRole() {
        return this.usersRole;
    }

    /**
     * Checks if the user can view the compared budgets.
     *
     * @return true if the user can view, false otherwise
     */
    public boolean getCanView() {
        return canViewComparison;
    }

    /**
     * Checks if the user has permission to comment.
     *
     * @return true if the user can comment, false otherwise
     */
    public boolean getCanComment() {
        return canComment;
    }

    /**
     * Checks if the user has permission to edit.
     *
     * @return true if the user can edit, false otherwise
     */
    public boolean getCanEdit() {
        return canEdit;
    }

    /**
     * Checks if there can be only one user with this role.
     *
     * @return true if there can be only one user with this role
     */
    public boolean getThereIsOnlyOne() {
        return thereIsOnlyOne;
    }

    /**
     * Returns the Role corresponding to a given code.
     *
     * @param code the code of the user's role
     * @return the Role object, or null if not found
     */
    public static Role fromCode(final int code) {
        for (Role opt : Role.values()) {
            if (opt.getCode() == code) {
                return opt;
            }
        }
        return null;
    }

    /**
     * Returns the name of the user's role.
     *
     * @return the role name
     */
    @Override
    public String toString() {
        return usersRole;
    }

    /**
     * Decides if this role has access to a specific menu option.
     *
     * @param option the menu option
     * @return true if this role has access to the given option
     * or false if not
     */
    public boolean canAccess(final MenuOptions option) {
        return switch (option) {
            case SHOW_BUDGET, SUMMARY, GRAPHS, AGGREGATE -> true;
            case COMPARISON -> canViewComparison;
            case ACTION_3 -> true;
            case EXIT -> true;
        };
    }

    /**
     * Returns the text label of a menu option as it should appear to the user,
     * taking into account the role of the user.
     *
     * @param option the menu option whose label should be returned
     * @return the text label to display for the given menu option and role
     */
    public String getMenuLabel(final MenuOptions option) {
        return switch (option) {

            case SHOW_BUDGET ->
                "Εμφάνιση Κρατικού Προϋπολογισμού";
            case SUMMARY ->
                "Εμφάνιση Προϋπολογισμού Υπουργείων";
            case ACTION_3 -> switch (this) {
                case PRIME_MINISTER, PARLIAMENT ->
                    "Σύγκριση Τρέχοντος και Τροποποιημένου Προϋπολογισμού";
                case FINANCE_MINISTER ->
                    "Τροποποίηση Στοιχείων Προϋπολογισμού";
                case OTHER_MINISTRY ->
                    "Υποβολή Αιτημάτων";
            };
            case AGGREGATE ->
                "Συγκεντρωτικά Στοιχεία";
            case COMPARISON ->
                "Σύγκριση Κρατικού Προϋπολογισμού Διαφορετικών Ετών";
            case GRAPHS ->
                "Γραφήματα";
            case EXIT ->
                "Έξοδος";
        };
    }
}
