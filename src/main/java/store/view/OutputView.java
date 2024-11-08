package store.view;

import static store.util.message.OutputMessage.OUTPUT_FINAL_COST;
import static store.util.message.OutputMessage.OUTPUT_GIVEAWAY_ITEM;
import static store.util.message.OutputMessage.OUTPUT_GIVEAWAY_TITLE;
import static store.util.message.OutputMessage.OUTPUT_PURCHASED_ITEM;
import static store.util.message.OutputMessage.OUTPUT_PURCHASE_RESULT_TITLE;
import static store.util.message.OutputMessage.OUTPUT_RECEIPT_TITLE;
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

    public void displayInventories(List<Inventory> inventories) {
        System.out.println(WELCOME_MESSAGE);
        inventories.forEach(inventory -> System.out.print(inventory.toString()));
    }

    public void displayReceipt(Purchases purchases) {
        displayTotalCost(purchases);
        displayGiveaway(purchases);
        displayTotalResult(purchases.getResult());
    }

    public void displayTotalCost(Purchases purchases) {
        System.out.println(OUTPUT_RECEIPT_TITLE);
        for (Purchase purchase : purchases.getAll()) {
            System.out.printf(OUTPUT_PURCHASED_ITEM, purchase.getName(), purchase.getTotalNeedCount(), purchase.getTotalCost());
        }
    }

    public void displayGiveaway(Purchases purchases) {
        System.out.println(OUTPUT_GIVEAWAY_TITLE);
        for (Purchase purchase : purchases.getAll()) {
            if (purchase.getGetCount() > 0) {
                System.out.printf(OUTPUT_GIVEAWAY_ITEM, purchase.getName(), purchase.getGetCount());
            }
        }
    }

    public void displayTotalResult(PurchaseResult result) {
        System.out.println(OUTPUT_PURCHASE_RESULT_TITLE);
        System.out.printf(OUTPUT_TOTAL_COST, result.getTotalCount(), result.getTotalCost());
        System.out.printf(OUTPUT_TOTAL_PROMOTION_DISCOUNT, result.getPromotionDiscount());
        System.out.printf(OUTPUT_TOTAL_MEMBERSHIP_DISCOUNT, result.getMembershipDiscount());
        System.out.printf(OUTPUT_FINAL_COST, result.getFinalCost());
    }
}
