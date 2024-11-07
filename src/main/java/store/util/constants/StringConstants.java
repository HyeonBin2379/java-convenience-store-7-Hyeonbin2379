package store.util.constants;

public final class StringConstants {

    public static final String PURCHASE_DELIMITER = ",";
    public static final String CONSECUTIVE_DELIMITER = ",,";

    public static final String YES = "Y";
    public static final String NO = "N";

    public static final String PURCHASE_INFO_FORMAT = "^\\[([^0-9]+)-([1-9][0-9]*)]$";
    public static final String PURCHASE_INFO_DELIMITER = "[\\[\\]\\-]";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String OUT_OF_STOCK = "재고 없음";
    public static final String NO_PROMOTION = "";

    private StringConstants() {
    }
}
