package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLoginService {

    private LoginService service;

    @BeforeEach
    void setup() {
        UserDatabase.getDB().clearUsersForTest();
        service = new LoginService();
    }

    @Test
    void testRegisterAndLoginPrimeMinister() {
        String username = "pm_user_test";
        String password = "pm_pass";

        User user = service.register(Role.PRIME_MINISTER, username, password);
        assertNotNull(user, "Πρέπει να δημιουργηθεί ο χρήστης");

        User loginUser = service.login(Role.PRIME_MINISTER, username, password);
        assertNotNull(loginUser, "Πρέπει να γίνει login επιτυχώς");
        assertEquals(Role.PRIME_MINISTER, loginUser.getRole());
    }

    @Test
    void testRegisterAndLoginParliament() {
        String username = "parliament_user_test";
        String password = "parliament_pass";

        User user = service.register(Role.PARLIAMENT, username, password);
        assertNotNull(user);

        User loginUser = service.login(Role.PARLIAMENT, username, password);
        assertNotNull(loginUser);
        assertEquals(Role.PARLIAMENT, loginUser.getRole());
    }

    @Test
    void testRegisterAndLoginFinanceMinister() {
        String username = "finance_user_test";
        String password = "finance_pass";

        User user = service.register(Role.FINANCE_MINISTER, username, password);
        assertNotNull(user);

        User loginUser = service.login(Role.FINANCE_MINISTER, username, password);
        assertNotNull(loginUser);
        assertEquals(Role.FINANCE_MINISTER, loginUser.getRole());
    }

    @Test
    void testRegisterAndLoginOtherMinistry() {
        String username = "other_ministry_test";
        String password = "ministry_pass";

        User user = service.register(Role.OTHER_MINISTRY, username, password);
        assertNotNull(user);

        User loginUser = service.login(Role.OTHER_MINISTRY, username, password);
        assertNotNull(loginUser);
        assertEquals(Role.OTHER_MINISTRY, loginUser.getRole());
    }

    @Test
    void testRegisterDuplicateUser() {
        String username = "duplicate_user_test";
        String password = "dup_pass";

        User user1 = service.register(Role.PRIME_MINISTER, username, password);
        assertNotNull(user1);

        User user2 = service.register(Role.PRIME_MINISTER, username, password);
        assertNull(user2, "Δε μπορεί να δημιουργηθεί χρήστης με ίδιο username");
    }
}
