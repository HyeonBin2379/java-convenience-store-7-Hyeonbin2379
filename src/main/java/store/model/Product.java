package store.model;

import java.util.Arrays;

public enum Product {

    COKE("콜라", 1000),
    CIDER("사이다", 1000),
    ORANGE_JUICE("오렌지주스", 1800),
    SODA("탄산수", 1200),
    WATER("물", 500),
    VITAMIN_WATER("비타민워터", 1500),
    POTATO_CHIP("감자칩", 1500),
    CHOCO_BAR("초코바", 1200),
    ENERGY_BAR("에너지바", 2000),
    LUNCHBOX("정식도시락", 6400),
    CUP_RAMEN("컵라면", 1700),
    NONE("", 0);

    private final String name;
    private final int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice(int count) {
        return price * count;
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
