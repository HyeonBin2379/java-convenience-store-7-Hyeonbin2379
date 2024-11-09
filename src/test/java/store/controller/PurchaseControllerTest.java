package store.controller;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static store.util.constants.StringConstants.NO;
import static store.util.constants.StringConstants.YES;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.Application;

class PurchaseControllerTest extends NsTest {

    @Test
    @DisplayName("프로모션 적용 수량보다 적게 구매할 때 프로모션 할인을 적용하면 프로모션 적용 수량만큼 구매한 비용을 지불한다.")
    void discountByPromotionIfNeedCountLess() {
        assertSimpleTest(() -> {
            run("[오렌지주스-1]", YES, "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈1,800");
        });
    }

    @Test
    @DisplayName("프로모션 재고가 구매 수량보다 적을 때, 일부 수량을 정가로 결제하면 무료 증정은 프로모션 재고에만 적용한다.")
    void testPurchaseIfPromotionNotEnough() {
        assertSimpleTest(() -> {
            run("[사이다-10]", YES, "N", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .containsSubsequence("증정", "사이다2" , "행사할인-2,000", "내실돈8,000");
        });
    }

    @Test
    @DisplayName("프로모션 재고가 구매 수량보다 적을 때, 일부 수량을 정가로 결제하지 않으면 프로모션이 적용된 수량만큼만 구매한다.")
    void onlyPurchaseInPromotion() {
        assertSimpleTest(() -> {
            run("[컵라면-3]", NO, "N", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .containsSubsequence("행사할인-0", "내실돈0");
        });
    }

    @ParameterizedTest
    @DisplayName("멤버십 할인 금액은 일반 재고에서 구매한 금액의 30%이며, 최대 8,000원까지 할인할 수 있다.")
    @MethodSource("providePurchaseAndExpects")
    void testDiscountByMembership(String purchase, String expectedDiscount, String expectedFinalCost) {
        assertSimpleTest(() -> {
            run(purchase, YES, "N");
            assertThat(output().replaceAll("\\s", ""))
                    .containsSubsequence(expectedDiscount, expectedFinalCost);
        });
    }

    private static Stream<Arguments> providePurchaseAndExpects() {
        return Stream.of(
                Arguments.of("[에너지바-5]", "멤버십할인-3,000", "내실돈7,000"),
                Arguments.of("[정식도시락-5]", "멤버십할인-8,000", "내실돈24,000")
        );
    }

    @Override
    protected void runMain() {
        try {
            Application.main(new String[]{});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}