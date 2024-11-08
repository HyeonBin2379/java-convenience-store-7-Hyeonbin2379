package store.controller;

import static store.util.message.InputMessage.PROMOTION_AVAILABLE;
import static store.util.message.InputMessage.PROMOTION_NOT_AVAILABLE;

import store.config.AppConfig;
import store.model.ItemStock;
import store.model.Promotion;
import store.model.Purchase;
import store.service.PromotionDiscountService;
import store.view.InputView;

public class DiscountController {

    private final InputView inputView = AppConfig.getInputView();
    private final PromotionDiscountService promotionDiscountService;

    public DiscountController(PromotionDiscountService promotionDiscountService) {
        this.promotionDiscountService = promotionDiscountService;
    }

    public Purchase discountByPromotion(Purchase purchase, ItemStock stock) {
        Promotion promotion = stock.getPromotion();
        if (promotion == Promotion.NONE || !promotion.isInProgress()) {
            return promotionDiscountService.updateNormalQuantityNoPromotion(purchase);
        }
        return discountByAvailablePromotion(purchase, stock);
    }


    public Purchase discountByAvailablePromotion(Purchase purchase, ItemStock stock) {
        Promotion promotion = stock.getPromotion();
        int needCount = purchase.getTotalNeedCount();
        int promotionQuantity = stock.getPromotionQuantity();

        if (needCount <= promotionQuantity) {
            return discountByEnoughPromotion(purchase, promotion, stock);
        }
        return discountByNotEnoughPromotion(purchase, promotion, stock);
    }


    public Purchase discountByEnoughPromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        int needCount = purchase.getTotalNeedCount();

        if (needCount < promotion.getPromotionCount()) {
            return discountByLessThanPromotion(purchase, promotion, stock);
        }
        return promotionDiscountService.updateByPromotionDiscount(purchase, promotion, stock);
    }


    public Purchase discountByLessThanPromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        if (inputView.retryYesOrNo(makePromotionAvailableMessage(purchase, promotion))) {
            return promotionDiscountService.updateByLessThanPromotionCount(purchase, promotion, stock);
        }
        return promotionDiscountService.updateByOriginalNeedCount(purchase, promotion, stock);
    }

    private String makePromotionAvailableMessage(Purchase purchase, Promotion promotion) {
        return String.format(PROMOTION_AVAILABLE, purchase.getName(), promotion.getGiveawayCount());
    }


    public Purchase discountByNotEnoughPromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();
        int promotionCount = promotion.getPromotionCount();
        int remainder = purchase.getTotalNeedCount() - promotionCount*(promotionQuantity/promotionCount);

        if (inputView.retryYesOrNo(makePromotionNotAvailableMessage(purchase, remainder))) {
            return promotionDiscountService.updateByMoreThanPromotionQuantity(purchase, promotion, stock);
        }
        return promotionDiscountService.updateOnlyPromotionQuantity(purchase, promotion,stock);
    }

    private String makePromotionNotAvailableMessage(Purchase purchase, int remainder) {
        return String.format(PROMOTION_NOT_AVAILABLE, purchase.getName(), remainder);
    }
}
