package dhmosiabytes;

public enum IncomeOptions {
    FOROI(1, "Φόροι"),
    KOINONIKES_EISFORES(2, "Κοινωνικές εισφορές"),
    METAVIVASEIS(3, "Μεταβιβάσεις"),
    POLHSEIS(4, "Πωλήσεις αγαθών και υπηρεσιών"), 
    LOIPA(5, "Λοιπά τρέχοντα έσοδα"),
    PAGIA(6, "Πάγια Περιουσιακά Στοιχεία"), 
    XREOSTIKOI(7, "Χρεωστικοί τίτλοι"),
    DANEIO(8, "Δάνεια"),
    SYMMETOXIKOI(9, "Συμμετοχικοί τίτλοι και μερίδια επενδυτικών κεφαλαίων"),
    YPOXREOSEIS(10, "Υποχρεώσεις από νόμισμα και καταθέσεις"), 
    XREOSTIKOI_YPOXREOSEIS(11, "Χρεωστικοί τίτλοι (υποχρεώσεις)"),
    DANEIA(12, "Δάνεια"),
    XRHMATOOIKONOMIKA(13, "Χρηματοοικονομικά παράγωγα");

    private final int incomeCode;
    private final String incomeDescription;

    IncomeOptions(int incomeCode, String incomeDescription) {
        this.incomeCode = incomeCode;
        this.incomeDescription = incomeDescription;
    }

    public int getIncomeCode() {
        return this.incomeCode;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public static IncomeOptions IncomeOption(final int incomeCode) {
        for (IncomeOptions incomeOpt : IncomeOptions.values()) {
            if (incomeOpt.getIncomeCode() == incomeCode) {
                return incomeOpt;
            }
        }
        return null;
    }
}

