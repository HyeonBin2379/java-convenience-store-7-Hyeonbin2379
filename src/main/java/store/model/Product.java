package store.model;

import java.util.Arrays;

public enum Product {

    COKE_PROMOTION("콜라", 1000, "탄산2+1"),
    COKE("콜라", 1000, "null"),
    CIDER_PROMOTION("사이다", 1000, "탄산2+1"),
    CIDER("사이다", 1000, "null"),
    ORANGE_JUICE_PROMOTION("오렌지주스", 1800, "MD추천상품"),
    ORANGE_JUICE("오렌지주스", 1800, "null"),
    SODA_PROMOTION("탄산수", 1200, "탄산2+1"),
    SODA("탄산수", 1200, "null"),
    WATER_PROMOTION("물", 500, ""),
    WATER("물", 500, "null"),
    VITAMIN_WATER("비타민워터", 1500, "null"),

    POTATO_CHIP_PROMOTION("감자칩", 1500, "반짝할인"),
    POTATO_CHIP("감자칩", 1500, "null"),
    CHOCO_BAR_PROMOTION("초코바", 1200, "MD추천상품"),
    CHOCO_BAR("초코바", 1200, "null"),
    ENERGY_BAR("에너지바", 2000, "null"),

    LUNCHBOX("정식도시락", 6400, "null"),
    CUP_RAMEN_PROMOTION("컵라면", 1700, "MD추천상품"),
    CUP_RAMEN("컵라면", 1700, "null"),

    NONE("", 0, "null");

    private final String name;
    private final int price;
    private final Promotion promotion;

    Product(String name, int price, String promotionName) {
        this.name = name;
        this.price = price;
        this.promotion = Promotion.findPromotion(promotionName);
    }

    public String getName() {
        return name;
    }

    public int getPrice(int count) {
        return price * count;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public static Product findProduct(String name) {
        return Arrays.stream(Product.values())
                .filter(product -> product.isMatched(name))
                .findFirst()
                .orElse(NONE);
    }
    private boolean isMatched(String name) {
        return this.name.equals(name);
    }
}
