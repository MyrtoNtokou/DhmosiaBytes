package budgetcomparison;

/** Utility class
 * for resolving budget file names based on year. */
public final class BudgetFileResolver {
    /** Private constructor to prevent instantiation. */
    private BudgetFileResolver() { }

    /** Year constant 2020. */
    private static final int YEAR_2020 = 2020;
    /** Year constant 2021. */
    private static final int YEAR_2021 = 2021;
    /** Year constant 2022. */
    private static final int YEAR_2022 = 2022;
    /** Year constant 2023. */
    private static final int YEAR_2023 = 2023;
    /** Year constant 2024. */
    private static final int YEAR_2024 = 2024;
    /** Year constant 2025. */
    private static final int YEAR_2025 = 2025;
    /** Year constant 2026. */
    private static final int YEAR_2026 = 2026;

    /**
     * Returns the general budget file name for the specified year.
     *
     * @param year the year for which to get the budget file name
     * @return the budget file name
     * @throws IllegalArgumentException if the year is not supported
     */
    public static String generalBudgetFile(final int year) {

        return switch (year) {
            case YEAR_2020 -> "proypologismos2020.csv";
            case YEAR_2021 -> "proypologismos2021.csv";
            case YEAR_2022 -> "proypologismos2022.csv";
            case YEAR_2023 -> "proypologismos2023.csv";
            case YEAR_2024 -> "proypologismos2024.csv";
            case YEAR_2025 -> "proypologismos2025.csv";
            case YEAR_2026 -> "proypologismos2026.csv";
            default -> throw new IllegalArgumentException(
                "Μη υποστηριζόμενο έτος: " + year);
        };
    }

    /**
     * Returns the ministry budget file name for the specified year.
     *
     * @param year the year for which to get the ministry budget file name
     * @return the ministry budget file name
     * @throws IllegalArgumentException if the year is not supported
     */
    public static String ministryBudgetFile(final int year) {

        return switch (year) {
            case YEAR_2020 -> "proypologismos2020anaypourgeio.csv";
            case YEAR_2021 -> "proypologismos2021anaypourgeio.csv";
            case YEAR_2022 -> "proypologismos2022anaypourgeio.csv";
            case YEAR_2023 -> "proypologismos2023anaypourgeio.csv";
            case YEAR_2024 -> "proypologismos2024anaypourgeio.csv";
            case YEAR_2025 -> "proypologismos2025anaypourgeio.csv";
            case YEAR_2026 -> "proypologismos2026anaypourgeio.csv";
            default -> throw new IllegalArgumentException(
                "Μη υποστηριζόμενο έτος: " + year);
        };
    }
}
