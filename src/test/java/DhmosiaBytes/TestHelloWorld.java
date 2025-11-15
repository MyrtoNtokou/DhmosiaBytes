package dhmosiabytes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHelloWorld {

    @Test
    public void testSayHello() {
        assertEquals("Hello, world!", HelloWorld.sayHello());
    }
}
