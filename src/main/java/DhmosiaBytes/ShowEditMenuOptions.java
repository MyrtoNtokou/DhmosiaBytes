package dhmosiabytes;

import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.io.IOException;

import budgetlogic.Budget;
import budgetlogic.ReadBudget;
import budgetlogic.BudgetService;
import budgetlogic.BudgetWriter;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;
import budgetreader.DisplayBudget;
import budgetreader.Eggrafi;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ShowEditMenuOptions {
    Scanner scanner = new Scanner(System.in);
    BudgetEditor editor = new BudgetEditor();

    /**
     * Displays the RevenueOrExpense menu, gets the user's choice,
     * displays the proper list and makes the change to this list.
     */
    public void chooseRevenueOrExpense() {
        for (RevenueOrExpense opt : RevenueOrExpense.values()) {
            System.out.println(opt.getRevenueOrExpenseCode() + ". "
            + opt.getDescription());
        }
        RevenueOrExpense selected = null;
        while (selected == null) {
            System.out.print("Επιλογή: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                selected = RevenueOrExpense.fromCode(choice);
                if (selected == null) {
                    System.out.println("Μη έγκυρη επιλογή");
                }
            }  else {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + RevenueOrExpense.values().length);
                scanner.next();
            }
        }

        if (selected == RevenueOrExpense.INCOME) {
            List<Eggrafi> g =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");

            List<Eggrafi> esoda = new ArrayList<>();
            for (Eggrafi e : g) {
                if (e.getKodikos().startsWith("1,")) {
                    esoda.add(e);
                }
            }
            DisplayBudget.showGeneral(esoda);

            String code = selectRevenue();
            editIncome(code);
        } else if (selected == RevenueOrExpense.EXPENSE) {
            List<Ypourgeio> y =
            ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");

            List<Ypourgeio> ministries = new ArrayList<>();
            for (Ypourgeio e : y) {
                if (!e.getOnoma().startsWith("4,")
                && !e.getOnoma().startsWith("25,")
                && !e.getOnoma().startsWith("33,")) {
                    ministries.add(e);
                }
            }
            DisplayBudget.showMinistry(ministries);

            int code = selectExpense();
            editExpense(code);
        }
    }

    /**
     * Asks for specific revenue to be edited.
     *
     * @return the code of the revenue to be edited
     */
    public String selectRevenue() {
        IncomeOptions selected = null;
        while (selected == null) {
            System.out.print("Επιλογή: ");
            try {
                String choice = scanner.nextLine().trim();
                selected = IncomeOptions.fromCode(choice);
            } catch (InputMismatchException e) {
                System.out.println(" Δώστε έναν αριθμό από το 1 έως το "
                + IncomeOptions.values().length);
            }
        }
        return selected.getIncomeCode();
    }

    /**
     * Asks for specific expense to be edited.
     *
     * @return the code of the expense to be edited 
     */
    public int selectExpense() {
        ExpenseOptions selectedOption = null;
        while (selectedOption == null) {
            System.out.print("Επιλογή: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                selectedOption = ExpenseOptions.fromCode(choice);
            } catch (InputMismatchException e) {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + ExpenseOptions.values().length);
            }
        }
        return selectedOption.getExpenseCode();
    }

    /**
     * Displays the BudgetType menu and asks the user for their choice.
     *
     * @return the code of user's choice
     */
    public int selectBudgetType() {
        BudgetType selectedOption1 = null;
        for (BudgetType option : BudgetType.values()) {
            System.out.println(option.getTypeCode() + ". "
            + option.getTypeDescription());
        }

        while (selectedOption1 == null) {
            System.out.print("Επιλογή: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                selectedOption1 = BudgetType.fromCode(choice);
            } catch (InputMismatchException e) {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + BudgetType.values().length);
            }
        }
        return selectedOption1.getTypeCode();
    }
}
