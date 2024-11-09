package store.controller;

import static store.util.message.InputMessage.PROMOTION_AVAILABLE;
import static store.util.message.InputMessage.PROMOTION_NOT_AVAILABLE;

import camp.nextstep.edu.missionutils.DateTimes;
import store.config.AppConfig;
import store.config.PurchaseConfig;
import store.model.ItemStock;
import store.model.Promotion;
import store.model.Purchase;
import store.service.DiscountService;
import store.service.PurchaseService;
import store.view.InputView;

public class DiscountController {

    private final InputView inputView;

    private final PurchaseService purchaseService;
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.inputView = AppConfig.getInputView();
        this.purchaseService = PurchaseConfig.getPurchaseService();
        this.discountService = discountService;
    }

    public Purchase discountByPromotionOrNot(Purchase purchase, ItemStock stock) {
        Promotion promotion = stock.getPromotion();

        if (!Promotion.isAvailable(promotion, DateTimes.now())) {
            purchaseService.reduceOnlyNormalQuantity(purchase);
            return discountService.discountByNoPromotion(purchase);
        }
        return discountByAvailablePromotion(purchase, stock);
    }

    public Purchase discountByAvailablePromotion(Purchase purchase, ItemStock stock) {
        Promotion promotion = stock.getPromotion();
        int needCount = purchase.getNeedCount();
        int promotionQuantity = stock.getPromotionQuantity();

        if (needCount < promotionQuantity) {
            return needLessThanBundleOrNot(purchase, promotion, stock);
        }
        return needNoLessThanPromotion(purchase, promotion, stock);
    }


    public Purchase needLessThanOneBundle(Purchase purchase, Promotion promotion, ItemStock stock) {
        if (allowsPromotion(purchase, promotion)) {
            purchaseService.reduceByOneBundle(purchase, promotion, stock.getPromotionQuantity());
            return discountService.discountByOneBundle(purchase, promotion);
        }
        purchaseService.reduceByNeedCount(purchase, promotion, stock.getPromotionQuantity());
        return purchase;
    }
    private boolean allowsPromotion(Purchase purchase, Promotion promotion) {
        return inputView.retryYesOrNo(makeMessage(purchase, promotion));
    }
    private String makeMessage(Purchase purchase, Promotion promotion) {
        return String.format(PROMOTION_AVAILABLE.toString(), purchase.getName(), promotion.getFreeCount());
    }


    public Purchase needLessThanBundleOrNot(Purchase purchase, Promotion promotion, ItemStock stock) {
        int needCount = purchase.getNeedCount();

        if (needCount < promotion.getBundleCount()) {
            return needLessThanOneBundle(purchase, promotion, stock);
        }
        purchaseService.reduceByNeedCount(purchase, promotion, stock.getPromotionQuantity());
        return discountService.discountByPromotion(purchase, promotion);
    }


    public Purchase needNoLessThanPromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();
        int promotionCount = promotion.getBundleCount();
        int extraCount = purchase.getNeedCount() - promotionCount*(promotionQuantity/promotionCount);

        if (allowsBuyingExtra(purchase, extraCount)) {
            purchaseService.reduceNormalAndPromotion(purchase, promotion, stock);
            return discountService.discountOnlyInPromotion(purchase, promotion, promotionQuantity);
        }
        purchaseService.reduceByManyBundles(purchase, promotion, promotionQuantity);
        return discountService.discountOnlyInManyBundles(purchase, promotion, promotionQuantity);
    }
    private boolean allowsBuyingExtra(Purchase purchase, int extraCount) {
        return inputView.retryYesOrNo(makeMessage(purchase, extraCount));
    }
    private String makeMessage(Purchase purchase, int extraCount) {
        return String.format(PROMOTION_NOT_AVAILABLE.toString(), purchase.getName(), extraCount);
    }
}
