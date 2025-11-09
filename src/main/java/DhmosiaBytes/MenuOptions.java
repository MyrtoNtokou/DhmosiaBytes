public enum MenuOptions {
    
    //δημιούργησε τα σταθερά αντικείμενα της κλάσης
    SHOW_BUDGET(1, "Εμφάνιση Κρατικού Προϋπολογισμού"),
    EDIT_BUDGET(2, "Αλλαγή στοιχείων"),
    SUMMARY(3, "Κατηγοριοποίηση στοιχείων"), //εδώ μπορεί να μπει έξτρα επιλογή ανά έτη, ανά χώρα, ανά υπουργείο
    GRAPHS(4, "Γραφήματα"),
    EXIT(5, "Έξοδος");

    private final int code;
    private final String description;

    private MenuOptions(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return description;
    }

    //βρες το αντικείμενο από τον αριθμό που σου δίνω
    public static MenuOptions fromCode(int code) {
        for (MenuOptions opt : MenuOptions.values()) {
            if (opt.getCode() == code) {
                return opt;
            }
        }
        return null;
    }
}