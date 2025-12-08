package.dhmosiabytes;
    
    public enum  ChangeOptions{
        INCOME(1 ,"Έσοδα Προϋπολογισμού"),
        EXPENSE(2,"Έξοδα Προϋπολογισμού");
    private final int changeCode;
    private final String changeDescription;
    ChangeOptions(final int givenChangeCode, final String givenChangeDescription) {
        this.changeCode = givenChangeCode;
        this.changeDescription = givenChangeDescription;
    }
    public int getChangeCode() {
        return this.changeCode;
    }
    public String getChangeDescription() {
        return changeDescription;
    }
    public static ChangeOptions CodeOption(final int changeCode) {
        for (ChangeOptions changeOpt : ChangeOptions.values()) {
            if (changeOpt.getChangeCode() == changeCode) {
                return changeOpt;
            }
        }
        return null;
    }
}
