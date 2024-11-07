package store.config;

import static store.util.message.ExceptionMessage.OTHER_INPUT_ERROR;

import java.io.IOException;
import store.controller.FileController;
import store.controller.RetryController;
import store.dao.InventoryDao;
import store.view.InputView;
import store.view.OutputView;

public final class AppConfig {

    public static InputView inputView = getInputView();
    public static OutputView outputView = getOutputView();

    public static RetryController retryController = getRetryController();
    public static FileController fileController = getFileController();
    public static InventoryDao inventoryDao = getInventoryDao();

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
                inventoryDao = getFileController().loadInventoryData();
            }
            return inventoryDao;
        } catch (IOException e) {
            throw new IllegalArgumentException(OTHER_INPUT_ERROR.getMessage());
        }
    }

    public static RetryController getRetryController() {
        if (retryController == null) {
            retryController = new RetryController(getInputView());
        }
        return retryController;
    }

    private static FileController getFileController() {
        if (fileController == null) {
            fileController = new FileController();
        }
        return fileController;
    }
}
