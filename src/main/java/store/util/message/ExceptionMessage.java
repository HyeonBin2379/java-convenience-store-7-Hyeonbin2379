package store.util.message;

import static store.util.constants.StringConstants.ERROR_MESSAGE;

public enum ExceptionMessage {

    INPUT_FORMAT_INCORRECT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PURCHASED_ITEM_NOT_EXIST("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    PURCHASE_EXCEED_INVENTORY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    OTHER_INPUT_ERROR("잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format("%s %s", ERROR_MESSAGE, message);
    }
}
