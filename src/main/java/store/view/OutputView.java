package store.view;

import static store.util.message.OutputMessage.OUTPUT_FINAL_COST;
import static store.util.message.OutputMessage.OUTPUT_GIVEAWAY_ITEM;
import static store.util.message.OutputMessage.OUTPUT_GIVEAWAY_TITLE;
import static store.util.message.OutputMessage.OUTPUT_ITEM_INFO;
import static store.util.message.OutputMessage.OUTPUT_PURCHASED_ITEM;
import static store.util.message.OutputMessage.OUTPUT_PURCHASE_RESULT_TITLE;
import static store.util.message.OutputMessage.OUTPUT_RECEIPT_TITLE;
import static store.util.message.OutputMessage.OUTPUT_TOTAL_COST;
import static store.util.message.OutputMessage.OUTPUT_TOTAL_MEMBERSHIP_DISCOUNT;
import static store.util.message.OutputMessage.OUTPUT_TOTAL_PROMOTION_DISCOUNT;
import static store.util.message.OutputMessage.WELCOME_MESSAGE;

import java.util.Map;
import store.model.Inventory;
import store.model.Purchase;
import store.model.Purchases;

public class OutputView {

    public void displayInventories(Map<Integer, Inventory> inventories) {
        System.out.println(WELCOME_MESSAGE);
        inventories.values().forEach(inventory -> System.out.print(makeString(inventory)));
    }
    private String makeString(Inventory inventory) {
        return String.format(
                OUTPUT_ITEM_INFO,
                inventory.getName(),
                inventory.getPrice(),
                getNewValue(inventory.getQuantity()),
                inventory.getPromotion()
        );
    }
    private String getNewValue(Integer productCount) {
        if (productCount == 0) {
            return "재고 없음";
        }
        return String.format("%,d개", productCount);
    }

    public void displayReceipt(Purchases purchases) {
        displayTotalCost(purchases);
        displayGiveaway(purchases);
        displayTotalResult(purchases);
    }

    public void displayTotalCost(Purchases purchases) {
        System.out.println(OUTPUT_RECEIPT_TITLE);
        for (Purchase purchase : purchases.getAll()) {
            System.out.printf(OUTPUT_PURCHASED_ITEM, purchase.getName(), purchase.getPurchaseCount(), purchase.getTotalCost());
        }
    }

    public void displayGiveaway(Purchases purchases) {
        System.out.println(OUTPUT_GIVEAWAY_TITLE);
        for (Purchase purchase : purchases.getAll()) {
            if (purchase.getGiveawayCount() > 0) {
                System.out.printf(OUTPUT_GIVEAWAY_ITEM, purchase.getName(), purchase.getGiveawayCount());
            }
        }
    }

    public void displayTotalResult(Purchases purchases) {
        System.out.println(OUTPUT_PURCHASE_RESULT_TITLE);
        System.out.printf(OUTPUT_TOTAL_COST, purchases.getTotalCount(), purchases.getTotalCost());
        System.out.printf(OUTPUT_TOTAL_PROMOTION_DISCOUNT, purchases.getPromotionDiscount());
        System.out.printf(OUTPUT_TOTAL_MEMBERSHIP_DISCOUNT, purchases.getMembershipDiscount());
        System.out.printf(OUTPUT_FINAL_COST, purchases.getFinalCost());
    }
}
