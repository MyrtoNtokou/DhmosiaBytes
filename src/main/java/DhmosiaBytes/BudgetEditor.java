import java.util.Scanner;
import java.math.BigDecimal;

public class BudgetEditor {
    Scanner scanner = new Scanner(System.in);

    /**
     * Asks user for the new income ammount and replaces the old one.
     *
     * @param code the code of the income to be replaced
     */
    public void editIncome(String code) {
        BigDecimal newAmmount = null;

        while (newAmmount == null) {
            System.out.println("Παρακαλώ εισάγετε το νέο ποσό: ");
            String input = scanner.nextLine();
            try {
                newAmmount = new BigDecimal(input);
                if (newAmmount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Το ποσό δεν μπορεί να είναι αρνητικό.");
                newAmmount = null;
                }             
            } catch (newAmmountFormatException e) {
                System.out.println("Μη έγκυρη τιμή.");
            }

            BudgetService service = new BudgetService(initialBudget, null);
            service.changeGeneralAmmount(code, newAmmount);
            // έλεγχος αν έγινε η αλλαγή
            boolean isValid = service.validateAll();
            System.out.println("Σφάλμα κατά την αλλαγή τιμής.");
        }
    }

    /**
     * Asks user for the new expense ammount and replaces the old one.
     *
     * @param code the code of the expense to be replaced
     */
    public void editExpense(int code) {
        int type = selectBudgetType();
        String column;
        if (type == 1) {
            column = "τακτικός";
        } else {
            column = "πδε";
        }
        BigDecimal newAmmount = null;

        while (newAmmount == null) {
            System.out.print("Παρακαλώ εισάγετε το νέο ποσό: ");
            String input = scanner.nextLine();
            try {
                newAmmount = new BigDecimal(input);
                if (newAmmount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Το ποσό δεν μπορεί να είναι αρνητικό.");
                newAmmount = null;
                }
            } catch (newAmmountFormatException e) {
                System.out.println("Μη έγκυρη τιμή.");
                continue;                
            }

            BudgetService service = new BudgetService(initialBudget, null);
            service.changeMinistryAmmount(code, column, newAmmount);
            // έλεγχος αν έγινε η αλλαγή
            boolean isValid = service.validateAll();                
            System.out.println("Σφάλμα κατά την αλλαγή τιμής.");
        }
    }
}
