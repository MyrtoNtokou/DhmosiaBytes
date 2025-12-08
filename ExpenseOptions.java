package dhmosiabytes;

public enum ExpenseOptions {
    PROEDRIADHM(1, "Προεδρία της Δημοκρατίας"),
    BOYLH(2, "Βουλή των Ελλήνων"),
    PROEDRIA(3, "Προεδρία της Κυβέρνησης"),
    ESOTERIKON(4, "Υπουργείο Εσωτερικών"),
    EXOTERIKON(5, "Υπουργείο Εξωτερικών"),
    ETHNIKHS_AMYNAS(6, "Υπουργείο Εθνικής Άμυνας"),
    YGEIAS(7, "Υπουργείο Υγείας"),
    DIKAIOSYNHS(8, "Υπουργείο Δικαιοσύνης"),
    PAIDEIAS(9, "Υπουργείο Παιδείας, Θρησκευμάτων και Αθλητισμού"),
    POLITISMOY(10, "Υπουργείο Πολιτισμού"),
    OIKONOMIKON(11, "Υπουργείο Εθνικής Οικονομίας και Οικονομικών"),
    AGROTIKHS(12, "Υπουργείο Αγροτικής Ανάπτυξης και Τροφίμων"),
    PERIVALLONTOS(13, "Υπουργείο Περιβάλλοντος και Ενέργειας"),
    ERGASIAS(14, "Υπουργείο Εργασίας και Κοινωνικής Ασφάλισης"),
    OIKOGENEIAS(15, "Υπουργείο Κοινωνικής Συνοχής και Οικογένειας"),
    ANAPTYXHS(16, "Υπουργείο Ανάπτυξης"),
    YPODOMON(17, "Υπουργείο Υποδομών και Μεταφορών"),
    NAYTILIAS(18, "Υπουργείο Ναυτιλίας και Νησιωτικής Πολιτικής"),
    TOYRISMOY(19, "Υπουργείο Τουρισμού"),
    DIAKYBERNHSHS(20, "Υπουργείο Ψηφιακής Διακυβέρνησης"),
    METANASTEYSHS(21, "Υπουργείο Μετανάστευσης και Ασύλου"),
    PROSTASIAS(22, "Υπουργείο Προστασίας του Πολίτη"),
    POLITIKHSPROSTASIAS(23, "Υπουργείο Κλιματικής Κρίσης και Πολιτικής Προστασίας"),
    ATTIKHS(24, "Αποκεντρωμένη Διοίκηση Αττικής"),
    STEREAS_ELLADAS_THESSALIAS(25, "Αποκεντρωμένη Διοίκηση Θεσσαλίας - Στερεάς Ελλάδας"),
    HPEIROY_DYTIKHS(26, "Αποκεντρωμένη Διοίκηση Ηπείρου - Δυτικής"),
    PELOPONNHSOY_DYTIKHS(27, "Αποκεντρωμένη Διοίκηση Πελοποννήσου - Δυτικής"),
    AIGAIOY(28, "Αποκεντρωμένη Διοίκηση Αιγαίου"),
    KRHTHS(29, "Αποκεντρωμένη Διοίκηση Κρήτης"),
    MAKEDONIAS_THRAKHS(30, "Αποκεντρωμένη Διοίκηση Μακεδονίας - Θράκης");

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
