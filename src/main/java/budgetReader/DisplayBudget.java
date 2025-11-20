package budgetreader;

import java.util.List;

public final class DisplayBudget {
    
    private DisplayBudget() {}

    public static <T> void displayList(List<T> list) {
        list.forEach(System.out::println);
    }

    public static void showGeneral(List<Eggrafi> eggrafes) {
        System.out.println("\n=== ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ===");
        displayList(eggrafes);
    }

    public static void showMinistry(List<Ypourgeio> ypourg) {
        System.out.println("\n=== ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ΑΝΑ ΥΠΟΥΡΓΕΙΟ ===");
        displayList(ypourg);
    }
}
