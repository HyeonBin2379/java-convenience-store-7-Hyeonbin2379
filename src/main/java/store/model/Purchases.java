package store.model;

import java.util.ArrayList;
import java.util.List;

public class Purchases {

    private final List<Purchase> purchases;

    private int totalPurchaseCount;
    private int totalCost;
    private int totalBuyCost;
    private int promotionDiscount;
    private int membershipDiscount;

    public Purchases(List<Purchase> purchases) {
        this.purchases = new ArrayList<>(purchases);
    }

    public Purchase get(int index) {
        return purchases.get(index);
    }

    public List<Purchase> getAll() {
        return purchases;
    }

    public void updatePurchase(int index, Purchase purchase) {
        purchases.set(index, purchase);
    }

    public int getSize() {
        return purchases.size();
    }

    public void countTotalPurchase() {
        totalPurchaseCount = purchases.stream()
                .map(Purchase::getTotalNeedCount)
                .reduce(0, Integer::sum);
    }

    public void calculateTotalCost() {
        totalCost = purchases.stream()
                .map(Purchase::getTotalCost)
                .reduce(0, Integer::sum);
    }

    public void calculatePromotionDiscount() {
        promotionDiscount = purchases.stream()
                .map(Purchase::getPromotionDiscount)
                .reduce(0, Integer::sum);
    }

    public void calculateTotalBuyCost() {
        totalBuyCost = purchases.stream()
                .map(Purchase::getNonPromotionBuyCost)
                .reduce(0, Integer::sum);
    }

    public void calculateMembershipDiscount() {
        membershipDiscount = (int) Math.min(totalBuyCost*0.3, 8000);
    }

    public PurchaseResult getResult() {
        return new PurchaseResult(totalPurchaseCount, totalCost, promotionDiscount, membershipDiscount);
    }
}
