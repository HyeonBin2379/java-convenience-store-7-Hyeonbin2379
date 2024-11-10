package store.model;

import static store.util.constants.StringConstants.NO_PROMOTION;
import static store.util.constants.StringConstants.OUT_OF_STOCK;
import static store.util.message.OutputMessage.ITEM_COUNT;
import static store.util.message.OutputMessage.ITEM_INFO;

import java.util.List;

public class Inventory {

    private final String name;
    private final int price;
    private final Promotion promotion;

    private int quantity;

    public Inventory(List<String> params) {
        this.name = params.getFirst();
        this.price = Integer.parseInt(params.get(1));
        this.quantity = Integer.parseInt(params.get(2));
        this.promotion = Promotion.findPromotion(params.get(3).replace("null", NO_PROMOTION));
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromo() {
        return promotion;
    }

    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    @Override
    public String toString() {
        return String.format(ITEM_INFO, name, price, getNewValue(quantity), getPromoName());
    }

    private String getNewValue(Integer productCount) {
        if (productCount == 0) {
            return OUT_OF_STOCK;
        }
        return String.format(ITEM_COUNT, productCount);
    }

    private String getPromoName() {
        return promotion.getName();
    }
}
