package TextTranslator;

/**
 * A enumerated list of values from the text dumps that denote an easily identifiable pointer or special character
 * that will need modifying for translation purposes
 */
public enum MappedValues {
    PKNAME0("[VAR PKNAME(0000)]"), PKNAME1("[VAR PKNAME(0001)]"), PKNAME2("[VAR PKNAME(0002)]"),
    PKNAME3("[VAR PKNAME(0003)]"), PKNAME4("[VAR PKNAME(0004)]"), PKNAME5("[VAR PKNAME(0005)]"),
    PKN0("[VAR PKNICK(0000)]"), PKN1("[VAR PKNICK(0001)]"), TNA0("[VAR TRNAME(0000)]"), TNA1("[VAR TRNAME(0001)]"),
    TNI0("[VAR TRNICK(0000)]"), TNI1("[VAR TRNICK(0001)]"), CL0("[VAR COLOR(0000)]"), CL1("[VAR COLOR(0001)]"),
    CL2("[VAR COLOR(0002)]"), RETURN("\\r"), LINE_BREAK("\\n"), DIALOGUE_BREAK("\\c"), INCORRECT_APOSTROPHE("’");

    String value;

    MappedValues(String value) {
        this.value = value;
    }

    /**
     * The color pointers
     *
     * @return the values for the color pointers
     */
    public static MappedValues[] getCL() {
        return new MappedValues[]{CL0, CL1, CL2};
    }

    /**
     * The special characters that are mapped via the text dump
     *
     * @return the special characters
     */
    public static MappedValues[] getSpecChars() {
        return new MappedValues[]{RETURN, LINE_BREAK, DIALOGUE_BREAK, INCORRECT_APOSTROPHE};
    }

    /**
     * The Pokemon name pointers
     *
     * @return the pokemon name values
     */
    public static MappedValues[] getPKNAME() {
        return new MappedValues[]{PKNAME0, PKNAME1, PKNAME2, PKNAME3, PKNAME4, PKNAME5};
    }

    /**
     * The pokemon nick pointers
     *
     * @return the pokemon nick values
     */
    public static MappedValues[] getPKNICK() {
        return new MappedValues[]{PKN0, PKN1};
    }

    /**
     * The trainer name/nick pointers
     *
     * @return the trainer pointer values
     */
    public static MappedValues[] getTN() {
        return new MappedValues[]{TNA0, TNA1, TNI0, TNI1};
    }

    /**
     * The entries pointer or not that are able to be made blank without affecting the program
     *
     * @return the nullable values
     */
    public static MappedValues[] getNullable() {
        return new MappedValues[]{RETURN, DIALOGUE_BREAK, CL0, CL1, CL2, PKN0, PKN1};
    }

    /**
     * The entries that require correcting in some manner
     *
     * @return the values that denote needing correction
     */
    public static MappedValues[] getCorrectable() {
        return new MappedValues[]{LINE_BREAK, INCORRECT_APOSTROPHE};
    }

    public String getValue() {
        return value;
    }
}