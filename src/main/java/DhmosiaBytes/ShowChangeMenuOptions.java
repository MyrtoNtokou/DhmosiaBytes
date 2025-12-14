package dhmosiabytes;

import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.InputMismatchException;

import budgetlogic.AllocationMapping;
import budgetlogic.BasicRecord;
import budgetlogic.Budget;
import budgetlogic.ReadBudget;
import budgetlogic.BudgetService;
import budgetlogic.BudgetWriter;
import budgetlogic.Ministry;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;
import budgetreader.DisplayBudget;
import budgetreader.Eggrafi;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ShowChangeMenuOptions {
    Scanner scanner = new Scanner(System.in);

    public void showChangeMenu() {
        for (ChangeOptions opt : ChangeOptions.values()) {
            System.out.println(opt.getChangeCode() + ". "
            + opt.getChangeDescription());
        }

        ChangeOptions selected = null;
        while (selected == null) {
            System.out.print("Επιλογή: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                selected = ChangeOptions.CodeOption(choice);
                if (selected == null) {
                    System.out.println("Μη έγκυρη επιλογή");
                }
            }  else {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + ChangeOptions.values().length);
                scanner.next();
            }
        }
        if (selected == ChangeOptions.INCOME) {
            List<Eggrafi> g =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");
            
            List<Eggrafi> esoda = new ArrayList<>();
            for (Eggrafi e : g) {
                if (e.getKodikos().startsWith("1,")) {
                    esoda.add(e);
                }
            }
            DisplayBudget.showGeneral(esoda);
            incomeDifference();
        } else if (selected == ChangeOptions.EXPENSE) {
            List<Ypourgeio> y =
            ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");

            List<Ypourgeio> ministries = new ArrayList<>();
            for (Ypourgeio e : y) {
                if (!e.getOnoma().startsWith("4,") && !e.getOnoma().startsWith("25,")
                && !e.getOnoma().startsWith("33,")) {
                    ministries.add(e);
                }
            }
            DisplayBudget.showMinistry(ministries);
            expenseDifference();
        }
    }

    public String selectIncomeMenu() {
        IncomeOptions selected1 = null;
        while (selected1 == null) {
            System.out.print("Επιλογή: ");
            try {
                String choice1 = scanner.nextLine().trim();
                selected1 = IncomeOptions.IncomeOption(choice1);
            } catch (InputMismatchException e) {
                System.out.println(" Δώστε έναν αριθμό από το 1 έως το "
                + IncomeOptions.values().length);
            }
        }
        return selected1.getIncomeCode(); 
    }

    public int showExpenceMenu() {
        for (ExpenceOptions opt : ExpenceOptions.values()) {
            System.out.println(opt.getExpenseCode() + ". "
            + opt.getExpenseDescription());
        }

        ExpenceOptions selectedOption = null;
        while (selectedOption == null) {
            System.out.print("Επιλογή: ");
            try {
                int choice1 = scanner.nextInt();
                selectedOption = ExpenceOptions.ExpenseOption(choice1);
            } catch (InputMismatchException e) {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + ExpenceOptions.values().length);
            }
        }
        return selectedOption.getExpenseCode();
    }

    public int showTypeOptions() {
        TypeOptions selectedOption1 = null;
    
        for (TypeOptions option : TypeOptions.values()) {
            System.out.println(option.getTypeCode() + ". "
            + option.getTypeDescription());
        }

        while (selectedOption1 == null) {
            System.out.print("Επιλογή: ");
            try {
                int choice = scanner.nextInt();
                selectedOption1 = TypeOptions.TypeOption(choice);
            } catch (InputMismatchException e) {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + TypeOptions.values().length);
            }
        }
        return selectedOption1.getTypeCode();
    }

    public void incomeDifference() {
        String kodikos = selectIncomeMenu();
        Scanner scanner4 = new Scanner(System.in);
        BigDecimal number = null;

        while (number == null) {
            System.out.print("Παρακαλώ εισάγετε το νέο ποσό: ");
            String input = scanner4.nextLine();
            try {
                number = new BigDecimal(input);
                //αυτό παίζει και να μην μπαίνει εδώ (?)
                List<Eggrafi> general = ReadBudget.readGeneralBudget("proypologismos2025.csv");
                List<Ypourgeio> ministries = ReadBudget.readByMinistry("proypologismos2025anaypoyrgeio.csv");
                Map<String, Eggrafi> revenueMap = new LinkedHashMap<>();
                Map<String, Eggrafi> expenceMap = new LinkedHashMap<>();
                Map<String, Ypourgeio> ministryMap = new LinkedHashMap<>();

                // εδώ καλώ μία μέθοδο για τη δημιουργία του budget
                BudgetService service = new BudgetService(budget, null);
                

            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη τιμή.");
                continue;
            }
            if (number.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Το ποσό δεν μπορεί να είναι αρνητικό.");
                number = null;
            }
        }

        BudgetService service1 = new BudgetService(budget1);
        service1.changeGeneralAmount(kodikos, number);
        BudgetWriter.writeGeneral("proypologismos2025b.csv",
        budget1.getGeneralList());
    }

    public void expenseDifference() {
        int ministryKodikos = showExpenceMenu();
        int typos = showTypeOptions();
        String column;
        if (typos == 1) {
            column = "τακτικός";
        } else {
            column = "πδε";
        }
        BigDecimal number = null;

        while (number == null) {
            System.out.print("Παρακαλώ εισάγετε το νέο ποσό: ");
            String input = scanner.nextLine();
            try {
                number = new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη τιμή.");
                continue;                
            }
            if (number.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Το ποσό δεν μπορεί να είναι αρνητικό.");
                number = null;
            }
        }

        List<Eggrafi> g =
        ReadBudget.readGeneralBudget("proypologismos2025.csv");
        DisplayBudget.showGeneral(g);

        ReadBudget.loadMinistries("proypologismos2025anaypourgeio.csv", budget2);
        BudgetService service2 = new BudgetService(budget2);
        service2.changeMinistryAmount(ministryKodikos, column, number);
        BudgetWriter.writeGeneral("proypologismos2025b.csv",
        budget2.getGeneralList());
        BudgetWriter.writeMinistries("proypologismos2025anaypourgeiob.csv",
        budget2.getMinistryList());
    }
}
