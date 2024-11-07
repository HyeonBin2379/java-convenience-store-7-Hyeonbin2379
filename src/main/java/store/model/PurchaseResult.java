package store.model;

import java.util.List;

public class PurchaseResult {

    private int totalCost;
    private int promotionDiscount;
    private int membershipDiscount;

    public PurchaseResult(List<Purchase> purchases) {
        setTotalCost(purchases);
        setPromotionDiscount(purchases);
    }

    public void setTotalCost(List<Purchase> purchases) {
        totalCost = purchases.stream().map(Purchase::getTotalCost).reduce(0, Integer::sum);
    }

    public void setPromotionDiscount(List<Purchase> purchases) {
        promotionDiscount = purchases.stream().map(Purchase::getPromotionDiscount).reduce(0, Integer::sum);
    }

    public void setMembershipDiscount(List<Purchase> purchases) {
        int totalBuyCost = purchases.stream().map(Purchase::getBuyCost).reduce(0, Integer::sum);
        membershipDiscount = (int) Math.min(totalBuyCost*0.3, 8000);
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalCost() {
        return totalCost - promotionDiscount - membershipDiscount;
    }
}
