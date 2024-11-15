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

    public Purchase discount(Purchase purchase, ItemStock stock) {
        Promotion promotion = stock.getPromotion();

        if (!Promotion.isAvailable(promotion, DateTimes.now())) {
            purchaseService.reduceOnlyNormalQuantity(purchase);
            return discountService.discountByNoPromotion(purchase);
        }
        return discountByAvailablePromotion(purchase, promotion, stock);
    }

    public Purchase discountByAvailablePromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        int needCount = purchase.getNeedCount();
        int promotionQuantity = stock.getPromotionQuantity();

        if (needCount == promotionQuantity) {
            return discountByAsNeeded(purchase, promotion, promotionQuantity);
        }
        if (needCount > promotionQuantity) {
            return needMoreThanPromotion(purchase, promotion, stock);
        }
        return needLessThanBundleOrNot(purchase, promotion, stock);
    }

    public Purchase discountByAsNeeded(Purchase purchase, Promotion promotion, int promotionQuantity) {
        purchaseService.reduceByNeedCount(purchase, promotion, promotionQuantity);
        return discountService.discountByPromotion(purchase, promotion);
    }

    public Purchase needMoreThanPromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();
        int extraCount = purchase.getNeedCount() - stock.getPurchasedPromotionCount();

        if (allowsBuyingExtra(purchase, extraCount)) {
            purchaseService.reduceNormalAndPromotion(purchase, promotion, stock);
            return discountService.discountOnlyInPromotion(purchase, promotion, promotionQuantity);
        }
        purchaseService.reduceByManyBundles(purchase, promotion, promotionQuantity);
        return discountService.discountOnlyInManyBundles(purchase, promotion, promotionQuantity);
    }
    private boolean allowsBuyingExtra(Purchase purchase, Integer extraCount) {
        String message = String.format(PROMOTION_NOT_AVAILABLE.toString(), purchase.getName(), extraCount);

        return inputView.retryYesOrNo(message);
    }

    public Purchase needLessThanBundleOrNot(Purchase purchase, Promotion promotion, ItemStock stock) {
        int needCount = purchase.getNeedCount();

        if (needCount < promotion.getBundleCount()) {
            return needLessThanOneBundle(purchase, promotion, stock);
        }
        return discountByAsNeeded(purchase, promotion, stock.getPromotionQuantity());
    }

    public Purchase needLessThanOneBundle(Purchase purchase, Promotion promotion, ItemStock stock) {
        if (allowsPromotion(purchase, promotion)) {
            purchaseService.reduceByOneBundle(purchase, promotion, stock.getPromotionQuantity());
            return discountService.discountByOneBundle(purchase, promotion);
        }
        purchaseService.reduceByNeedCount(purchase, promotion, stock.getPromotionQuantity());
        return discountService.discountByNoPromotion(purchase);
    }
    private boolean allowsPromotion(Purchase purchase, Promotion promotion) {
        String message = String.format(PROMOTION_AVAILABLE.toString(), purchase.getName(), promotion.getFreeCount());

        return inputView.retryYesOrNo(message);
    }
}
