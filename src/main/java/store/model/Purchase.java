package store.model;

import static store.util.validator.PurchaseValidator.validatePurchaseItem;

import java.util.List;

public class Purchase {

    private final Product product;

    private int totalNeedCount;
    private int freeCount;
    private int normalCount;

    public Purchase(List<String> params) {
        validatePurchaseItem(params.getFirst(), Integer.parseInt(params.getLast()));
        this.product = Product.findProduct(params.getFirst());
        this.totalNeedCount = Integer.parseInt(params.getLast());
    }

    public Integer calculateCostBeforeDiscount() {
        return product.calculateCost(totalNeedCount);
    }

    public Integer calculatePromotionDiscount() {
        return product.calculateCost(freeCount);
    }

    public Integer calculateNormalBuyCost() {
        return product.calculateCost(normalCount);
    }

    public void addNormalCount(Integer count) {
        normalCount += count;
    }

    public void addFreeItemCount(Integer count) {
        freeCount += count;
    }

    public String getName() {
        return product.getName();
    }

    public Integer getFreeCount() {
        return freeCount;
    }

    public Integer getNeedCount() {
        return totalNeedCount;
    }

    public void setNeedCount(Integer newTotalCount) {
        totalNeedCount = newTotalCount;
    }
}
