package dhmosiabytes;

    /**
     * Enum representing expense categories.
     */
    public enum ExpenseOptions {
        /** Employee benefits. */
        EMPLOYEE_BENEFITS("2,1", "Παροχές σε εργαζομένους"),
        /** Social benefits. */
        SOCIAL_BENEFITS("2,2", "Κοινωνικές παροχές"),
        /** Transfers. */
        TRANSFERS("2,3", "Μεταβιβάσεις"),
        /** Purchases of goods and services. */
        PURCHASES_GOODS_SERVICES("2,4", "Αγορές αγαθών και υπηρεσιών"),
        /** Subsidies. */
        SUBSIDIES("2,5", "Επιδοτήσεις"),
        /** Interests. */
        INTERESTS("2,6", "Τόκοι"),
        /** Other expenses. */
        OTHER_EXPENSES("2,7", "Λοιπές δαπάνες"),
        /** Credits to allocate. */
        CREDITS_TO_ALLOCATE("2,8", "Πιστώσεις υπό κατανομή"),
        /** Fixed assets. */
        FIXED_ASSETS("2,9", "Πάγια περιουσιακά στοιχεία"),

        /** Precious metals. */
        PRECIOUS_METALS("2,9,1", "Τιμαλφή"),
        /** Loans. */
        LOANS_1("2,9,2", "Δάνεια"),
        /** Equity securities and investment fund shares. */
        EQUITY_SECURITIES(
            "2,9,3",
             "Συμμετοχικοί τίτλοι και μερίδια επενδυτικών κεφαλαίων"),
        /** Debt securities (liabilities). */
        DEBT_SECURITIES("2,9,4", "Χρεωστικοί τίτλοι (υποχρεώσεις)"),
        /** Loans. */
        LOANS_2("2,9,5", "Δάνεια"),
        /** Financial derivatives. */
        XRHMATOOIKONOMIKA("2,9,6", "Χρηματοοικονομικά παράγωγα");

        /** Expense code. */
        private final String code;
        /** Expense description. */
        private final String description;

        ExpenseOptions(final String codeParam, final String descParam) {
        this.code = codeParam;
        this.description = descParam;
        }

        /** @return the expense code. */
        public String getExpenseCode() {
            return code;
        }

        /** @return the expense description. */
        public String getExpenseDescription() {
            return description;
        }

        /**
         * Returns the ExpenseOptions constant corresponding to the given code.
         *
         * @param expenseCode the code to look up
         * @return matching ExpenseOptions constant
         * @throws IllegalArgumentException if no matching constant is found
         */
        public static ExpenseOptions fromCode(final String expenseCode) {
            for (ExpenseOptions expenseOpt : ExpenseOptions.values()) {
                if (expenseOpt.getExpenseCode().equals(expenseCode)) {
                    return expenseOpt;
                }
            }
            throw new IllegalArgumentException("Μη έγκυρη επιλογή: "
            + expenseCode);
        }
    }
