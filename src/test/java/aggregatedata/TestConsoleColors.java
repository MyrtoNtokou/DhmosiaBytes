package aggregatedata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestConsoleColors {

    @Test
    void constants_areNotEmpty() {
        assertNotNull(ConsoleColors.RESET);
        assertNotNull(ConsoleColors.BOLD);
        assertNotNull(ConsoleColors.RED);
        assertNotNull(ConsoleColors.GREEN);
        assertNotNull(ConsoleColors.BLUE);
        assertNotNull(ConsoleColors.CYAN);
        assertNotNull(ConsoleColors.UNDERLINE);

        assertFalse(ConsoleColors.RESET.isEmpty());
        assertFalse(ConsoleColors.BOLD.isEmpty());
        assertFalse(ConsoleColors.RED.isEmpty());
        assertFalse(ConsoleColors.GREEN.isEmpty());
        assertFalse(ConsoleColors.BLUE.isEmpty());
        assertFalse(ConsoleColors.CYAN.isEmpty());
        assertFalse(ConsoleColors.UNDERLINE.isEmpty());
    }

    @Test
    void rgb_returnsCorrectAnsiFormat() throws Exception {
        // Reflection to call private method rgb
        var method = ConsoleColors.class.getDeclaredMethod("rgb", int.class, int.class, int.class);
        method.setAccessible(true);

        String code = (String) method.invoke(null, 255, 0, 128);
        assertEquals("\u001B[38;2;255;0;128m", code);

        code = (String) method.invoke(null, 0, 255, 64);
        assertEquals("\u001B[38;2;0;255;64m", code);
    }
}
