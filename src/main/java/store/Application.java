package store;

import static store.config.AppConfig.inventoryDao;
import static store.config.AppConfig.outputView;
import static store.config.AppConfig.retryController;
import static store.util.message.InputMessage.MEMBERSHIP_DISCOUNT;
import static store.util.message.InputMessage.PROMOTION_AVAILABLE;
import static store.util.message.InputMessage.RETRY_PURCHASE;

import java.io.IOException;
import store.model.Promotion;
import store.model.Purchase;
import store.model.Purchases;

public class Application {

    public static void main(String[] args) throws IOException {
        do {
            outputView.displayInventories(inventoryDao.getAll());
            Purchases purchases = retryController.retryPurchaseItemInput();
            for (Purchase purchase : purchases.getAll()) {

            }
            if (retryController.retryOptionInput(MEMBERSHIP_DISCOUNT)) {
                purchases.setMembershipDiscount();
            }
            outputView.displayReceipt(purchases);
        } while(retryController.retryOptionInput(RETRY_PURCHASE));
    }

    private static void discountByPromotion(Purchase purchase, Promotion promotion) {

    }
    private static String makeString(String name, int giveawayCount) {
        return String.format(PROMOTION_AVAILABLE, name, giveawayCount);
    }
}