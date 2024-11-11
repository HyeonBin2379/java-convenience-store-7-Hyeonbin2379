package store.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ProductTest {

    @ParameterizedTest
    @DisplayName("상품 구매 금액은 상품의 가격과 수량을 곱해서 계산한다.")
    @MethodSource("provideItemNameAndPrice")
    void calculateCostTest(Product product, int purchaseCount, int expected) {
        int result = product.calculateCost(purchaseCount);

        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideItemNameAndPrice() {
        return Stream.of(
                Arguments.of(Product.COKE, 2, 2000),
                Arguments.of(Product.POTATO_CHIP, 2, 3000),
                Arguments.of(Product.CHOCO_BAR, 2, 2400),
                Arguments.of(Product.LUNCHBOX, 2, 12800)
        );
    }

    @ParameterizedTest
    @DisplayName("입력한 이름에 대응되는 상품을 찾는다.")
    @MethodSource("provideItemNameAndProduct")
    void findProductTest(String itemName, Product expected) {
        Product found = Product.findProduct(itemName);

        assertEquals(expected, found);
    }

    private static Stream<Arguments> provideItemNameAndProduct() {
        return Stream.of(
                Arguments.of("사이다", Product.CIDER),
                Arguments.of("오렌지주스", Product.ORANGE_JUICE),
                Arguments.of("컵라면", Product.CUP_RAMEN)
        );
    }
}