package store.util.validator;

import static store.util.constants.StringConstants.CONSECUTIVE_DELIMITER;
import static store.util.constants.StringConstants.NO;
import static store.util.constants.StringConstants.PURCHASE_DELIMITER;
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
        return Arrays.stream(input.split(PURCHASE_DELIMITER)).toList();
    }

    public static void validateBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(OTHER_INPUT_ERROR.getMessage());
        }
    }

    public static void validateDelimiter(String input) {
        if (input.startsWith(PURCHASE_DELIMITER)
                || input.endsWith(PURCHASE_DELIMITER)
                || input.contains(CONSECUTIVE_DELIMITER)) {
            throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
        }
    }

    public static List<String> validatePurchaseFormat(String purchaseInput) {
        if (!purchaseInput.matches(PURCHASE_INFO_FORMAT)) {
            throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
        }
        return Arrays.stream(purchaseInput.split(PURCHASE_INFO_DELIMITER)).filter(s -> !s.isEmpty()).toList();
    }

    public static int validatePositive(String input) {
        int quantity = validateInteger(input);
        if (quantity <= 0) {
            throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
        }
        return quantity;
    }

    private static int validateInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
        }
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
