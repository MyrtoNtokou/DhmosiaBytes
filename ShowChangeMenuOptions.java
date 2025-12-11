package dhmosiabytes;

import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;

import budgetlogic.AllocationMapping;
import budgetlogic.BasicRecord;
import budgetlogic.Budget;
import budgetlogic.BudgetLoader;
import budgetlogic.BudgetService;
import budgetlogic.BudgetWriter;
import budgetlogic.Ministry;

public class ShowChangeMenuOptions {
    private ShowChangeMenuOptions() { }
    public static void ShowChangeMenu() {
        for (ChangeOptions opt : ChangeOptions.values()) {
            System.out.println(opt.getChangeCode() + " - " + opt.getChangeDescription());
        }
        Scanner scanner = new Scanner(System.in);
        ChangeOptions selected = null;
        while (selected == null) {
            System.out.print("Δώσε επιλογή");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                selected = ChangeOptions.CodeOption(choice);
                if (selected == null) {
                    System.out.println("Μη έγκυρη επιλογή");
                }
            }  else {
            System.out.println("Πρέπει να δώσεις έγκυρη επιλογή.");
            scanner.next();
        }
        }
        if (selected == ChangeOptions.INCOME) {
            IncomeDifference();
        } else if (selected == ChangeOptions.EXPENSE) {
            ExpenseDifference();
        } 
    }
    public String ShowIncomeMenu() {
    for (IncomeOptions opt : IncomeOptions.values()) {
        System.out.println(opt.getIncomeCode() + " - " + opt.getIncomeDescription());
    }
    Scanner scanner1 = new Scanner(System.in);
    IncomeOptions selected1 = null;
    while (selected1 == null) {
        System.out.print("Δώσε επιλογή");
        String choice1 = scanner1.nextLine().trim();

        selected1 = IncomeOptions.IncomeOption(choice1);

        if (selected1 == null) {
            System.out.println("Μη έγκυρη επιλογή");
        }
    }
    return selected1.getIncomeCode(); 
    }
    public int ShowExpenseMenu() {
        for (ExpenseOptions opt : ExpenseOptions.values()) {
            System.out.println(opt.getExpenseCode() + " - " + opt.getExpenseDescription());
        }
        Scanner scanner2 = new Scanner(System.in);
        ExpenseOptions selectedOption = null;
        while (selectedOption == null) {
            System.out.print("Δώσε επιλογή");
            if (scanner2.hasNextInt()) {
                int choice1 = scanner2.nextInt();
                selectedOption = ExpenseOptions.ExpenseOption(choice1);
                if (selectedOption == null) {
                    System.out.println("Μη έγκυρη επιλογή");
                }
            } else {
                System.out.println("Πρέπει να δώσεις έγκυρη επιλογή.");
                scanner2.nextLine(); 
            }
        }

        return selectedOption.getExpenseCode();
    }
    public int ShowTypeOptions() {
        Scanner scanner3 = new Scanner(System.in);
        TypeOptions selectedOption1 = null;
        for (TypeOptions option : TypeOptions.values()) {
            System.out.println(option.getTypeCode() + ". " + option.getTypeDescription());
        }

        while (selectedOption1 == null) {
            System.out.print("Δώσε επιλογή");
            if (scanner3.hasNextInt()) {
                int choice = scanner3.nextInt();
                selectedOption1 = TypeOptions.TypeOption(choice);
                if (selectedOption1 == null) {
                    System.out.println("Μη έγκυρη επιλογή.");
                }
            } else {
                System.out.println("Παρακαλώ εισάγετε έναν αριθμό.");
                scanner3.next(); 
            }
        }
        return selectedOption1.getTypeCode;
    }
    public void IncomeDifference() {
        String kodikos = ShowIncomeMenu();
        Scanner scanner4 = new Scanner(System.in);
        BigDecimal number = null;

        while (number == null) {
            System.out.print("Παρακαλώ εισάγετε το ποσό αλλαγής του Προϋπολογισμού: ");
            String input = scanner4.nextLine();
            try {
                number = new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρος αριθμός.");
            }
        }
        Budget budget1 = BudgetLoader.loadGeneral("proypologismos2025.csv");
        BudgetService service1 = new BudgetService(budget1);
        service1.changeGeneralAmount(kodikos, number);
        BudgetWriter.writeGeneral("proypologismos2025b.csv", budget1.getGeneralList());
    }

    public void ExpenseDifference() {
        int ministryKodikos = ShowExpenseMenu();
        int typos = ShowTypeOptions();
        String column;
        if (typos == 1) {
            column = "τακτικός";
        } else {
            column = "πδε";
        }
        Scanner scanner5 = new Scanner(System.in);
        BigDecimal number1 = null;

        while (number1 == null) {
            System.out.print("Παρακαλώ εισάγετε το ποσό αλλαγής του Προϋπολογισμού: ");
            String input = scanner5.nextLine();
            try {
                number1 = new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρος αριθμός.");
            }
        }
        Budget budget2 = BudgetLoader.loadGeneral("proypologismos2025.csv");
        BudgetLoader.loadMinistries("proypologismos2025anaypourgeio.csv", budget2);
        BudgetService service2 = new BudgetService(budget2);
        service2.changeMinistryAmount(ministryKodikos, column, number1);
        BudgetWriter.writeGeneral("proypologismos2025b.csv", budget2.getGeneralList());
        BudgetWriter.writeMinistries("proypologismos2025anaypourgeiob.csv", budget2.getMinistryList());
    }
}
