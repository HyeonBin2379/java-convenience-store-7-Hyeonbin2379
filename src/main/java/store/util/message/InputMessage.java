package store.util.message;

public enum InputMessage {

    SELECT_ITEM("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    PROMOTION_AVAILABLE("현재 %s은(는) %,d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    PROMOTION_NOT_AVAILABLE("현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),

    MEMBERSHIP_DISCOUNT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    RETRY_PURCHASE("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

    private final String message;

    InputMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return System.lineSeparator() + message;
    }
}
