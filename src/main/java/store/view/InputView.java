package store.view;

import static store.util.message.InputMessage.SELECT_ITEM;
import static store.util.validator.FormatValidator.validateInputString;
import static store.util.validator.FormatValidator.validateOption;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.model.Purchase;
import store.util.validator.FormatValidator;

public class InputView {

    public List<Purchase> inputPurchasedItem() {
        System.out.println(SELECT_ITEM);
        List<String> input = validateInputString(Console.readLine());
        return input.stream()
                .map(FormatValidator::validatePurchaseFormat)
                .map(Purchase::new)
                .toList();
    }

    public boolean inputYesOrNo(String message) {
        System.out.println(message);
        String input = Console.readLine();
        return validateOption(input);
    }
}
