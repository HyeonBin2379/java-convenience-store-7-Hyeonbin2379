package store.controller;

import static store.util.message.InputMessage.MEMBERSHIP_DISCOUNT;
import static store.util.message.InputMessage.RETRY_PURCHASE;

import java.util.List;
import store.config.AppConfig;
import store.config.DiscountConfig;
import store.model.Inventory;
import store.model.ItemStock;
import store.model.Purchase;
import store.model.Purchases;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseController {

    private final InputView inputView = AppConfig.getInputView();
    private final OutputView outputView = AppConfig.getOutputView();

    private final DiscountController discountController = DiscountConfig.getDiscountController();

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public void start() {
        do {
            openAllInventory();
            Purchases result = purchase();
            outputView.displayReceipt(calculateCost(result));
        } while(inputView.retryYesOrNo(RETRY_PURCHASE.toString()));
    }

    public void openAllInventory() {
        List<Inventory> allInventory = purchaseService.getAllInventories();
        outputView.displayInventoryInfo(allInventory);
    }

    public Purchases purchase() {
        Purchases beforePromotion = inputView.retryItemInput();
        Purchases afterPromotion = discountByPromotion(beforePromotion);
        return discountByMembership(afterPromotion);
    }

    public Purchases discountByPromotion(Purchases purchases) {
        for (int index = 0; index < purchases.getSize(); index++) {
            ItemStock stock = purchaseService.getItemStockInfo(purchases.get(index));
            Purchase result = discountController.discount(purchases.get(index), stock);
            purchases.updatePurchase(index, result);
        }
        return purchases;
    }

    public Purchases discountByMembership(Purchases purchases) {
        if (inputView.retryYesOrNo(MEMBERSHIP_DISCOUNT.toString())) {
            purchases.calculateMembershipDiscount();
        }
        return purchases;
    }

    public Purchases calculateCost(Purchases purchases) {
        purchases.calculateTotalCost();
        purchases.countTotalPurchase();
        purchases.calculatePromotionDiscount();
        return purchases;
    }
}
