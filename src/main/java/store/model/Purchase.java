package store.model;

import static store.util.validator.FormatValidator.validatePositive;
import static store.util.validator.PurchaseValidator.validatePurchaseItem;

import java.util.List;

public class Purchase {

    private final Product product;
    private final Promotion promotion;
    private int purchaseCount;
    private int giveawayCount;
    private int buyCount;

    public Purchase(List<String> params) {
        validatePurchaseItem(params.getFirst(), Integer.parseInt(params.getLast()));
        this.product = Product.findProduct(params.getFirst());
        this.promotion = product.getPromotion();
        this.purchaseCount = validatePositive(params.getLast());
    }

    public boolean isPromotionAvailable() {
        return promotion.isAvailable();
    }

    public String getName() {
        return product.getName();
    }

    public int getTotalCost() {
        return product.getPrice(purchaseCount);
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int newCount) {
        purchaseCount = newCount;
    }

    public int getPromotionDiscount() {
        return product.getPrice(giveawayCount);
    }

    public int getGiveawayCount() {
        return giveawayCount;
    }

    public void addGiveawayCount() {
        giveawayCount += promotion.getGiveawayCount();
    }

    public int getBuyCost() {
        return product.getPrice(buyCount);
    }

    public void addBuyCount() {
        buyCount += promotion.getBuyCount();
    }

    public void addBuyCount(int count) {
        buyCount += count;
    }
}
