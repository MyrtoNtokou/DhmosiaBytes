package budgetlogic;

import java.math.BigDecimal;

/**
 * Service interface for budget operations.
 */
public interface BudgetService {

    /**
     * Loads budget data from CSV files.
     *
     * @param kodikos the budget code
     * @param newAmount the new amount to set
     * @throws Exception if loading fails
     */
    void changeGeneralAmount(String kodikos, BigDecimal newAmount);

    /**
     * Changes the amount for a specific ministry.
     *
     * @param ministryKodikos the ministry code
     * @param column the column to change
     * @param newValue the new value to set
     */
    void changeMinistryAmount(int ministryKodikos,
                              String column,
                              BigDecimal newValue);

    /**
     * Validates the general budget data.
     *
     * @return true if valid, false otherwise
     */
    boolean validateGeneral();

    /**
     * Validates the ministries budget data.
     *
     * @return true if valid, false otherwise
     */
    boolean validateMinistries();

    /**
     * Validates all budget data.
     *
     * @return true if all data is valid, false otherwise
     */
    boolean validateAll();

    /**
     * Retrieves the current budget data.
     *
     * @return the budget data
     */
    Budget getBudget();
}
