import java.util.Scanner;

public class MainMenuOptions {

    public static void main(String[] args) {

    //εκτέλεσε την ενέργεια που ζητάει ο χρήστης
    MenuOptions choice;
    Scanner input = new Scanner(System.in);
    do {

        //εμφάνιση μενού επιλογών
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

    //Υποτυπώδεις μέθοδη
    public static void showBudget() {System.out.println("Βλέπεις τον Κρατικό Προϋπολογισμό"); }
    public static void editBudget() {System.out.println("Τώρα επεξεργάζεσαι τον Κρατικό Προϋπολογισμό"); }
    public static void summary() {System.out.println("Τώρα βλέπεις συγκεντρωτικά στοιχεία"); }
    public static void graphs() {System.out.println("Τώρα βλέπεις τα γραφήματα"); }
}