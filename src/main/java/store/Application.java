package store;

import store.config.PurchaseConfig;
import store.controller.PurchaseController;

public class Application {

    public static void main(String[] args) {
        PurchaseController PURCHASE_CONTROLLER = PurchaseConfig.getPurchaseController();
        PURCHASE_CONTROLLER.start();
    }
}