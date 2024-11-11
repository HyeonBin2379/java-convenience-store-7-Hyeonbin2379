package store.view;

import static store.util.constants.StringConstants.COST;
import static store.util.constants.StringConstants.FINAL_COST;
import static store.util.constants.StringConstants.ITEM_NAME;
import static store.util.constants.StringConstants.MEMBERSHIP_DISCOUNT;
import static store.util.constants.StringConstants.PROMOTION_DISCOUNT;
import static store.util.constants.StringConstants.QUANTITY;
import static store.util.constants.StringConstants.TOTAL_COST;
import static store.util.message.OutputMessage.OUTPUT_FINAL_COST;
import static store.util.message.OutputMessage.GIVEAWAY_ITEM;
import static store.util.message.OutputMessage.GIVEAWAY_TITLE;
import static store.util.message.OutputMessage.PURCHASED_ITEM;
import static store.util.message.OutputMessage.PURCHASE_RESULT_TITLE;
import static store.util.message.OutputMessage.RECEIPT_TITLE;
import static store.util.message.OutputMessage.OUTPUT_TOTAL_COST;
import static store.util.message.OutputMessage.OUTPUT_TOTAL_MEMBERSHIP_DISCOUNT;
import static store.util.message.OutputMessage.OUTPUT_TOTAL_PROMOTION_DISCOUNT;
import static store.util.message.OutputMessage.WELCOME_MESSAGE;

import java.util.List;
import store.model.Inventory;
import store.model.Purchase;
import store.model.PurchaseResult;
import store.model.Purchases;

public class OutputView {

    public void displayInventoryInfo(List<Inventory> inventories) {
        System.out.println(WELCOME_MESSAGE);
        inventories.forEach(inventory -> System.out.print(inventory.toString()));
    }

    public void displayReceipt(Purchases purchases) {
        displayTotalCost(purchases.getAll());
        displayFreeItemInfo(purchases.getAll());
        displayTotalResult(purchases.getResult());
    }

    public void displayTotalCost(List<Purchase> purchases) {
        System.out.printf(RECEIPT_TITLE, ITEM_NAME, QUANTITY, COST);
        for (Purchase purchase : purchases) {
            System.out.printf(PURCHASED_ITEM,
                    purchase.getName(),
                    purchase.getNeedCount(),
                    purchase.calculateCostBeforeDiscount());
        }
    }

    public void displayFreeItemInfo(List<Purchase> purchases) {
        System.out.println(GIVEAWAY_TITLE);
        for (Purchase purchase : purchases) {
            if (purchase.getFreeCount() > 0) {
                System.out.printf(GIVEAWAY_ITEM, purchase.getName(), purchase.getFreeCount());
            }
        }
    }

    public void displayTotalResult(PurchaseResult result) {
        System.out.println(PURCHASE_RESULT_TITLE);
        System.out.printf(OUTPUT_TOTAL_COST, TOTAL_COST, result.getTotalCount(), result.getTotalCost());
        System.out.printf(OUTPUT_TOTAL_PROMOTION_DISCOUNT, PROMOTION_DISCOUNT, result.getPromotionDiscount());
        System.out.printf(OUTPUT_TOTAL_MEMBERSHIP_DISCOUNT, MEMBERSHIP_DISCOUNT, result.getMembershipDiscount());
        System.out.printf(OUTPUT_FINAL_COST, FINAL_COST, result.getFinalCost());
    }
}
