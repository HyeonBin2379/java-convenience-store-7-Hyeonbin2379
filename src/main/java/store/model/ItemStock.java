package store.model;

public class ItemStock {

    private final Promotion promotion;
    private final int promotionQuantity;
    private final int normalQuantity;

    public ItemStock(Promotion promotion, int promotionQuantity, int normalQuantity) {
        this.promotion = promotion;
        this.promotionQuantity = promotionQuantity;
        this.normalQuantity = normalQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getNormalQuantity() {
        return normalQuantity;
    }
}
