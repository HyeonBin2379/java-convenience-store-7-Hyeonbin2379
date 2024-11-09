package store.model;

import static store.util.validator.PurchaseValidator.validatePurchaseItem;

import java.util.List;

public class Purchase {

    private final Product product;
    private int freeItemCount;
    private int totalNeedCount;
    private int normalCount;

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

    public int getNeedCount() {
        return totalNeedCount;
    }

    public void setNeedCount(int newTotalCount) {
        totalNeedCount = newTotalCount;
    }


    public int calculateCostBeforeDiscount() {
        return product.calculateCost(totalNeedCount);
    }

    public int calculatePromotionDiscount() {
        return product.calculateCost(freeItemCount);
    }

    public int calculateNormalBuyCost() {
        return product.calculateCost(normalCount);
    }

    public void addNormalCount(int count) {
        normalCount += count;
    }

    public void addFreeItemCount(int count) {
        freeItemCount += count;
    }
}
