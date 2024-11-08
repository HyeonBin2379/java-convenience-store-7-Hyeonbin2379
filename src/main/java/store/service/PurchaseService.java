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
}
