package store.model;

public class PurchaseResult {

    private final int totalCount;
    private final int totalCost;
    private final int promotionDiscount;
    private final int membershipDiscount;

    public PurchaseResult(Integer totalCount, Integer totalCost, Integer promotionDiscount, Integer membershipDiscount) {
        this.totalCount = totalCount;
        this.totalCost = totalCost;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public Integer getPromotionDiscount() {
        return promotionDiscount;
    }

    public Integer getMembershipDiscount() {
        return membershipDiscount;
    }

    public Integer getFinalCost() {
        return getTotalCost() - getPromotionDiscount() - getMembershipDiscount();
    }
}
