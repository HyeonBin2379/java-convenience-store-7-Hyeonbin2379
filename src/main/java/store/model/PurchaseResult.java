package store.model;

public class PurchaseResult {

    private final int totalPurchaseCount;
    private final int totalCost;
    private final int promotionDiscount;
    private final int membershipDiscount;

    public PurchaseResult(int totalPurchaseCount, int totalCost, int promotionDiscount, int membershipDiscount) {
        this.totalPurchaseCount = totalPurchaseCount;
        this.totalCost = totalCost;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
    }

    public int getTotalCount() {
        return totalPurchaseCount;
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
        return getTotalCost() - getPromotionDiscount() - getMembershipDiscount();
    }
}
