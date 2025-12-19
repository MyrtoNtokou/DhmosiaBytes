package budgetcomparison;

public final class BudgetFileResolver {

    private BudgetFileResolver() {}

    public static String generalBudgetFile(int year) {

        return switch (year) {
            case 2020 -> "proypologismos2020.csv";
            case 2021 -> "proypologismos2021.csv";
            case 2022 -> "proypologismos2022.csv";
            case 2023 -> "proypologismos2023.csv";
            case 2024 -> "proypologismos2024.csv";
            case 2025 -> "proypologismos2025.csv";
            case 2026 -> "proypologismos2026.csv";
            default -> throw new IllegalArgumentException(
                "Μη υποστηριζόμενο έτος: " + year);
        };
    }

    public static String ministryBudgetFile(int year) {

        return switch (year) {
            case 2020 -> "proypologismos2020anaypourgeio.csv";
            case 2021 -> "proypologismos2021anaypourgeio.csv";
            case 2022 -> "proypologismos2022anaypourgeio.csv";
            case 2023 -> "proypologismos2023anaypourgeio.csv";
            case 2024 -> "proypologismos2024anaypourgeio.csv";
            case 2025 -> "proypologismos2025anaypourgeio.csv";
            case 2026 -> "proypologismos2026anaypourgeio.csv";
            default -> throw new IllegalArgumentException(
                "Μη υποστηριζόμενο έτος: " + year);
        };
    }
}
