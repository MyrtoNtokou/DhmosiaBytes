package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLoginService {

    private LoginService loginService;
    private UserDatabase db;
    private Map<String, User> usersMap;

    @BeforeEach
    void setUp() throws Exception {
        db = UserDatabase.getDB();

        // 🔥 Αντικατάσταση του internal map (ΧΩΡΙΣ save/load)
        Field usersField = UserDatabase.class.getDeclaredField("users");
        usersField.setAccessible(true);

        usersMap = new HashMap<>();
        usersField.set(db, usersMap);

        loginService = new LoginService();
    }

    @Test
    void successfulLogin() {
        User user =
            new User(Role.PRIME_MINISTER, "pm", "1234");
        usersMap.put("pm", user);

        User result =
            loginService.login(Role.PRIME_MINISTER, "pm", "1234");

        assertNotNull(result);
        assertEquals(Role.PRIME_MINISTER, result.getRole());
    }

    @Test
    void loginFailsWrongPassword() {
        usersMap.put(
            "parl",
            new User(Role.PARLIAMENT, "parl", "pass")
        );

        User result =
            loginService.login(Role.PARLIAMENT, "parl", "wrong");

        assertNull(result);
    }

    @Test
    void loginFailsWrongRole() {
        usersMap.put(
            "fin",
            new User(Role.FINANCE_MINISTER, "fin", "123")
        );

        User result =
            loginService.login(Role.OTHER_MINISTRY, "fin", "123");

        assertNull(result);
    }

    @Test
    void loginFailsUserNotFound() {
        User result =
            loginService.login(Role.PRIME_MINISTER, "ghost", "123");

        assertNull(result);
    }

    @Test
    void successfulRegister() {
        User result =
            loginService.register(
                Role.OTHER_MINISTRY, "min1", "pwd");

        assertNotNull(result);
        assertTrue(usersMap.containsKey("min1"));
    }

    @Test
    void registerFailsDuplicateUsername() {
        usersMap.put(
            "dup",
            new User(Role.PARLIAMENT, "dup", "123")
        );

        User result =
            loginService.register(
                Role.PRIME_MINISTER, "dup", "456");

        assertNull(result);
    }
}
