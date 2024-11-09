package store.util.validator;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static store.util.constants.StringConstants.ERROR_MESSAGE;
import static store.util.message.ExceptionMessage.INPUT_FORMAT_INCORRECT;
import static store.util.validator.FormatValidator.validateBlank;
import static store.util.validator.FormatValidator.validateDelimiter;
import static store.util.validator.FormatValidator.validateOption;
import static store.util.validator.FormatValidator.validatePurchaseFormat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FormatValidatorTest {

    @ParameterizedTest
    @DisplayName("구매할 상품과 수량 혹은 Y/N 입력 시 공백이나 빈 문자열을 입력하면 예외가 발생한다.")
    @ValueSource(strings = {"", "    "})
    void validateBlankExceptionTest(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> validateBlank(input))
                .withMessageStartingWith(ERROR_MESSAGE);
    }

    @ParameterizedTest
    @DisplayName("구매할 상품과 수량 입력 시 개별 상품을 구분하는 쉼표를 잘못 사용하면 예외가 발생한다.")
    @ValueSource(strings = {",[사이다-2],[콜라-1]", "[사이다-2],[콜라-1],", "[사이다-2],,[콜라-1]"})
    void validateDelimiterExceptionTest(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> validateDelimiter(input))
                .withMessageStartingWith(ERROR_MESSAGE);
    }

    @ParameterizedTest
    @DisplayName("구매할 개별 상품의 이름과 수량이 요구사항에서 지정한 형식을 따르지 않으면 예외가 발생한다.")
    @ValueSource(strings = {"[사이다-A]", "사이다-1]", "사이다-1", "[사이다-]", "[사이다1]", "[-1]", "[사이다--1]"})
    void validatePurchaseFormatExceptionTest(String purchaseItem) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> validatePurchaseFormat(purchaseItem))
                .withMessage(INPUT_FORMAT_INCORRECT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("프로모션 할인 또는 멤버십 할인 적용 여부 선택 시 Y, N 이외의 문자를 입력하면 예외가 발생한다.")
    @ValueSource(strings = {"A", "1", "A1", "A1!"})
    void validateOptionExceptionTest(String optionInput) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> validateOption(optionInput))
                .withMessageStartingWith(INPUT_FORMAT_INCORRECT.getMessage());
    }

    @Test
    @DisplayName("Y나 y를 입력하면 true, N이나 n을 입력하면 false를 반환한다.")
    void validateOptionTest() {
        assertTrue(validateOption("Y"));
        assertTrue(validateOption("y"));
        assertFalse(validateOption("N"));
        assertFalse(validateOption("n"));
    }
}