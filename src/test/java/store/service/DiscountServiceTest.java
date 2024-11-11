package store.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import store.config.AppConfig;
import store.model.Promotion;
import store.model.Purchase;

class DiscountServiceTest {

    private static DiscountService discountService;

    @BeforeAll
    public static void setup() {
        discountService = new DiscountService();
    }

    @Test
    @DisplayName("상품 구매 시 프로모션 할인을 적용하지 않으면 입력한 구매 수량만큼 프로모션 미적용 수량이 증가한다.")
    void discountByNoPromotionTest() {
        Purchase purchase = new Purchase(List.of("물", "5"));
        int expected = purchase.calculateCostBeforeDiscount();

        Purchase result = discountService.discountByNoPromotion(purchase);
        assertEquals(expected, result.calculateNormalBuyCost());
    }

    @Test
    @DisplayName("구매 수량이 프로모션 상품 1묶음보다 적을 때, 프로모션 할인을 적용하면 구매 수량을 1묶음의 수량으로 갱신한다.")
    void discountByOneBundlePurchaseCountTest() {
        Purchase purchase = new Purchase(List.of("오렌지주스", "1"));
        Promotion promotion = AppConfig.getInventoryDao().findPromotion(purchase.getName());
        int expected = promotion.getBundleCount();

        Purchase result = discountService.discountByOneBundle(purchase, promotion);
        assertEquals(expected, result.getNeedCount());
    }

    @Test
    @DisplayName("구매 수량이 프로모션 상품 1묶음보다 적을 때, 프로모션 할인을 적용하면 무료 증정 수량이 1 증가한다.")
    void discountByOneBundleTest() {
        Purchase purchase = new Purchase(List.of("오렌지주스", "1"));
        Promotion promotion = AppConfig.getInventoryDao().findPromotion(purchase.getName());
        int expected = promotion.getFreeCount();

        Purchase result = discountService.discountByOneBundle(purchase, promotion);
        assertEquals(expected, result.getFreeCount());
    }

    @ParameterizedTest
    @DisplayName("프로모션 재고가 충분하고 구매 수량이 프로모션 1묶음 이상일 때, 증정 수량은 구매 수량을 프로모션 1묶음으로 나눈 몫이다.")
    @MethodSource("provideParams")
    void discountByPromotionFreeCountTest(List<String> params) {
        Purchase purchase = new Purchase(params);
        Promotion promotion = AppConfig.getInventoryDao().findPromotion(purchase.getName());
        int expected = purchase.getNeedCount() / promotion.getBundleCount();

        Purchase result = discountService.discountByPromotion(purchase, promotion);
        assertEquals(expected, result.getFreeCount());
    }

    private static Stream<List<String>> provideParams() {
        return Stream.of(List.of("콜라", "1"), List.of("콜라", "3"), List.of("콜라", "8"));
    }

    @Test
    @DisplayName("프로모션 재고가 부족할 때 부족분도 구매하면 프로모션 미적용 수량은 구매 수량에서 구매 가능한 프로모션 묶음들을 뺀 값이다.")
    void discountOnlyInPromotionNormalCountTest() {
        Purchase purchase = new Purchase(List.of("감자칩", "8"));
        Promotion promotion = AppConfig.getInventoryDao().findPromotion(purchase.getName());
        int promotionQuantity = AppConfig.getInventoryDao().findPromotionQuantity(purchase.getName());
        int oneBundle = promotion.getBundleCount();

        int expected = purchase.getNeedCount() - (promotionQuantity/oneBundle)*oneBundle;
        Purchase result = discountService.discountOnlyInPromotion(purchase, promotion, promotionQuantity);

        assertEquals(expected*1500, result.calculateNormalBuyCost());
    }

    @Test
    @DisplayName("프로모션 재고가 부족할 때, 부족분도 정가로 구매하면 증정 수량은 프로모션 재고 수량을 프로모션 1묶음으로 나눈 몫이다.")
    void discountOnlyInPromotionFreeCountTest() {
        Purchase purchase = new Purchase(List.of("감자칩", "8"));
        Promotion promotion = AppConfig.getInventoryDao().findPromotion(purchase.getName());
        int promotionQuantity = AppConfig.getInventoryDao().findPromotionQuantity(purchase.getName());

        int expected = promotionQuantity / promotion.getBundleCount();
        Purchase result = discountService.discountOnlyInPromotion(purchase, promotion, promotionQuantity);

        assertEquals(expected, result.getFreeCount());
    }

    @Test
    @DisplayName("프로모션 재고가 부족할 때, 부족분을 구매하지 않으면 구매 수량을 프로모션 재고 수량으로 갱신한다.")
    void discountOnlyInManyBundlesNeedCountTest() {
        Purchase purchase = new Purchase(List.of("감자칩", "8"));
        Promotion promotion = AppConfig.getInventoryDao().findPromotion(purchase.getName());
        int promotionQuantity = AppConfig.getInventoryDao().findPromotionQuantity(purchase.getName());
        int oneBundle = promotion.getBundleCount();

        int expected = purchase.getNeedCount() - (promotionQuantity/oneBundle) * oneBundle;
        Purchase result = discountService.discountOnlyInManyBundles(purchase, promotion, promotionQuantity);

        assertEquals(expected, result.getNeedCount());
    }

    @Test
    @DisplayName("프로모션 재고가 부족할 때 부족분을 구매하지 않으면 증정 수량은 프로모션 재고 수량을 프로모션 1묶음으로 나눈 몫이다.")
    void discountOnlyInManyBundlesFreeCountTest() {
        Purchase purchase = new Purchase(List.of("감자칩", "8"));
        Promotion promotion = AppConfig.getInventoryDao().findPromotion(purchase.getName());
        int promotionQuantity = AppConfig.getInventoryDao().findPromotionQuantity(purchase.getName());

        int expected = promotionQuantity / promotion.getBundleCount();
        Purchase result = discountService.discountOnlyInManyBundles(purchase, promotion, promotionQuantity);

        assertEquals(expected, result.getFreeCount());
    }
}