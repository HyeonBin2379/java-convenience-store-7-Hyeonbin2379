package store.service;

import store.model.Purchase;

public class DiscountService {

    public Purchase discountByNoPromotion(Purchase purchase) {
        purchase.addNormalCount(purchase.getNeedCount());
        return purchase;
    }

    public Purchase discountByOneBundle(Purchase purchase, int onePromotionBundle, int freeItemPerBundle) {
        purchase.setNeedCount(onePromotionBundle);
        purchase.addFreeItemCount(freeItemPerBundle);
        return purchase;
    }

    public Purchase discountByPromotion(Purchase purchase, int onePromotionBundle) {
        purchase.addFreeItemCount(purchase.getNeedCount()/onePromotionBundle);

        return purchase;
    }

    public Purchase discountOnlyInPromotion(Purchase purchase, int onePromotionBundle, int promotionQuantity) {
        int freeItemCount = promotionQuantity / onePromotionBundle;
        int totalBundleCount = freeItemCount * onePromotionBundle;

        purchase.addNormalCount(purchase.getNeedCount() - totalBundleCount);
        purchase.addFreeItemCount(freeItemCount);
        return purchase;
    }

    public Purchase discountOnlyInManyBundles(Purchase purchase, int onePromotionBundle, int promotionQuantity) {
        int freeItemCount = promotionQuantity / onePromotionBundle;
        int totalBundleCount = freeItemCount * onePromotionBundle;

        purchase.setNeedCount(totalBundleCount);
        purchase.addFreeItemCount(freeItemCount);

        return purchase;
    }
}
