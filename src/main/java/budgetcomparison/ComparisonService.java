package budgetcomparison;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.math.RoundingMode;
import budgetreader.*;

public class ComparisonService {

    public void compareGeneralBudgetByYear(
            int year1,
            int year2,
            String kodikos) {

        String f1 = BudgetFileResolver.generalBudgetFile(year1);
        String f2 = BudgetFileResolver.generalBudgetFile(year2);

        List<Eggrafi> l1 = ReadBudget.readGeneralBudget(f1);
        List<Eggrafi> l2 = ReadBudget.readGeneralBudget(f2);

        Optional<Eggrafi> e1 = findEggrafi(l1, kodikos);
        Optional<Eggrafi> e2 = findEggrafi(l2, kodikos);

        if (e1.isEmpty() || e2.isEmpty()) {
            System.out.println("Δεν βρέθηκε η εγγραφή και στα δύο έτη");
            return;
        }

        ComparisonResult result = calculatePercentageChange(
            e1.get().getPoso(),
            e2.get().getPoso(),
            kodikos,
            e1.get().getPerigrafi(),
            year1,
            year2);

        printResult(result, year1, year2, e1.get().getPerigrafi());
    }

 
    public void compareMinistryBudgetByYear(
            int year1,
            int year2,
            String kodikos) {

        String f1 = BudgetFileResolver.ministryBudgetFile(year1);
        String f2 = BudgetFileResolver.ministryBudgetFile(year2);

        List<Ypourgeio> l1 = ReadBudget.readByMinistry(f1);
        List<Ypourgeio> l2 = ReadBudget.readByMinistry(f2);

        Optional<Ypourgeio> y1 = findYpourgeio(l1, kodikos);
        Optional<Ypourgeio> y2 = findYpourgeio(l2, kodikos);

        if (y1.isEmpty() || y2.isEmpty()) {
            System.out.println("Δεν βρέθηκε υπουργείο και στα δύο έτη");    
            return;
        }

        ComparisonResult result = calculatePercentageChange(
            y1.get().getSynolo(),
            y2.get().getSynolo(),
            kodikos,
            y1.get().getOnoma(),
            year1,
            year2);

        printResult(result, year1, year2, y1.get().getOnoma());
    }

  
    private Optional<Eggrafi> findEggrafi(
        List<Eggrafi> list, String kodikos) {

        return list.stream()
            .filter(e -> e.getKodikos().trim().equals(kodikos.trim()))
            .findFirst();
    }



    private Optional<Ypourgeio> findYpourgeio(
        List<Ypourgeio> list, String kodikos) {

        return list.stream()
                .filter(y -> String.valueOf(y.getKodikos()).equals(kodikos))
                .findFirst();
    }

    
    private ComparisonResult calculatePercentageChange(
        BigDecimal base,
        BigDecimal current,
        String kodikos,
        String name,
        int baseYear,
        int compareYear) {

        if (base.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException(
                    "Η βάση σύγκρισης είναι μηδέν για τον κωδικό " + kodikos);
        }

        BigDecimal percentage = current
                .subtract(base)
                .multiply(BigDecimal.valueOf(100))
                .divide(base, 2, RoundingMode.HALF_UP);

        return new ComparisonResult(kodikos, name, baseYear, compareYear, percentage);
    }

    
    private void printResult(
        ComparisonResult r, int year1, int year2, String name) {
        
        int sign = r.getPercentageChange().signum();
        String verb=(sign >= 0) ? "αυξήθηκε" : "μειώθηκε";

        System.out.printf(
                "%s (%s) %s κατά %s%% το %d σε σχέση με το %d%n",
                r.getKodikos(),
                name,
                verb,
                r.getPercentageChange().abs(),
                year2,
                year1
        );
    }
}