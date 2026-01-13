package budgetreader;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/** Represents a single budget record (BasicRecord).
 * Each record contains a code, a description and an amount.
 * This class is immutable except for the provided getter methods.
 * It also provides a formatted string representation of the entry.*/
public final class  BasicRecord {
    /** code of
     * BasicRecord instance.*/
    private String code;

    /**description  of
     * BasicRecord instance. */
    private String description;

    /**amount  of
     * BasicRecord instance. */
    private BigDecimal amount;

    /** constructor
     * that creates new BasicRecord instance.

    * @param codeValue the code value
     * @param descriptionValue the description value
     * @param amountValue the amount value
     */
    public BasicRecord(final String codeValue, final String descriptionValue,
    final BigDecimal amountValue) {
        this.code = codeValue;
        this.description = descriptionValue;
        this.amount = amountValue;
    }

    /** method that return code.
    * @return code.
    */
    public String getCode() {
        return code;
    }

    /** method that return description.
    *@return description
    */
    public String getDescription() {
        return description;
    }

    /** method that return amount.
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /** method that sets new value to code variable.
     * @param codeNew to set.
    */
    public void setCode(final String codeNew) {
        code = codeNew;
    }

    /** method that sets new value to description variable.
     * @param descriptionNew to set.
    */
    public void setDescription(final String descriptionNew) {
        description = descriptionNew;
    }

    /**method that set new value to amount variable.
     * @param amountNew to set.
     */
    public void setAmount(final BigDecimal amountNew) {
        amount = amountNew;
    }

    /** toString method.
    *
    * @return the formatted string representation
    */
    @Override
    public String toString() {
         DecimalFormat df = new DecimalFormat("#,###");

    return code + " | " + description + " | " + df.format(amount);
    }
}
