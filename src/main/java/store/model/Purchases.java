package store.model;

import java.util.List;

public class Purchases {

    private final List<Purchase> purchases;
    private final PurchaseResult result;

    public Purchases(List<Purchase> input) {
        this.purchases = input;
        this.result = new PurchaseResult(input);
    }

    public List<Purchase> getAll() {
        return purchases;
    }

    public int getTotalCount() {
        return purchases.stream().map(Purchase::getPurchaseCount).reduce(0, Integer::sum);
    }

    public int getTotalCost() {
        return result.getTotalCost();
    }

    public int getPromotionDiscount() {
        return result.getPromotionDiscount();
    }

    public void setMembershipDiscount() {
        result.setMembershipDiscount(purchases);
    }

    public int getMembershipDiscount() {
        return result.getMembershipDiscount();
    }

    public int getFinalCost() {
        return result.getFinalCost();
    }
}
