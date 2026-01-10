package dhmosiabytes;

import java.util.Scanner;
import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.BLUE;
import static aggregatedata.ConsoleColors.BOLD;

/**
 * Main class to start the application for user management.
 */
public final class Main {

    /** Minimum code value for user roles. */
    private static final int MIN_CODE = 1;

    /** Maximum code value for user roles. */
    private static final int MAX_CODE = Role.values().length;

    /** Maximum times a user can enter a password. */
    private static final int MAX_TIMES_CODE = 3;

    /** Code to exit the program. */
    private static final int CODE_FOR_EXIT = 0;

    /**
     * Private constractor so no objects will be created.
     */
    private Main() { }

   /**
     * Allows the user to select their role.
     *
     * @param input Scanner object for reading input
     * @return the selected Role
     */
    private static Role selectRole(final Scanner input) {
        while (true) {
            System.out.println("\nΕπιλέξτε μία από τις παρακάτω ιδιότητες:");
            System.out.println();
            for (Role r : Role.values()) {
                System.out.println(r.getCode() + ". " + r.getUsersRole());
            }
            System.out.println("0. Έξοδος");
            System.out.print("Επιλογή: ");

            try {
                int roleCode = Integer.parseInt(input.nextLine());
                if (roleCode >= MIN_CODE && roleCode <= MAX_CODE) {
                    return Role.fromCode(roleCode);
                } else if (roleCode == 0) {
                    return null;
                }
                System.out.println("Πρέπει να επιλέξετε μία από τις "
                + "παραπάνω ιδιότητες (1-" + MAX_CODE + ")");
            } catch (NumberFormatException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
            }
        }
    }

    /**
     * Main method to execute the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {

        System.out.println(BLUE + BOLD + "  ____  _   _ ____   ____ _____ _____   "
        + "      _          _      ");
        System.out.println(BLUE + BOLD + " | __ )| | | |  _ \\ / ___| ____|_   _|  "
        + "    _| |_      _| |_    ");
        System.out.println(BLUE + BOLD + " |  _ \\| | | | | | | |  _|  _|   | |    "
        + "   |_   _|    |_   _|   ");
        System.out.println(BLUE + BOLD + " | |_) | |_| | |_| | |_| | |___  | |    "
        + "     |_|        |_|     ");
        System.out.println(BLUE + BOLD + " |____/ \\___/|____/ \\____|_____| |_|    "
        + "                        " + RESET);
        System.out.println();

        Scanner input = new Scanner(System.in, "UTF-8");
        LoginService log = new LoginService();

        boolean running = true;
        while (running) {
            Role currentRole = selectRole(input);
            if (currentRole == null) {
                System.out.println("\nΈξοδος από την εφαρμογή");
                break;
            }

            int choice = -1;
            boolean validChoice = false;
            while (!validChoice) {
                System.out.println("\n1. Δημιουργία λογαριασμού");
                System.out.println("2. Σύνδεση σε λογαριασμό");
                System.out.println("0. Έξοδος");
                System.out.print("Επιλογή: ");
                String line = input.nextLine();
                try {
                    choice = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό:");
                    continue;
                }

                switch (choice) {
                    case CODE_FOR_EXIT -> {

                    System.out.println("\nΈξοδος από την εφαρμογή");

                    running = false;
                    validChoice = true;
                    }
                    case 1, 2 -> {
                        validChoice = true;
                    }
                    default -> {
                        System.out.println("Πρέπει να επιλέξετε 1, 2 ή 0");
                    }
                }
            }

            if (!running) {
                break;
            }

            boolean iAmIn = false;
            if (choice == 1) {
                //Register
                String currentUsername = InputReader.enterValidUsername(input);

                if (currentRole.getThereIsOnlyOne()) {
                    boolean exists =
                    UserDatabase.getDB().getUsers().values().stream()
                    .anyMatch(u -> u.getRole() == currentRole);
                    if (exists) {
                        System.out.println("Υπάρχει ήδη χρήστης με τον ρόλο: "
                        + currentRole);
                        continue; // Επιστροφή στην αρχή του loop
                    }
                }
                if (UserDatabase.getDB().findUser(currentUsername) != null) {
                    System.out.println("Υπάρχει ήδη λογαριασμός "
                    + "με αυτό το username");
                } else {
                    String currentPassword =
                    InputReader.enterValidPassword(input);
                    User currentUser = log.register(currentRole,
                    currentUsername, currentPassword);
                    if (currentUser != null) {
                        System.out.println("Επιτυχής δημιουργία λογαριασμού");
                        iAmIn = true;
                    } else {
                        System.out.println("Υπάρχει ήδη λογαριασμός "
                        + "με αυτό το username");
                    }
                }
            } else {
                // Login
                System.out.println("\nΠαρακαλώ εισάγετε το username σας: ");
                String currentUsername = input.nextLine();

                if (UserDatabase.getDB().findUser(currentUsername) == null) {
                    System.out.println("Δεν υπάρχει χρήστης"
                    + " με αυτό το username.");
                    continue;
                }

                User currentUser;
                String currentPassword;
                int counter = MAX_TIMES_CODE;
                while (counter > 0 && !iAmIn) {
                    System.out.println("Παρακαλώ εισάγετε το password σας: ");
                    currentPassword = input.nextLine();
                    currentUser = log.login(currentRole, currentUsername,
                    currentPassword);
                    if (currentUser != null) {
                        System.out.println("Επιτυχής είσοδος");
                        iAmIn = true;
                    } else {
                        counter--;
                        System.out.println("Σας απομένουν " + counter
                        + " προσπάθειες.");
                    }
                }
            }

            if (iAmIn) {
                boolean exit = ShowMenuOptions.showMenu(currentRole, input);

                if (exit) {
                    System.out.println("Αποσύνδεση");
                }
            }
        }
    }
}
