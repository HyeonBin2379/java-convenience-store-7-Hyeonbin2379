package store.util.validator;

import static store.util.message.ExceptionMessage.PURCHASED_ITEM_NOT_EXIST;
import static store.util.message.ExceptionMessage.PURCHASE_EXCEED_INVENTORY;

import java.util.List;
import store.config.AppConfig;
import store.model.Inventory;

public class PurchaseValidator {

    public static void validatePurchaseItem(String name, int purchaseCount) {
        List<Inventory> found = AppConfig.getInventoryDao().findByName(name);
        validateItemExist(found);
        validatePurchaseCount(found, purchaseCount);
    }

    public static void validateItemExist(List<Inventory> found) {
        if (found.isEmpty()) {
            throw new IllegalArgumentException(PURCHASED_ITEM_NOT_EXIST.getMessage());
        }
    }

    public static void validatePurchaseCount(List<Inventory> found, int purchaseCount) {
        int quantitySum = found.stream()
                .map(Inventory::getQuantity)
                .reduce(0, Integer::sum);
        if (quantitySum < purchaseCount) {
            throw new IllegalArgumentException(PURCHASE_EXCEED_INVENTORY.getMessage());
        }
    }
}
