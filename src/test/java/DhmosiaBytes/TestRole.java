package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestRole {

    @Test
    void testGettersForPrimeMinister() {
        Role role = Role.PRIME_MINISTER;

        assertEquals(1, role.getCode());
        assertEquals("Κυβέρνηση", role.getUsersRole());
        assertTrue(role.getCanView());
        assertFalse(role.getCanComment());
        assertFalse(role.getCanEdit());
        assertTrue(role.getThereIsOnlyOne());
    }

    @Test
    void testGettersForOtherMinistry() {
        Role role = Role.OTHER_MINISTRY;

        assertEquals(4, role.getCode());
        assertEquals("Άλλο Υπουργείο", role.getUsersRole());
        assertFalse(role.getCanView());
        assertTrue(role.getCanComment());
        assertFalse(role.getCanEdit());
        assertFalse(role.getThereIsOnlyOne());
    }

    @Test
    void testFromCodeValidCodes() {
        assertEquals(Role.PRIME_MINISTER, Role.fromCode(1));
        assertEquals(Role.PARLIAMENT, Role.fromCode(2));
        assertEquals(Role.FINANCE_MINISTER, Role.fromCode(3));
        assertEquals(Role.OTHER_MINISTRY, Role.fromCode(4));
    }

    @Test
    void testFromCodeInvalidCode() {
        assertNull(Role.fromCode(0));
        assertNull(Role.fromCode(-1));
        assertNull(Role.fromCode(999));
    }

    @Test
    void testToStringReturnsUsersRole() {
        assertEquals("Κυβέρνηση", Role.PRIME_MINISTER.toString());
        assertEquals("Κοινοβούλιο", Role.PARLIAMENT.toString());
        assertEquals("Υπουργείο Οικονομικών", Role.FINANCE_MINISTER.toString());
        assertEquals("Άλλο Υπουργείο", Role.OTHER_MINISTRY.toString());
    }

    @Test
    void testCanAccessAllMenuOptionsForAllRoles() {
        for (Role role : Role.values()) {
            for (MenuOptions option : MenuOptions.values()) {
                assertTrue(
                    role.canAccess(option),
                    () -> "Role " + role + " should access " + option
                );
            }
        }
    }

    @Test
    void testMenuLabelsCommonOptions() {
        Role role = Role.PRIME_MINISTER;

        assertEquals(
            "Εμφάνιση Δημοσιευμένου Κρατικού Προϋπολογισμού",
            role.getMenuLabel(MenuOptions.SHOW_BUDGET)
        );

        assertEquals(
            "Συγκεντρωτικά Στοιχεία",
            role.getMenuLabel(MenuOptions.AGGREGATE)
        );

        assertEquals(
            "Γραφήματα",
            role.getMenuLabel(MenuOptions.GRAPHS)
        );

        assertEquals(
            "Έξοδος",
            role.getMenuLabel(MenuOptions.EXIT)
        );
    }

    @Test
    void testAction3MenuLabelForPrimeMinisterAndParliament() {
        assertEquals(
            "Αξιολόγηση Τροποποιήσεων Προϋπολογισμού",
            Role.PRIME_MINISTER.getMenuLabel(MenuOptions.ACTION_3)
        );

        assertEquals(
            "Αξιολόγηση Τροποποιήσεων Προϋπολογισμού",
            Role.PARLIAMENT.getMenuLabel(MenuOptions.ACTION_3)
        );
    }

    @Test
    void testAction3MenuLabelForFinanceMinister() {
        assertEquals(
            "Τροποποίηση Στοιχείων Προϋπολογισμού",
            Role.FINANCE_MINISTER.getMenuLabel(MenuOptions.ACTION_3)
        );
    }

    @Test
    void testAction3MenuLabelForOtherMinistry() {
        assertEquals(
            "Αιτήματα",
            Role.OTHER_MINISTRY.getMenuLabel(MenuOptions.ACTION_3)
        );
    }
}