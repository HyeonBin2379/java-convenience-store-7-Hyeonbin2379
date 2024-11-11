package store.util.message;

public final class OutputMessage {

    public static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    public static final String ITEM_INFO = "- %s %,d원 %s %s\n";
    public static final String ITEM_COUNT = "%,d개";

    public static final String RECEIPT_TITLE = "\n==============W 편의점================\n%-16s\t%-8s%s\n";
    public static final String PURCHASED_ITEM = "%-16s\t%,-9d %,-7d\n";

    public static final String GIVEAWAY_TITLE = "=============증     정===============";
    public static final String GIVEAWAY_ITEM = "%-16s\t%,-16d\n";

    public static final String PURCHASE_RESULT_TITLE = "====================================";
    public static final String OUTPUT_TOTAL_COST = "%-16s\t%,-9d %,-7d\n";
    public static final String OUTPUT_TOTAL_PROMOTION_DISCOUNT = "%-24s\t  -%,-4d\n";
    public static final String OUTPUT_TOTAL_MEMBERSHIP_DISCOUNT = "%-24s\t  -%,-4d\n";
    public static final String OUTPUT_FINAL_COST = "%-24s\t  %,6d\n";
}
