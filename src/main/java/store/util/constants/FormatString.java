package store.util.constants;

public final class FormatString {

    public static final String PURCHASE_UNIT_DELIMITER = ",";
    public static final String CONSECUTIVE_UNIT_DELIMITER = ",,";
    public static final String CONSECUTIVE_INFO_DELIMITER = "--";

    public static final String PURCHASE_INFO_FORMAT = "^\\[([^0-9]+)-([1-9][0-9]*)\\]$";
    public static final String PURCHASE_INFO_DELIMITER = "[\\[\\]\\-]";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private FormatString() {
    }
}
