package store.util.message;

public final class InputMessage {

    public static final String SELECT_ITEM = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";

    public static final String PROMOTION_AVAILABLE = "\n현재 %s은(는) %,d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public static final String PROMOTION_NOT_AVAILABLE = "\n현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";

    public static final String MEMBERSHIP_DISCOUNT = "\n멤버십 할인을 받으시겠습니까? (Y/N)";
    public static final String RETRY_PURCHASE = "\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    private InputMessage() {
    }
}