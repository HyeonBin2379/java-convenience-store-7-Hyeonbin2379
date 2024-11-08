package store;

import java.io.IOException;
import store.config.PurchaseConfig;
import store.controller.PurchaseController;

public class Application {

    private static final PurchaseController PURCHASE_CONTROLLER = PurchaseConfig.getPurchaseController();

    public static void main(String[] args) throws IOException {
        PURCHASE_CONTROLLER.start();
    }
}