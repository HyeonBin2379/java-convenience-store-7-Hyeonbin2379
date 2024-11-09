package store.model;

import static store.util.validator.PurchaseValidator.validatePurchaseItem;

import java.util.List;

public class Purchase {

    private final Product product;
    private int freeItemCount;
    private int totalNeedCount;
    private int nonPromotionCount;

    public Purchase(List<String> params) {
        validatePurchaseItem(params.getFirst(), Integer.parseInt(params.getLast()));
        this.product = Product.findProduct(params.getFirst());
        this.totalNeedCount = Integer.parseInt(params.getLast());
    }

    public String getName() {
        return product.getName();
    }

    public int getFreeItemCount() {
        return freeItemCount;
    }

    public int getTotalNeedCount() {
        return totalNeedCount;
    }

    public void setTotalNeedCount(int newTotalCount) {
        totalNeedCount = newTotalCount;
    }


    public int calculateCostBeforeDiscount() {
        return product.calculateCost(totalNeedCount);
    }

    public int calculatePromotionDiscount() {
        return product.calculateCost(freeItemCount);
    }

    public int calculateNonPromotionBuyCost() {
        return product.calculateCost(nonPromotionCount);
    }

    public void addNonPromotionCount(int count) {
        nonPromotionCount += count;
    }

    public void addFreeItemCount(int count) {
        freeItemCount += count;
    }
}
