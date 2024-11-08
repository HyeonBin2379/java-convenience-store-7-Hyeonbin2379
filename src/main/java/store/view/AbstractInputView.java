package store.view;

import java.util.function.Supplier;

public abstract class AbstractInputView {

    public final <T> T retryInput(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
