package store.service;

import store.dao.InventoryDao;
import store.model.ItemStock;
import store.model.Promotion;
import store.model.Purchase;

public class PromotionDiscountService {

    private final InventoryDao inventoryDao;

    public PromotionDiscountService(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    public Purchase updateNormalQuantityNoPromotion(Purchase purchase) {
        int normalQuantity = inventoryDao.findNormalQuantity(purchase.getName());

        normalQuantity -= purchase.getTotalNeedCount();
        purchase.addNonPromotionCount(purchase.getTotalNeedCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), Promotion.NONE), normalQuantity);

        return purchase;
    }

    public Purchase updateByLessThanPromotionCount(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();

        promotionQuantity -= promotion.getPromotionCount();
        purchase.setTotalNeedCount(promotion.getPromotionCount());
        purchase.addFreeItemCount(promotion.getFreeItemCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);

        return purchase;
    }

    public Purchase updateByOriginalNeedCount(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();

        promotionQuantity -= purchase.getTotalNeedCount();
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);

        return purchase;
    }

    public Purchase updateByPromotionDiscount(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();
        int totalNeedCount = purchase.getTotalNeedCount();

        promotionQuantity -= totalNeedCount;
        purchase.addFreeItemCount(totalNeedCount/promotion.getPromotionCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);

        return purchase;
    }

    public Purchase updateByMoreThanPromotionQuantity(Purchase purchase, Promotion promotion, ItemStock stock) {
        int normalQuantity = stock.getNormalQuantity();
        int promotionQuantity = stock.getPromotionQuantity();

        normalQuantity -= (purchase.getTotalNeedCount() - promotionQuantity);
        purchase.addNonPromotionCount(purchase.getTotalNeedCount()-promotionQuantity);
        purchase.addFreeItemCount(promotionQuantity/promotion.getPromotionCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), 0);
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), Promotion.NONE), normalQuantity);

        return purchase;
    }

    public Purchase updateOnlyPromotionQuantity(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();

        purchase.setTotalNeedCount(promotionQuantity);
        purchase.addFreeItemCount(promotionQuantity/promotion.getPromotionCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), 0);

        return purchase;
    }
}
