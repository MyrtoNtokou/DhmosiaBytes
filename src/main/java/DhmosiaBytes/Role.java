package dhmosiabytes;

/**
 * Enum representing the roles of users.
 * Each role has specific access rights.
 */
public enum Role {

    /**
     * Represents the Prime Minister role.
     * Can view and comment, but cannot edit.
     * Only one Prime Minister exists.
     */
    PRIME_MINISTER(1, "Πρωθυπουργός", true, true, false, true),

    /**
     * Represents the Parliament role.
     * Can view and comment, but cannot edit.
     * There is only one account as Parliament.
     */
    PARLIAMENT(2, "Κοινοβούλιο", true, true, false, true),

    /**
     * Represents the Finance Minister role.
     * Can view, comment, and edit content.
     * Only one Finance Minister exists.
     */
    FINANCE_MINISTER(3, "Υπουργός Οικονομικών", true, true, true, true),

    /**
     * Represents a Finance Ministry Employee.
     * Can view and comment, but cannot edit.
     * There are many employees in the Finance Ministry.
     */
    FINANCE_MINISTRY_EMPLOYEE(4, "Υπάλληλος Υπουργείου Οικονομικών",
    true, true, false, false),

    /**
     * Represents other Ministry employees.
     * Can view and comment, but cannot edit.
     * There are many other Ministries.
     */
    OTHER_MINISTER(5, "Υπουργός άλλου Υπουργείου", true, true, false, false);

    /** The numeric code identifying the user role. */
    private final int code;

    /** The name/description of the user role. */
    private final String usersRole;

    /** Flag indicating whether the user can view/read content. */
    private final boolean canView;

    /** Flag indicating whether the user can comment on content. */
    private final boolean canComment;

    /** Flag indicating whether the user can edit content. */
    private final boolean canEdit;

    /** Flag indicating whether there is only one user whith this role. */
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
        this.canView = view;
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
     * Checks if the user has view access.
     *
     * @return true if the user can view, false otherwise
     */
    public boolean getCanView() {
        return canView;
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
            case SHOW_BUDGET, SUMMARY, GRAPHS, AGGRIGATE, COMPARISON ->
            canView;
            case EDIT_BUDGET -> canEdit;
            case EXIT -> true;
        };
    }
}
