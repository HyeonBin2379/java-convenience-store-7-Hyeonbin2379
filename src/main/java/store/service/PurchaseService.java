package store.service;

import java.util.List;
import store.dao.InventoryDao;
import store.model.Inventory;
import store.model.ItemStock;
import store.model.Promotion;
import store.model.Purchase;

public class PurchaseService {

    private final InventoryDao inventoryDao;

    public PurchaseService(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    public List<Inventory> getAllInventories() {
        return inventoryDao.getAll();
    }

    public ItemStock getItemStockInfo(Purchase purchase) {
        Promotion promotion = inventoryDao.findPromotion(purchase.getName());
        int promotionQuantity = inventoryDao.findPromotionQuantity(purchase.getName());
        int normalQuantity = inventoryDao.findNormalQuantity(purchase.getName());
        return new ItemStock(promotion, promotionQuantity, normalQuantity);
    }

    public Purchase updateNormalQuantityNoPromotion(Purchase purchase) {
        int normalQuantity = inventoryDao.findNormalQuantity(purchase.getName());

        normalQuantity -= purchase.getTotalNeedCount();
        purchase.addBuyCount(purchase.getTotalNeedCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), Promotion.NONE), normalQuantity);
        return purchase;
    }

    public Purchase updateByLessThanPromotionCount(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();

        promotionQuantity -= promotion.getPromotionCount();
        purchase.setTotalNeedCount(promotion.getPromotionCount());
        purchase.addGetCount(promotion.getGiveawayCount());
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
        purchase.addGetCount(totalNeedCount/promotion.getPromotionCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);

        return purchase;
    }

    public Purchase updateNotEnoughPromoQuantity(Purchase purchase, Promotion promotion, ItemStock stock) {
        int normalQuantity = stock.getNormalQuantity();
        int promotionQuantity = stock.getPromotionQuantity();

        normalQuantity -= purchase.getTotalNeedCount() - promotionQuantity;
        purchase.addBuyCount(normalQuantity);
        purchase.addGetCount(promotionQuantity/promotion.getPromotionCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), 0);
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), Promotion.NONE), normalQuantity);

        return purchase;
    }

    public Purchase updateOnlyPromotionQuantity(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();

        purchase.setTotalNeedCount(promotionQuantity);
        purchase.addGetCount(promotionQuantity/promotion.getPromotionCount());
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), 0);

        return purchase;
    }
}
