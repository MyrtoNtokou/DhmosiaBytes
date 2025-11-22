package budgetreader;

import java.util.List;

/**
 * Utility class for displaying budget data.
 */
public final class DisplayBudget {
   
    /**
     * Private constructor to prevent object creation.
     */
    private DisplayBudget() {}

    /** Prints the General Budget List on the screen */
    public static void showGeneral(List<Eggrafi> eggrafes) {
        System.out.println("\n=== ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ===");
        
        /* Print each record in the list */
        for (Eggrafi e : eggrafes) {
            System.out.println(e);
        }
    }

    /** Prints the Ministry Budget List on the screen */
    public static void showMinistry(List<Ypourgeio> ypourg) {
        System.out.println("\n=== ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ΑΝΑ ΥΠΟΥΡΓΕΙΟ ===");
        
        /* Print each record in the list*/
        for (Ypourgeio y : ypourg) {
            System.out.println(y);
        }
    }
}
