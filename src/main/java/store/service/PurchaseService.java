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

    public void reduceOnlyNormalQuantity(Purchase purchase) {
        int normalCount = inventoryDao.findNormalQuantity(purchase.getName());
        int afterPurchase = normalCount - purchase.getNeedCount();
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), Promotion.NONE), afterPurchase);
    }

    public void reduceByOneBundle(Purchase purchase, Promotion promotion, int promotionQuantity) {
        promotionQuantity -= promotion.getBundleCount();
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);
    }

    public void reduceByNeedCount(Purchase purchase, Promotion promotion, int promotionQuantity) {
        promotionQuantity -= purchase.getNeedCount();
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);
    }

    public void reduceNormalAndPromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        int normalCount = stock.getNormalQuantity();
        int promotionQuantity = stock.getPromotionQuantity();

        normalCount -= (purchase.getNeedCount() - promotionQuantity);
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), 0);
        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), Promotion.NONE), normalCount);
    }

    public void reduceByManyBundles(Purchase purchase, Promotion promotion, int promotionQuantity) {
        int freeItemCount = promotionQuantity/promotion.getBundleCount();

        promotionQuantity -= freeItemCount*promotion.getBundleCount();

        inventoryDao.update(inventoryDao.findIdBy(purchase.getName(), promotion), promotionQuantity);
    }
}
