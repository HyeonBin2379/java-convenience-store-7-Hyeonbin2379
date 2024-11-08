package store.config;

import static store.util.message.ExceptionMessage.OTHER_INPUT_ERROR;

import java.io.IOException;
import store.dao.InventoryDao;
import store.view.InputView;
import store.view.OutputView;

public final class AppConfig {

    private static InputView inputView = getInputView();
    private static OutputView outputView = getOutputView();

    private static InventoryDao inventoryDao = getInventoryDao();

    private AppConfig() {
    }

    public static InputView getInputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    public static OutputView getOutputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }

    public static InventoryDao getInventoryDao() {
        try {
            if (inventoryDao == null) {
                inventoryDao = new InventoryDao();
            }
            return inventoryDao;
        } catch (IOException e) {
            throw new IllegalArgumentException(OTHER_INPUT_ERROR.getMessage());
        }
    }
}
