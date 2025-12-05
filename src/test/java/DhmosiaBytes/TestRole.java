package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestRole {

    @Test
    void testGetters() {
        Role role = Role.FINANCE_MINISTER;

        assertEquals(3, role.getCode());
        assertEquals("Υπουργός Οικονομικών", role.getUsersRole());
        assertTrue(role.getCanView());
        assertTrue(role.getCanComment());
        assertTrue(role.getCanEdit());
    }

    @Test
    void testToString() {
        Role role = Role.PRIME_MINISTER;
        assertEquals("Πρωθυπουργός", role.toString());
    }

    @Test
    void testFromCodeValid() {
        assertEquals(Role.PRIME_MINISTER, Role.fromCode(1));
        assertEquals(Role.FINANCE_MINISTRY_EMPLOYEE, Role.fromCode(4));
    }

    @Test
    void testFromCodeInvalid() {
        assertNull(Role.fromCode(999));
        assertNull(Role.fromCode(-1));
    }

    @Test
    void testCanAccessShowBudget() {
        for (Role role : Role.values()) {
            assertEquals(role.getCanView(),
            role.canAccess(MenuOptions.SHOW_BUDGET));
        }
    }

    @Test
    void testCanAccessSummary() {
        for (Role role : Role.values()) {
            assertEquals(role.getCanView(),
            role.canAccess(MenuOptions.SUMMARY));
        }
    }

    @Test
    void testCanAccessGraphs() {
        for (Role role : Role.values()) {
            assertEquals(role.getCanView(),
            role.canAccess(MenuOptions.GRAPHS));
        }
    }

    @Test
    void testCanAccessEditBudget() {
        for (Role role : Role.values()) {
            assertEquals(role.getCanEdit(),
            role.canAccess(MenuOptions.EDIT_BUDGET));
        }
    }

    @Test
    void testCanAccessExitAlwaysTrue() {
        for (Role role : Role.values()) {
            assertTrue(role.canAccess(MenuOptions.EXIT));
        }
    }
}