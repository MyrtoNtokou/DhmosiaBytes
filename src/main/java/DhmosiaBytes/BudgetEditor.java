import java.util.Scanner;
import java.math.BigDecimal;

import budgetlogic.BudgetService;
import dhmosiabytes.ShowEditMenuOptions;

public class BudgetEditor {
    /** BudgetService object. */
    private final BudgetService service;

    public BudgetEditor(finale BudgetService serv) {
        this.service = serv;
    }

    ShowEditMenuOptions editMenu = new ShowEditMenuOptions();

    /**
     * Asks user for the new income ammount and replaces the old one.
     *
     * @param code the code of the income to be replaced
     */
    public void editIncome(final String code, final Budget initialBudget,
    final Scanner scanner) {
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
    public void editExpense(final int code, final Budget initialBudget,
    final Scanner scanner) {
        int type = editMenu.selectBudgetType(scanner);
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
