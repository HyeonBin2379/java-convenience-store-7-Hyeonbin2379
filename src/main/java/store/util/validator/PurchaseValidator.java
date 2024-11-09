package store.util.validator;

import static store.util.message.ExceptionMessage.OTHER_INPUT_ERROR;
import static store.util.message.ExceptionMessage.PURCHASED_ITEM_NOT_EXIST;
import static store.util.message.ExceptionMessage.PURCHASE_EXCEED_INVENTORY;

import java.util.List;
import store.config.AppConfig;
import store.model.Inventory;
import store.model.Purchase;

public class PurchaseValidator {

    public static void validatePurchaseItem(String name, Integer purchaseCount) {
        validateItemExist(name);
        validatePurchaseCount(name, purchaseCount);
    }

    public static void validateItemExist(String name) {
        List<Inventory> found = AppConfig.getInventoryDao().findByName(name);
        if (found.isEmpty()) {
            throw new IllegalArgumentException(PURCHASED_ITEM_NOT_EXIST.getMessage());
        }
    }

    public static void validatePurchaseCount(String name, Integer purchaseCount) {
        int normal = AppConfig.getInventoryDao().findNormalQuantity(name);
        int promotion = AppConfig.getInventoryDao().findPromotionQuantity(name);

        if ((normal+promotion) < purchaseCount) {
            throw new IllegalArgumentException(PURCHASE_EXCEED_INVENTORY.getMessage());
        }
    }

    public static void validateDuplicatedItem(List<Purchase> purchases) {
        int distinctCount = (int) purchases.stream()
                .map(Purchase::getName)
                .distinct()
                .count();
        if (distinctCount != purchases.size()) {
            throw new IllegalArgumentException(OTHER_INPUT_ERROR.getMessage());
        }
    }
}
