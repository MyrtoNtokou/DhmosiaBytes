/**
 * Enum representing the roles of users.
 * Each role has specific access rights.
 */
public enum Role {

    PRIME_MINISTER(1, "Πρωθυπουργός", true, true, false),
    PARLIAMENT(2, "Κοινοβούλιο", true, true, false),
    FINANCE_MINISTER(3, "Υπουργός Οικονομικών", true, true, true),
    FINANCE_MINISTRY_EMPLOYEE(4, "Υπάλληλος Υπουργείου Οικονομικών", 
    true, true, false),
    OTHER_MINISTRY(5, "Άλλο Υπουργείο", true, true, false),
    CITIZEN(6, "Πολίτης", true, false, false);

    private final int code;
    private final String usersRole;
    private final boolean canView;
    private final boolean canComment;
    private final boolean canEdit;

    /**
     * Creates the constant objects of the Role enum.
     * 
     * @param code the code of the user's role
     * @param usersRole the name of the role
     * @param canView whether the user has view access
     * @param canComment whether the user can comment
     * @param canEdit whether the user can edit
     */
    private Role(final int code, final String usersRole, 
    final boolean canView, final boolean canComment, final boolean canEdit) {
        this.code = code;
        this.usersRole = usersRole;
        this.canView = canView;
        this.canComment = canComment;
        this.canEdit = canEdit;
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
