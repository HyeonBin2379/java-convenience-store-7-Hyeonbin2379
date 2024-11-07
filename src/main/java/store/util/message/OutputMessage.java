package store.util.message;

public final class OutputMessage {

    public static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    public static final String OUTPUT_ITEM_INFO = "- %s %,d원 %s %s\n";

    public static final String OUTPUT_RECEIPT_TITLE = "\n===========W 편의점=============\n상품명\t\t수량\t금액";
    public static final String OUTPUT_PURCHASED_ITEM = "%s\t\t%,d \t%,d\n";

    public static final String OUTPUT_GIVEAWAY_TITLE = "===========증\t정=============";
    public static final String OUTPUT_GIVEAWAY_ITEM = "%s\t\t%,d\n";

    public static final String OUTPUT_PURCHASE_RESULT_TITLE = "==============================";
    public static final String OUTPUT_TOTAL_COST = "총구매액\t\t%,d\t%,d\n";
    public static final String OUTPUT_TOTAL_PROMOTION_DISCOUNT = "행사할인\t\t\t-%,d\n";
    public static final String OUTPUT_TOTAL_MEMBERSHIP_DISCOUNT = "멤버십할인\t\t\t-%,d\n";
    public static final String OUTPUT_FINAL_COST = "내실돈\t\t\t %,d\n";
}
