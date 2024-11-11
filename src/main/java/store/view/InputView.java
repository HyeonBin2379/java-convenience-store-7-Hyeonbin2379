package store.view;

import static store.util.message.InputMessage.SELECT_ITEM;
import static store.util.validator.FormatValidator.validateInputString;
import static store.util.validator.FormatValidator.validateOption;
import static store.util.validator.FormatValidator.validatePurchaseFormat;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.model.Purchase;
import store.model.Purchases;

public class InputView extends AbstractInputView {

    public List<Purchase> inputPurchasedItem() {
        System.out.println(SELECT_ITEM);
        List<String> input = validateInputString(Console.readLine());
        return input.stream()
                .map(purchaseUnit -> validatePurchaseFormat(purchaseUnit.strip()))
                .map(Purchase::new)
                .toList();
    }

    public boolean inputYesOrNo(String message) {
        System.out.println(message);
        String input = Console.readLine();
        return validateOption(input);
    }

    public Purchases retryItemInput() {
        while (true) {
            try {
                List<Purchase> purchases = retryInput(this::inputPurchasedItem);
                return new Purchases(purchases);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean retryYesOrNo(String message) {
        while (true) {
            try {
                return retryInput(() -> this.inputYesOrNo(message));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
