package store.model;

import static store.util.constants.StringConstants.NO_PROMOTION;

import java.util.List;

public class Inventory {

    private final int id;
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Inventory(int id, List<String> params) {
        this.id = id;
        this.name = params.getFirst();
        this.price = Integer.parseInt(params.get(1));
        this.quantity = Integer.parseInt(params.get(2));
        this.promotion = Promotion.findPromotion(params.get(3));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        if (promotion == Promotion.NONE){
            return NO_PROMOTION;
        }
        return promotion.getName();
    }

    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }
}
