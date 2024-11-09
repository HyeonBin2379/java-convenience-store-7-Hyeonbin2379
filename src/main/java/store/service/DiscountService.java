package store.service;

import store.model.Promotion;
import store.model.Purchase;

public class DiscountService {

    public Purchase discountByNoPromotion(Purchase purchase) {
        purchase.addNormalCount(purchase.getNeedCount());
        return purchase;
    }

    public Purchase discountByOneBundle(Purchase purchase, Promotion promotion) {
        purchase.setNeedCount(promotion.getBundleCount());
        purchase.addFreeItemCount(promotion.getFreeCount());
        return purchase;
    }

    public Purchase discountByPromotion(Purchase purchase, Promotion promotion) {
        purchase.addFreeItemCount(purchase.getNeedCount()/promotion.getBundleCount());
        purchase.addNormalCount(purchase.getNeedCount() % promotion.getBundleCount());

        return purchase;
    }

    public Purchase discountOnlyInPromotion(Purchase purchase, Promotion promotion, int promotionQuantity) {
        int freeItemCount = promotionQuantity / promotion.getBundleCount();
        int totalBundleCount = freeItemCount * promotion.getBundleCount();

        purchase.addNormalCount(purchase.getNeedCount() - totalBundleCount);
        purchase.addFreeItemCount(freeItemCount);
        return purchase;
    }

    public Purchase discountOnlyInManyBundles(Purchase purchase, Promotion promotion, int promotionQuantity) {
        int freeItemCount = promotionQuantity / promotion.getBundleCount();
        int totalBundleCount = freeItemCount * promotion.getBundleCount();

        purchase.setNeedCount(totalBundleCount);
        purchase.addFreeItemCount(freeItemCount);

        return purchase;
    }
}
