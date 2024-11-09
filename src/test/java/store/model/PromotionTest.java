package store.model;

import static org.junit.jupiter.api.Assertions.*;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PromotionTest {

    @ParameterizedTest
    @DisplayName("상품에 적용 가능한 프로모션 이름에 해당하는 프로모션 할인을 찾는다.")
    @MethodSource("provideNameAndExpected")
    void findPromotionTest(String promotionName, Promotion expected) {
        Promotion found = Promotion.findPromotion(promotionName);

        assertEquals(expected, found);
    }

    private static Stream<Arguments> provideNameAndExpected() {
        return Stream.of(
                Arguments.of("탄산2+1", Promotion.SODA_PROMOTION),
                Arguments.of("MD추천상품", Promotion.RECOMMENDED),
                Arguments.of("반짝할인", Promotion.SURPRISE)
        );
    }

    @Test
    @DisplayName("상품에 적용 가능한 프로모션 이름이 null이면 적용할 프로모션이 없으므로, 무료로 증정되는 상품도 없다.")
    void testIfPromotionNameNull() {
        String promotionName = "null";
        Promotion found = Promotion.findPromotion(promotionName);

        int expectedFreeItemCount = 0;

        assertEquals(Promotion.NONE, found);
        assertEquals(expectedFreeItemCount, found.getFreeItemCount());
    }

    @ParameterizedTest
    @DisplayName("상품에 적용된 프로모션의 이름이 null이 아니면 상품을 n개 구매하고 1개를 무료로 받을 수 있다.")
    @MethodSource("provideNameAndExpectedCounts")
    void testIfPromotionNameNotNull(String name, int expectedBuyCount, int expectedFreeItemCount) {
        Promotion found = Promotion.findPromotion(name);

        assertEquals(expectedBuyCount, found.getPromotionCount() - found.getFreeItemCount());
        assertEquals(expectedFreeItemCount, found.getFreeItemCount());
    }

    private static Stream<Arguments> provideNameAndExpectedCounts() {
        return Stream.of(
                Arguments.of("탄산2+1", 2, 1),
                Arguments.of("MD추천상품", 1, 1),
                Arguments.of("반짝할인", 1, 1)
        );
    }

    @ParameterizedTest
    @DisplayName("프로모션 할인을 적용하려면 프로모션 이름이 null이 아니면서 오늘 날짜가 프로모션 기간에 포함되어야 한다.")
    @MethodSource("providePromotionAndExpected")
    void isPromotionAvailableTest(String promotionName, boolean isInProgress, boolean expectedResult) {
        Promotion promotion = Promotion.findPromotion(promotionName);

        assertEquals(isInProgress, promotion.isInProgress(DateTimes.now()));
        assertEquals(expectedResult, Promotion.isAvailable(promotion, DateTimes.now()));
    }

    private static Stream<Arguments> providePromotionAndExpected() {
        return Stream.of(
                Arguments.of("탄산2+1", true, true),
                Arguments.of("발렌타인1+1", false, false),
                Arguments.of("null", true, false)
        );
    }
}