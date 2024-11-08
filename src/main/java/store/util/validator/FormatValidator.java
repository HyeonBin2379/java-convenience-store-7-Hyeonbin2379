package store.util.validator;

import static store.util.constants.StringConstants.CONSECUTIVE_DELIMITER;
import static store.util.constants.StringConstants.NO;
import static store.util.constants.StringConstants.DELIMITER;
import static store.util.constants.StringConstants.PURCHASE_INFO_DELIMITER;
import static store.util.constants.StringConstants.PURCHASE_INFO_FORMAT;
import static store.util.constants.StringConstants.YES;
import static store.util.message.ExceptionMessage.INPUT_FORMAT_INCORRECT;
import static store.util.message.ExceptionMessage.OTHER_INPUT_ERROR;

import java.util.Arrays;
import java.util.List;

public class FormatValidator {

    public static List<String> validateInputString(String input) {
        validateBlank(input);
        validateDelimiter(input);
        return Arrays.stream(input.split(DELIMITER)).toList();
    }

    public static void validateBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(OTHER_INPUT_ERROR.getMessage());
        }
    }

    public static void validateDelimiter(String input) {
        if (input.startsWith(DELIMITER)
                || input.endsWith(DELIMITER)
                || input.contains(CONSECUTIVE_DELIMITER)) {
            throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
        }
    }

    public static List<String> validatePurchaseFormat(String purchaseInput) {
        if (purchaseInput.contains("--") || !purchaseInput.matches(PURCHASE_INFO_FORMAT)) {
            throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
        }
        return Arrays.stream(purchaseInput.split(PURCHASE_INFO_DELIMITER))
                .filter(s -> !s.isEmpty())
                .toList();
    }

    public static boolean validateOption(String option) {
        validateBlank(option);
        if (option.equalsIgnoreCase(YES)) {
            return true;
        } else if (option.equalsIgnoreCase(NO)) {
            return false;
        }
        throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
    }
}
