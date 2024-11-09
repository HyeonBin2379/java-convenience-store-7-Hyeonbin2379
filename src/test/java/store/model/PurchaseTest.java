package store.model;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static store.util.message.ExceptionMessage.PURCHASED_ITEM_NOT_EXIST;
import static store.util.message.ExceptionMessage.PURCHASE_EXCEED_INVENTORY;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseTest {

    @Test
    @DisplayName("입력한 이름에 해당하는 상품이 없으면 예외가 발생한다.")
    void throwExceptionIfPurchaseItemNotExist() {
        List<String> param = List.of("아이스크림", "1");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Purchase(param))
                .withMessageStartingWith(PURCHASED_ITEM_NOT_EXIST.getMessage());
    }

    @Test
    @DisplayName("입력한 이름에 해당하는 상품의 구매 수량보다 재고 수량이 부족하면 예외가 발생한다.")
    void throwExceptionIfPurchaseQuantityExceedInventory() {
        List<String> param = List.of("컵라면", "12");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Purchase(param))
                .withMessageContainingAll(PURCHASE_EXCEED_INVENTORY.getMessage());
    }

    @Test
    @DisplayName("개별 상품의 할인 전 구매 비용은 구매한 상품의 가격과 구매 수량의 곱이다.")
    void calculateCostIfBeforePurchase() {
        Purchase purchase = new Purchase(List.of("콜라", "6"));
        int expectedNeedCount = 6;
        int expectedCost = 6000;

        assertEquals(expectedNeedCount, purchase.getTotalNeedCount());
        assertEquals(expectedCost, purchase.calculateCostBeforeDiscount());
    }

    @Test
    @DisplayName("개별 상품의 구매 수량이 변동되면 할인 전 구매 비용은 변동된 수량을 적용하여 계산한다.")
    void calculateCostIfPurchaseCountChanged() {
        Purchase purchase = new Purchase(List.of("콜라", "1"));
        int changedCount = 3;
        int expectedCost = 3000;

        purchase.setTotalNeedCount(changedCount);

        assertEquals(changedCount, purchase.getTotalNeedCount());
        assertEquals(expectedCost, purchase.calculateCostBeforeDiscount());
    }

    @Test
    @DisplayName("프로모션 할인 금액은 구매한 프로모션 상품의 가격과 무료 증정된 수량의 곱이다.")
    void calculatePromotionDiscountTest() {
        Purchase purchase = new Purchase(List.of("콜라", "6"));
        int freeItemCount = 2;
        int expectedDiscount = 2000;

        purchase.addFreeItemCount(freeItemCount);

        assertEquals(expectedDiscount, purchase.calculatePromotionDiscount());
    }

    @Test
    @DisplayName("개별 상품의 프로모션 미적용 금액은 상품의 가격과 일반 재고에서 구매한 수량의 곱이다.")
    void calculateNonPromotionCostTest() {
        Purchase purchase = new Purchase(List.of("물", "5"));
        int nonPromotionCount = 3;
        int expectedNonPromotionCost = 1500;

        purchase.addNonPromotionCount(nonPromotionCount);

        assertEquals(expectedNonPromotionCost, purchase.calculateNonPromotionBuyCost());
    }
}