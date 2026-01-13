package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestLoginService {

    private LoginService loginService;
    private Map<String, User> users;

    @BeforeEach
    void setUp() {
        users = new HashMap<>();

        loginService = new LoginService() {
            @Override
            public User login(Role role, String username, String password) {
                User currentUser = users.get(username);
                if (currentUser == null) return null;
                if (!currentUser.checkPassword(password) || !currentUser.getRole().equals(role)) return null;
                return currentUser;
            }

            @Override
            public User register(Role role, String username, String password) {
                if (users.containsKey(username)) return null;
                User newUser = new User(role, username, password);
                users.put(username, newUser);
                return newUser;
            }
        };
    }

    @Test
    void testSuccessfulLogin() {
        users.put("pm", new User(Role.PRIME_MINISTER, "pm", "1234"));

        User result = loginService.login(Role.PRIME_MINISTER, "pm", "1234");
        assertNotNull(result);
        assertEquals("pm", result.getUsername());
    }

    @Test
    void testLoginFailsWrongPassword() {
        users.put("parl", new User(Role.PARLIAMENT, "parl", "pass"));

        User result = loginService.login(Role.PARLIAMENT, "parl", "wrong");
        assertNull(result);
    }

    @Test
    void testLoginFailsWrongRole() {
        users.put("fin", new User(Role.FINANCE_MINISTER, "fin", "123"));

        User result = loginService.login(Role.OTHER_MINISTRY, "fin", "123");
        assertNull(result);
    }

    @Test
    void testSuccessfulRegister() {
        User result = loginService.register(Role.OTHER_MINISTRY, "min1", "pwd");
        assertNotNull(result);
        assertEquals("min1", result.getUsername());
    }

    @Test
    void testRegisterFailsDuplicate() {
        users.put("dup", new User(Role.PARLIAMENT, "dup", "123"));

        User result = loginService.register(Role.PRIME_MINISTER, "dup", "456");
        assertNull(result);
    }
}
