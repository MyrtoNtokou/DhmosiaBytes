package dhmosiabytes;

/**
 * Utility class that provides a greeting.
 */
public final class HelloWorld {

    // Hide constructor for utility class
    private HelloWorld() {
        throw new AssertionError("No instances.");
    }

    /**
     * Returns the hello message.
     *
     * @return the hello message
     */
    public static String sayHello() {
        return "Hello, world!";
    }

    /**
     * Entry point.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        System.out.println(sayHello());
    }
}
