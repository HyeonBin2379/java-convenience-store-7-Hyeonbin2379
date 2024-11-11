package store.model;

public class ItemStock {

    private final Promotion promotion;
    private final int promotionQuantity;
    private final int normalQuantity;

    public ItemStock(Promotion promotion, Integer promotionQuantity, Integer normalQuantity) {
        this.promotion = promotion;
        this.promotionQuantity = promotionQuantity;
        this.normalQuantity = normalQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Integer getPromotionQuantity() {
        return promotionQuantity;
    }

    public Integer getNormalQuantity() {
        return normalQuantity;
    }

    public Integer getPurchasedPromotionCount() {
        return promotion.getBundleCount() * (promotionQuantity / promotion.getBundleCount());
    }
}
