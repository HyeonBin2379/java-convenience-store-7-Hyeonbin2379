package store.controller;

import static store.util.message.InputMessage.MEMBERSHIP_DISCOUNT;
import static store.util.message.InputMessage.PROMOTION_AVAILABLE;
import static store.util.message.InputMessage.PROMOTION_NOT_AVAILABLE;
import static store.util.message.InputMessage.RETRY_PURCHASE;

import java.util.List;
import store.model.Inventory;
import store.model.ItemStock;
import store.model.Promotion;
import store.model.Purchase;
import store.model.Purchases;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseController {

    private final InputView inputView;
    private final OutputView outputView;

    private final PurchaseService purchaseService;

    public PurchaseController(InputView inputView, PurchaseService purchaseService, OutputView outputView) {
        this.inputView = inputView;
        this.purchaseService = purchaseService;
        this.outputView = outputView;
    }

    public void openAllInventory() {
        List<Inventory> allInventory = purchaseService.getAllInventories();
        outputView.displayInventories(allInventory);
    }

    public void purchase() {
        Purchases beforePromotion = inputView.retryItemInput();
        Purchases afterPromotion = discountByPromotion(beforePromotion);
        Purchases result = discountByMembership(afterPromotion);
        outputView.displayReceipt(result);
    }

    public Purchases discountByPromotion(Purchases purchases) {
        for (int i = 0; i < purchases.getSize(); i++) {
            ItemStock stock = purchaseService.getItemStockInfo(purchases.get(i));
            Purchase result = discountByPromotion(purchases.get(i), stock);
            purchases.updatePurchase(i, result);
        }
        purchases.calculateTotalCost();
        purchases.countTotalPurchase();
        purchases.calculatePromotionDiscount();
        return purchases;
    }

    public Purchase discountByPromotion(Purchase purchase, ItemStock stock) {
        Promotion promotion = stock.getPromotion();
        if (promotion == Promotion.NONE || !promotion.isInProgress()) {
            return purchaseService.updateNormalQuantityNoPromotion(purchase);
        }
        return discountByAvailablePromotion(purchase, stock);
    }

    public Purchase discountByAvailablePromotion(Purchase purchase, ItemStock stock) {
        Promotion promotion = stock.getPromotion();
        int needCount = purchase.getTotalNeedCount();
        int promotionQuantity = stock.getPromotionQuantity();

        if (needCount <= promotionQuantity) {   // 일반적인 프로모션 할인 적용
            if (needCount < promotion.getPromotionCount()) {
                return discountByLessThanPromotionCount(purchase, promotion, stock);   // 프로모션보다 적게 구매하는 경우 & 정가 구매
            }
            return purchaseService.updateByPromotionDiscount(purchase, promotion, stock);
        }
        return discountByNotEnoughPromotion(purchase, promotion, stock);
    }

    public Purchase discountByLessThanPromotionCount(Purchase purchase, Promotion promotion, ItemStock stock) {
        if (inputView.retryYesOrNo(makeString(purchase, promotion))) {
            return purchaseService.updateByLessThanPromotionCount(purchase, promotion, stock);
        }
        return purchaseService.updateByOriginalNeedCount(purchase, promotion, stock);
    }

    private String makeString(Purchase purchase, Promotion promotion) {
        return String.format(PROMOTION_AVAILABLE, purchase.getName(), promotion.getGiveawayCount());
    }

    public Purchase discountByNotEnoughPromotion(Purchase purchase, Promotion promotion, ItemStock stock) {
        int promotionQuantity = stock.getPromotionQuantity();
        int promotionCount = promotion.getPromotionCount();
        int remainder = purchase.getTotalNeedCount() - promotionCount*(promotionQuantity/promotionCount);

        if (inputView.retryYesOrNo(makeString(purchase, remainder))) {   // 프로모션 할인 & 프로모션 재고 부족
            return purchaseService.updateNotEnoughPromoQuantity(purchase, promotion, stock);
        }
        return purchaseService.updateOnlyPromotionQuantity(purchase, promotion,stock);
    }

    private String makeString(Purchase purchase, int remainder) {
        return String.format(PROMOTION_NOT_AVAILABLE, purchase.getName(), remainder);
    }


    public Purchases discountByMembership(Purchases purchases) {
        if (inputView.retryYesOrNo(MEMBERSHIP_DISCOUNT)) {
            purchases.calculateTotalBuyCost();
            purchases.calculateMembershipDiscount();
        }
        return purchases;
    }

    public void start() {
        do {
            openAllInventory();
            purchase();
        } while(inputView.retryYesOrNo(RETRY_PURCHASE));
    }
}
