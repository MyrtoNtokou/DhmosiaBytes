package dhmosiabytes;

public enum IncomeOptions {
    FOROI("1,1", "Φόροι"),
    KOINONIKES_EISFORES("1,2", "Κοινωνικές εισφορές"),
    METAVIVASEIS("1,3", "Μεταβιβάσεις"),
    POLHSEIS("1,4", "Πωλήσεις αγαθών και υπηρεσιών"), 
    LOIPA("1,5", "Λοιπά τρέχοντα έσοδα"),
    PAGIA("1,6", "Πάγια Περιουσιακά Στοιχεία"), 
    XREOSTIKOI("1,7", "Χρεωστικοί τίτλοι"),
    DANEIO("1,8", "Δάνεια"),
    SYMMETOXIKOI("1,9",
    "Συμμετοχικοί τίτλοι και μερίδια επενδυτικών κεφαλαίων"),
    YPOXREOSEIS("1,9,1", "Υποχρεώσεις από νόμισμα και καταθέσεις"), 
    XREOSTIKOI_YPOXREOSEIS("1,9,2", "Χρεωστικοί τίτλοι (υποχρεώσεις)"),
    DANEIA("1,9,3", "Δάνεια"),
    XRHMATOOIKONOMIKA("1,9,4", "Χρηματοοικονομικά παράγωγα");

    private final String incomeCode;
    private final String incomeDescription;

    IncomeOptions(String incomeCode, String incomeDescription) {
        this.incomeCode = incomeCode;
        this.incomeDescription = incomeDescription;
    }

    public String getIncomeCode() {
        return incomeCode;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public static IncomeOptions fromCode(String incomeCode) {
        for (IncomeOptions incomeOpt : IncomeOptions.values()) {
            if (incomeOpt.getIncomeCode().equals(incomeCode)) {
                return incomeOpt;
            }
        }
        throw new IllegalArgumentException("Μη έγκυρη επιλογή: " + incomeCode);
    }   
}
