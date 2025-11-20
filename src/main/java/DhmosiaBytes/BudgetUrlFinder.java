package dhmosiabytes;

import java.util.Map;

/**
 * Utility class that finds the urls of the state budget pdfs.
 */
public final class BudgetUrlFinder {

    /** Map with the known urls of the state budget. */
    private static final Map<Integer, String> KNOWN_URL = Map.of(
        2019, "https://minfin.gov.gr/wp-content/uploads/2023/11/"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2019.pdf",
        2020, "https://minfin.gov.gr/wp-content/uploads/2019/11/21-11-2019-"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2020.pdf",
        2021, "https://minfin.gov.gr/wp-content/uploads/2023/11/"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2021.pdf",
        2022, "https://minfin.gov.gr/wp-content/uploads/2023/11/"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ_2022.pdf",
        2023, "https://minfin.gov.gr/wp-content/uploads/2023/11/21-11-2022-"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ-2023.pdf",
        2024, "https://minfin.gov.gr/wp-content/uploads/2023/11/"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2024.pdf",
        2025, "https://minfin.gov.gr/wp-content/uploads/2024/11/"
                + "Κρατικός-Προϋπολογισμός-2025_ΟΕ.pdf",
        2026, "https://minfin.gov.gr/wp-content/uploads/2025/11/"
                + "Κρατικός-Προϋπολογισμός-2026.pdf");

    // Constructor
    private BudgetUrlFinder() {
        // Utility class
    }

    /**
     * Finds the url of the given year.
     *
     * @param year
     * @return url or null
     */
    public static String findUrl(final int year) {
        return KNOWN_URL.get(year);
    }
}
