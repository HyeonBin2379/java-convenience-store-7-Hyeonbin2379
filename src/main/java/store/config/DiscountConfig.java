package store.config;

import store.controller.DiscountController;
import store.service.PromotionDiscountService;

public class DiscountConfig {

    private static PromotionDiscountService promotionDiscountService = getPromotionDiscountService();
    private static DiscountController discountController = getDiscountController();

    private DiscountConfig() {
    }

    public static DiscountController getDiscountController() {
        if (discountController == null) {
            discountController = new DiscountController(getPromotionDiscountService());
        }
        return discountController;
    }

    public static PromotionDiscountService getPromotionDiscountService() {
        if (promotionDiscountService == null) {
            promotionDiscountService = new PromotionDiscountService(AppConfig.getInventoryDao());
        }
        return promotionDiscountService;
    }
}
