package store.config;

import store.controller.DiscountController;
import store.service.PromotionService;

public final class DiscountConfig {

    private static PromotionService promotionDiscountService = getPromotionDiscountService();
    private static DiscountController discountController = getDiscountController();

    private DiscountConfig() {
    }

    public static DiscountController getDiscountController() {
        if (discountController == null) {
            discountController = new DiscountController(getPromotionDiscountService());
        }
        return discountController;
    }

    public static PromotionService getPromotionDiscountService() {
        if (promotionDiscountService == null) {
            promotionDiscountService = new PromotionService();
        }
        return promotionDiscountService;
    }
}
