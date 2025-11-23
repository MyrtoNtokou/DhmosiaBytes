import java.util.Scanner;

/**
 * Class that displays the application's main menu 
 * and executes the corresponding user actions.
 */
public class MainMenuOptions {

    /**
     * Main method to run the MenuOptions.
     * Shows the menu options and executes the action chosen by the user.
     * 
     * @param args command line arguments
     */
    public static void main( final String[] args) {
        MenuOptions choice;
        Scanner input = new Scanner(System.in, "UTF-8");
        do {
            /** Display menu options */
            for (MenuOptions opt : MenuOptions.values()) {
                System.out.println(opt.getCode() + ". " + opt.getDescription());
            }

            System.out.println("Επιλογή: ");
            choice = MenuOptions.fromCode(input.nextInt());
        
            switch(choice) {
                case SHOW_BUDGET -> showBudget();
                case EDIT_BUDGET -> editBudget();
                case SUMMARY -> summary();
                case GRAPHS -> graphs();
                case EXIT -> System.out.println("Έξοδος από εφαρμογή");
            }
        } while (choice != MenuOptions.EXIT);
    }

    /** Displays the national budget. */
    public static void showBudget() {
        System.out.println("Βλέπεις τον Κρατικό Προϋπολογισμό");
    }

    /** Edits the national budget. */
    public static void editBudget() {
        System.out.println("Τώρα επεξεργάζεσαι τον Κρατικό Προϋπολογισμό");
    }

    /** Shows summarized data. */
    public static void summary() {
        System.out.println("Τώρα βλέπεις συγκεντρωτικά στοιχεία");
    }

    /** Displays charts for the user. */
    public static void graphs() {
        System.out.println("Τώρα βλέπεις τα γραφήματα");
    }
}
