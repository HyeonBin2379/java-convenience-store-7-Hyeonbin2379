package store.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.DiscountController;
import store.service.PromotionService;

class DiscountConfigTest {

    @Test
    @DisplayName("생성한 DiscountController 객체는 싱글톤 객체이다.")
    void getDiscountControllerTest() {
        DiscountController sample1 = DiscountConfig.getDiscountController();
        assertNotNull(sample1);

        DiscountController sample2 = DiscountConfig.getDiscountController();
        assertSame(sample1, sample2);
    }

    @Test
    @DisplayName("생성한 PromotionDiscountService 객체는 싱글톤 객체이다.")
    void getPromotionDiscountServiceTest() {
        PromotionService sample1 = DiscountConfig.getPromotionDiscountService();
        assertNotNull(sample1);

        PromotionService sample2 = DiscountConfig.getPromotionDiscountService();
        assertSame(sample1, sample2);
    }
}