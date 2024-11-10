package store.model;

import static store.util.validator.PurchaseValidator.validateDuplicatedItem;

import java.util.ArrayList;
import java.util.List;

public class Purchases {

    private final List<Purchase> purchases;

    private int totalPurchaseCount;
    private int totalCost;
    private int promotionDiscount;
    private int membershipDiscount;

    public Purchases(List<Purchase> purchases) {
        validateDuplicatedItem(purchases);
        this.purchases = new ArrayList<>(purchases);
    }

    public Purchase get(Integer index) {
        return purchases.get(index);
    }

    public List<Purchase> getAll() {
        return purchases;
    }

    public void updatePurchase(Integer index, Purchase purchase) {
        purchases.set(index, purchase);
    }

    public Integer getSize() {
        return purchases.size();
    }

    public void countTotalPurchase() {
        totalPurchaseCount = purchases.stream()
                .map(Purchase::getNeedCount)
                .reduce(0, Integer::sum);
    }

    public void calculateTotalCost() {
        totalCost = purchases.stream()
                .map(Purchase::calculateCostBeforeDiscount)
                .reduce(0, Integer::sum);
    }

    public void calculatePromotionDiscount() {
        promotionDiscount = purchases.stream()
                .map(Purchase::calculatePromotionDiscount)
                .reduce(0, Integer::sum);
    }

    public void calculateMembershipDiscount() {
        int totalBuyCost = purchases.stream()
                .map(Purchase::calculateNormalBuyCost)
                .reduce(0, Integer::sum);
        membershipDiscount = Math.min((int)(totalBuyCost*0.3), 8000);
    }

    public PurchaseResult getResult() {
        return new PurchaseResult(totalPurchaseCount, totalCost, promotionDiscount, membershipDiscount);
    }
}
