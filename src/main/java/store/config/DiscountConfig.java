package store.config;

import store.controller.DiscountController;
import store.service.DiscountService;

public final class DiscountConfig {

    private static DiscountService promotionDiscountService = getPromotionDiscountService();
    private static DiscountController discountController = getDiscountController();

    private DiscountConfig() {
    }

    public static DiscountController getDiscountController() {
        if (discountController == null) {
            discountController = new DiscountController(getPromotionDiscountService());
        }
        return discountController;
    }

    public static DiscountService getPromotionDiscountService() {
        if (promotionDiscountService == null) {
            promotionDiscountService = new DiscountService();
        }
        return promotionDiscountService;
    }
}
