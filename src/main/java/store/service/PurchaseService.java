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

    public synchronized void reduceOnlyNormalQuantity(Purchase purchase) {
        int normalQuantity = inventoryDao.findNormalQuantity(purchase.getName());
        normalQuantity -= purchase.getNeedCount();

        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), Promotion.NULL), normalQuantity);
    }

    public synchronized void reduceByOneBundle(Purchase purchase, Promotion promotion, Integer promotionQuantity) {
        promotionQuantity -= promotion.getBundleCount();
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);
    }

    public synchronized void reduceByNeedCount(Purchase purchase, Promotion promotion, Integer promotionQuantity) {
        promotionQuantity -= purchase.getNeedCount();
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);
    }

    public synchronized void reduceNormalAndPromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        int normalCount = stock.getNormalQuantity();
        int promotionQuantity = stock.getPromotionQuantity();

        normalCount -= (purchase.getNeedCount() - promotionQuantity);
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), 0);
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), Promotion.NULL), normalCount);
    }

    public synchronized void reduceByManyBundles(Purchase purchase, Promotion promotion, Integer promotionQuantity) {
        int freeItemCount = promotionQuantity/promotion.getBundleCount();

        promotionQuantity -= freeItemCount * promotion.getBundleCount();
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);
    }
}
