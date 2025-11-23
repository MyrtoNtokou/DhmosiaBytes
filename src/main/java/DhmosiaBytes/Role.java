/**
 * Enum representing the roles of users.
 * Each role has specific access rights.
 */
public enum Role {

    /**
     * Represents the Prime Minister role.
     * Can view and comment, but cannot edit.
     */
    PRIME_MINISTER(1, "Πρωθυπουργός", true, true, false),

    /**
     * Represents the Parliament role.
     * Can view and comment, but cannot edit.
     */
    PARLIAMENT(2, "Κοινοβούλιο", true, true, false),

    /**
     * Represents the Finance Minister role.
     * Can view, comment, and edit content.
     */
    FINANCE_MINISTER(3, "Υπουργός Οικονομικών", true, true, true),

    /**
     * Represents a Finance Ministry Employee.
     * Can view and comment, but cannot edit.
     */
    FINANCE_MINISTRY_EMPLOYEE(4, "Υπάλληλος Υπουργείου Οικονομικών",
    true, true, false),

    /**
     * Represents other Ministry employees.
     * Can view and comment, but cannot edit.
     */
    OTHER_MINISTRY(5, "Άλλο Υπουργείο", true, true, false),

    /**
     * Represents a regular Citizen.
     * Can view content only, cannot comment or edit.
     */
    CITIZEN(6, "Πολίτης", true, false, false);

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

    /**
     * Creates the constant objects of the Role enum.
     *
     * @param roleCode the user's role
     * @param userRole the name of the role
     * @param view whether the user has view access
     * @param comment whether the user can comment
     * @param edit whether the user can edit
     */
    Role(final int roleCode, final String userRole,
    final boolean view, final boolean comment, final boolean edit) {
        this.code = roleCode;
        this.usersRole = userRole;
        this.canView = view;
        this.canComment = comment;
        this.canEdit = edit;
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
}
