package store.util.validator;

import static store.util.message.ExceptionMessage.INPUT_FORMAT_INCORRECT;
import static store.util.message.ExceptionMessage.OTHER_INPUT_ERROR;

import java.util.Arrays;
import java.util.List;

public class FormatValidator {

    public static List<String> validateInputString(String input) {
        validateBlank(input);
        validateDelimiter(input);
        return Arrays.stream(input.split(",")).toList();
    }

    public static void validateBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(OTHER_INPUT_ERROR.getMessage());
        }
    }

    public static void validateDelimiter(String input) {
        if (input.startsWith(",") || input.endsWith(",") || input.contains(",,")) {
            throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
        }
    }

    public static List<String> validatePurchaseFormat(String purchaseInput) {
        if (!purchaseInput.matches("^\\[([^0-9]+)-([1-9][0-9]*)]$")) {
            throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
        }
        return Arrays.stream(purchaseInput.split("[\\[\\]\\-]")).filter(s -> !s.isEmpty()).toList();
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
        if (option.equalsIgnoreCase("Y")) {
            return true;
        } else if (option.equalsIgnoreCase("N")) {
            return false;
        }
        throw new IllegalArgumentException(INPUT_FORMAT_INCORRECT.getMessage());
    }
}
