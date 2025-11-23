import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Scanner; 

/**
 * Main class to start the application for user management.
 */
public class Main{
    
    /** Minimum and maximum code values for user roles */
    private static final int MIN_CODE = 1;
    private static final int MAX_CODE = Role.values().length;

   /**
     * Allows the user to select their role.
     * 
     * @param input Scanner object for reading input
     * @return the selected Role
     */
    private static Role selectRole(Scanner input) {
        for (Role r : Role.values()) {
            System.out.println(r.getCode() + ". " + r.getUsersRole());
        }
        System.out.println("Επιλέξτε την ιδιότητά σας: ");
        int roleCode = input.nextInt();
        while (roleCode < MIN_CODE || roleCode > MAX_CODE) {
            System.out.println("Πρέπει να επιλέξετε μία από τις " 
            + "παραπάνω ιδιότητες (1-" + MAX_CODE + ")");
            roleCode = input.nextInt();
        }
        return Role.fromCode(roleCode);
    }

    /**
     * Main method to execute the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in, "UTF-8");
        LoginService log = new LoginService();
        
        /** User picks their Role */
        Role currentRole = selectRole(input);

        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.println("1. Δημιουργία λογαριασμού");
            System.out.println("2. Σύνδεση σε λογαριασμό");
            try {
                choice = input.nextInt();
                if(choice != 1 && choice != 2) {
                    System.out.println("Πρέπει να επιλέξετε 1 ή 2");
                }
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό:");
                input.next();
            }
        }
        
        if (choice == 1) {
            /** Register */
            String currentUsername = InputReader.enterValidUsername(input);
            if (UserDatabase.getDB().findUser(currentUsername) != null) {
                System.out.println("Υπάρχει ήδη λογαριασμός " 
                + "με αυτό το username");
            } else {
                String currentPassword = InputReader.enterValidPassword(input);
                User currentUser = log.register(currentRole, 
                currentUsername, currentPassword);
                if (currentUser != null) {
                    System.out.println("Επιτυχής δημιουργία λογαριασμού");
                } else {
                    System.out.println("Υπάρχει ήδη λογαριασμός " 
                    + "με αυτό το username");
                }
            }
        } else {
            /** Login */
            System.out.println("Παρακαλώ εισάγετε το username σας: ");
            String currentUsername = input.next();
            
            User currentUser;
            String currentPassword;
            boolean iAmIn = false;
            int counter = 3;
            while (counter > 0 && !iAmIn) {
                System.out.println("Παρακαλώ εισάγετε το password σας: ");
                currentPassword = input.next();
                currentUser = log.login(currentUsername, 
                currentPassword);
                if (currentUser != null) {
                    System.out.println("Επιτυχής είσοδος");
                    iAmIn = true;
                } else {
                    System.out.println("Λανθασμένος κωδικός πρόσβασης.");
                    counter--;
                    System.out.println("Σας απομένουν " + counter + 
                    " προσπάθειες.");
                }
            }
        }
    }
}
