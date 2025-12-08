package dhmosiabytes;

public enum ExpenseOptions {
    PROEDRIADHM(1, "Προεδρία της Δημοκρατίας"),
    BOYLH(2, "Βουλή των Ελλήνων"),
    PROEDRIA(3, "Προεδρία της Κυβέρνησης"),
    ESOTERIKON(5, "Υπουργείο Εσωτερικών"),
    EXOTERIKON(6, "Υπουργείο Εξωτερικών"),
    ETHNIKHS_AMYNAS(7, "Υπουργείο Εθνικής Άμυνας"),
    YGEIAS(8, "Υπουργείο Υγείας"),
    DIKAIOSYNHS(9, "Υπουργείο Δικαιοσύνης"),
    PAIDEIAS(10, "Υπουργείο Παιδείας, Θρησκευμάτων και Αθλητισμού"),
    POLITISMOY(11, "Υπουργείο Πολιτισμού"),
    OIKONOMIKON(12, "Υπουργείο Εθνικής Οικονομίας και Οικονομικών"),
    AGROTIKHS(13, "Υπουργείο Αγροτικής Ανάπτυξης και Τροφίμων"),
    PERIVALLONTOS(14, "Υπουργείο Περιβάλλοντος και Ενέργειας"),
    ERGASIAS(15, "Υπουργείο Εργασίας και Κοινωνικής Ασφάλισης"),
    OIKOGENEIAS(16, "Υπουργείο Κοινωνικής Συνοχής και Οικογένειας"),
    ANAPTYXHS(17, "Υπουργείο Ανάπτυξης"),
    YPODOMON(18, "Υπουργείο Υποδομών και Μεταφορών"),
    NAYTILIAS(19, "Υπουργείο Ναυτιλίας και Νησιωτικής Πολιτικής"),
    TOYRISMOY(20, "Υπουργείο Τουρισμού"),
    DIAKYBERNHSHS(21, "Υπουργείο Ψηφιακής Διακυβέρνησης"),
    METANASTEYSHS(22, "Υπουργείο Μετανάστευσης και Ασύλου"),
    PROSTASIAS(23, "Υπουργείο Προστασίας του Πολίτη"),
    POLITIKHSPROSTASIAS(24, "Υπουργείο Κλιματικής Κρίσης και Πολιτικής Προστασίας"),
    ATTIKHS(26, "Αποκεντρωμένη Διοίκηση Αττικής"),
    STEREAS_ELLADAS_THESSALIAS(27, "Αποκεντρωμένη Διοίκηση Θεσσαλίας - Στερεάς Ελλάδας"),
    HPEIROY_DYTIKHS(28, "Αποκεντρωμένη Διοίκηση Ηπείρου - Δυτικής"),
    PELOPONNHSOY_DYTIKHS(29, "Αποκεντρωμένη Διοίκηση Πελοποννήσου - Δυτικής"),
    AIGAIOY(30, "Αποκεντρωμένη Διοίκηση Αιγαίου"),
    KRHTHS(31, "Αποκεντρωμένη Διοίκηση Κρήτης"),
    MAKEDONIAS_THRAKHS(32, "Αποκεντρωμένη Διοίκηση Μακεδονίας - Θράκης");

    private final int expenseCode;
    private final String expenseDescription;

    ExpenseOptions(int expenseCode, String expenseDescription) {
        this.expenseCode = expenseCode;
        this.expenseDescription = expenseDescription;
    }

    public int getExpenseCode() {
        return this.expenseCode;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public static ExpenseOptions ExpenseOption(final int expenseCode) {
        for (ExpenseOptions expenseOpt : ExpenseOptions.values()) {
            if (expenseOpt.getExpenseCode() == expenseCode) {
                return expenseOpt;
            }
        }
        return null;
    }
}

