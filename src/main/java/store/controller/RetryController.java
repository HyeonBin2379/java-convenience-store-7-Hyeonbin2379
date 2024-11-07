package store.controller;

import java.util.List;
import java.util.function.Supplier;
import store.model.Purchase;
import store.model.Purchases;
import store.view.InputView;

public class RetryController {

    private final InputView inputView;

    public RetryController(InputView inputView) {
        this.inputView = inputView;
    }

    public <T> T retryInput(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Purchases retryPurchaseItemInput() {
        while (true) {
            try {
                List<Purchase> purchases = retryInput(inputView::inputPurchasedItem);
                return new Purchases(purchases);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean retryOptionInput(String message) {
        while (true) {
            try {
                return retryInput(() -> inputView.inputYesOrNo(message));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
