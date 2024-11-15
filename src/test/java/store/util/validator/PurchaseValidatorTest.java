package store.util.validator;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static store.util.constants.StringConstants.ERROR_MESSAGE;
import static store.util.message.ExceptionMessage.PURCHASED_ITEM_NOT_EXIST;
import static store.util.message.ExceptionMessage.PURCHASE_EXCEED_INVENTORY;
import static store.util.validator.PurchaseValidator.validateDuplicatedItem;
import static store.util.validator.PurchaseValidator.validateItemExist;
import static store.util.validator.PurchaseValidator.validatePurchaseCount;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Purchase;

class PurchaseValidatorTest {

    @Test
    @DisplayName("입력한 상품의 이름이 주어진 재고 데이터에 존재하지 않으면 예외가 발생한다.")
    void validateItemExistExceptionTest() {
        String notExistItem = "아이스크림";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> validateItemExist(notExistItem))
                .withMessageContaining(PURCHASED_ITEM_NOT_EXIST.getMessage());
    }

    @Test
    @DisplayName("입력한 상품에 관한 재고 수량의 총합이 입력한 구매 수량보다 적으면 예외가 발생한다.")
    void validatePurchaseCountExceptionTest() {
        String existItem = "컵라면";
        int needCount = 12;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> validatePurchaseCount(existItem, needCount))
                .withMessageContainingAll(PURCHASE_EXCEED_INVENTORY.getMessage());
    }

    @Test
    @DisplayName("동일한 상품명을 중복 입력한 경우 예외가 발생한다.")
    void validateDuplicatedItemExceptionTest() {
        List<Purchase> purchaseSample
                = List.of(new Purchase(List.of("컵라면", "1")), new Purchase(List.of("컵라면", "2")));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> validateDuplicatedItem(purchaseSample))
                .withMessageStartingWith(ERROR_MESSAGE);
    }
}