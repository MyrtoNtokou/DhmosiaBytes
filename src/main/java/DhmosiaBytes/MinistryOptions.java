package dhmosiabytes;

/**
 * Enum representing budget expense options, including ministries
 * and decentralized administrations, each with a numeric code and description.
 */
public enum MinistryOptions {
    /** Presidency of the Republic. */
    PROEDRIADHM(1, "Προεδρία της Δημοκρατίας"),

    /** Hellenic Parliament. */
    BOYLH(2, "Βουλή των Ελλήνων"),

    /** Presidency of the Government. */
    PROEDRIA(3, "Προεδρία της Κυβέρνησης"),

    /** Ministry of Interior. */
    ESOTERIKON(5, "Υπουργείο Εσωτερικών"),

    /** Ministry of Foreign Affairs. */
    EXOTERIKON(6, "Υπουργείο Εξωτερικών"),

    /** Ministry of National Defence. */
    ETHNIKHS_AMYNAS(7, "Υπουργείο Εθνικής Άμυνας"),

    /** Ministry of Health. */
    YGEIAS(8, "Υπουργείο Υγείας"),

    /** Ministry of Justice. */
    DIKAIOSYNHS(9, "Υπουργείο Δικαιοσύνης"),

    /** Ministry of Education, Religious Affairs and Sports. */
    PAIDEIAS(10, "Υπουργείο Παιδείας, Θρησκευμάτων και Αθλητισμού"),

    /** Ministry of Culture. */
    POLITISMOY(11, "Υπουργείο Πολιτισμού"),

    /** Ministry of National Economy and Finance. */
    OIKONOMIKON(12, "Υπουργείο Εθνικής Οικονομίας και Οικονομικών"),

    /** Ministry of Rural Development and Food. */
    AGROTIKHS(13, "Υπουργείο Αγροτικής Ανάπτυξης και Τροφίμων"),

    /** Ministry of Environment and Energy. */
    PERIVALLONTOS(14, "Υπουργείο Περιβάλλοντος και Ενέργειας"),

    /** Ministry of Labour and Social Security. */
    ERGASIAS(15, "Υπουργείο Εργασίας και Κοινωνικής Ασφάλισης"),

    /** Ministry of Social Cohesion and Family. */
    OIKOGENEIAS(16, "Υπουργείο Κοινωνικής Συνοχής και Οικογένειας"),

    /** Ministry of Development. */
    ANAPTYXHS(17, "Υπουργείο Ανάπτυξης"),

    /** Ministry of Infrastructure and Transport. */
    YPODOMON(18, "Υπουργείο Υποδομών και Μεταφορών"),

    /** Ministry of Shipping and Island Policy. */
    NAYTILIAS(19, "Υπουργείο Ναυτιλίας και Νησιωτικής Πολιτικής"),

    /** Ministry of Tourism. */
    TOYRISMOY(20, "Υπουργείο Τουρισμού"),

    /** Ministry of Digital Governance. */
    DIAKYBERNHSHS(21, "Υπουργείο Ψηφιακής Διακυβέρνησης"),

    /** Ministry of Migration and Asylum. */
    METANASTEYSHS(22, "Υπουργείο Μετανάστευσης και Ασύλου"),

    /** Ministry of Citizen Protection. */
    PROSTASIAS(23, "Υπουργείο Προστασίας του Πολίτη"),

    /** Ministry of Climate Crisis and Civil Protection. */
    POLITIKHSPROSTASIAS(24, "Υπουργείο Κλιματικής Κρίσης "
    + "και Πολιτικής Προστασίας"),

    /** Decentralized Administration of Attica. */
    ATTIKHS(26, "Αποκεντρωμένη Διοίκηση Αττικής"),

    /** Decentralized Administration of Thessaly - Central Greece. */
    STEREAS_ELLADAS_THESSALIAS(27, "Αποκεντρωμένη Διοίκηση Θεσσαλίας - "
    + "Στερεάς Ελλάδας"),

    /** Decentralized Administration of Epirus - Western Greece. */
    HPEIROY_DYTIKHS(28, "Αποκεντρωμένη Διοίκηση Ηπείρου - Δυτικής"),

    /** Decentralized Administration of Peloponnese - Western Greece. */
    PELOPONNHSOY_DYTIKHS(29, "Αποκεντρωμένη Διοίκηση Πελοποννήσου - Δυτικής"),

    /** Decentralized Administration of Aegean. */
    AIGAIOY(30, "Αποκεντρωμένη Διοίκηση Αιγαίου"),

    /** Decentralized Administration of Crete. */
    KRHTHS(31, "Αποκεντρωμένη Διοίκηση Κρήτης"),

    /** Decentralized Administration of Macedonia - Thrace. */
    MAKEDONIAS_THRAKHS(32, "Αποκεντρωμένη Διοίκηση Μακεδονίας - Θράκης");

    /** Numeric code identifying the expense. */
    private final int expenseCode;

    /** Human-readable description of the expense. */
    private final String expenseDescription;

     /**
     * Constructs an MinistryOptions enum constant.
     *
     * @param code numeric code of the expense
     * @param description description of the expense
     */
    MinistryOptions(final int code, final String description) {
        this.expenseCode = code;
        this.expenseDescription = description;
    }

     /**
     * Returns the numeric code of the expense.
     *
     * @return expense code
     */
    public int getExpenseCode() {
        return this.expenseCode;
    }

     /**
     * Returns the description of the expense.
     *
     * @return expense description
     */
    public String getExpenseDescription() {
        return expenseDescription;
    }

     /**
     * Returns the MinistryOptions constant matching the given code.
     *
     * @param expenseCode the numeric code to look up
     * @return matching MinistryOptions constant
     * @throws IllegalArgumentException if no matching constant is found
     */
    public static MinistryOptions fromCode(final int expenseCode) {
        for (MinistryOptions expenseOpt : MinistryOptions.values()) {
            if (expenseOpt.getExpenseCode() == expenseCode) {
                return expenseOpt;
            }
        }
        throw new IllegalArgumentException("Μη έγκυρη επιλογή: "
        + expenseCode);
    }
}
