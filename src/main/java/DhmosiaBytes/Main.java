package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.Scanner;

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
            for (Role r : Role.values()) {
                System.out.println(r.getCode() + ". " + r.getUsersRole());
            }
            System.out.print("Επιλέξτε την ιδιότητά σας: ");

            try {
                int roleCode = input.nextInt();
                if (roleCode >= MIN_CODE && roleCode <= MAX_CODE) {
                    return Role.fromCode(roleCode);
                }
                System.out.println("Πρέπει να επιλέξετε μία από τις "
                + "παραπάνω ιδιότητες (1-" + MAX_CODE + ")");
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.next();
            }
        }
    }

    /**
     * Main method to execute the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        Scanner input = new Scanner(System.in, "UTF-8");
        LoginService log = new LoginService();

        while (true) {
            Role currentRole = selectRole(input);

            int choice = 0;
            while (choice != 1 && choice != 2) {
                System.out.println("1. Δημιουργία λογαριασμού");
                System.out.println("2. Σύνδεση σε λογαριασμό");
                try {
                    choice = input.nextInt();
                    if (choice != 1 && choice != 2) {
                        System.out.println("Πρέπει να επιλέξετε 1 ή 2");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό:");
                    input.next();
                }
            }

            boolean iAmIn = false;
            if (choice == 1) {
                //Register
                String currentUsername = InputReader.enterValidUsername(input);
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
                System.out.println("Παρακαλώ εισάγετε το username σας: ");
                String currentUsername = input.next();

                User currentUser;
                String currentPassword;
                int counter = MAX_TIMES_CODE;
                while (counter > 0 && !iAmIn) {
                    System.out.println("Παρακαλώ εισάγετε το password σας: ");
                    currentPassword = input.next();
                    currentUser = log.login(currentRole, currentUsername,
                    currentPassword);
                    if (currentUser != null) {
                        System.out.println("Επιτυχής είσοδος");
                        iAmIn = true;
                    } else {
                        System.out.println("Λανθασμένος κωδικός πρόσβασης.");
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
                    continue;
                }
            }
        }
    }
}
