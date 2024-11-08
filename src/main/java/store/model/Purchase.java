package store.model;

import static store.util.validator.PurchaseValidator.validatePurchaseItem;

import java.util.List;

public class Purchase {

    private final Product product;
    private int totalNeedCount;
    private int freeItemCount;
    private int buyCountNotPromotionItem;

    public Purchase(List<String> params) {
        validatePurchaseItem(params.getFirst(), Integer.parseInt(params.getLast()));
        this.product = Product.findProduct(params.getFirst());
        this.totalNeedCount = Integer.parseInt(params.getLast());
    }

    public String getName() {
        return product.getName();
    }

    public int getTotalNeedCount() {
        return totalNeedCount;
    }

    public void setTotalNeedCount(int newTotalCount) {
        totalNeedCount = newTotalCount;
    }

    public int getTotalCost() {
        return product.getPrice(totalNeedCount);
    }

    public int getPromotionDiscount() {
        return product.getPrice(freeItemCount);
    }

    public int getFreeItemCount() {
        return freeItemCount;
    }

    public int getNonPromotionBuyCost() {
        return product.getPrice(buyCountNotPromotionItem);
    }

    public void addNonPromotionBuyCount(int count) {
        buyCountNotPromotionItem += count;
    }

    public void addFreeItemCount(int count) {
        freeItemCount += count;
    }
}
