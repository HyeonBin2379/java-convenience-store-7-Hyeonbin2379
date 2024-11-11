package store.config;

import store.controller.PurchaseController;
import store.service.PurchaseService;

public final class PurchaseConfig {

    private static PurchaseService purchaseService = getPurchaseService();
    private static PurchaseController purchaseController = getPurchaseController();

    public static PurchaseService getPurchaseService() {
        if (purchaseService == null) {
            purchaseService = new PurchaseService(AppConfig.getInventoryDao());
        }
        return purchaseService;
    }

    public static PurchaseController getPurchaseController() {
        if (purchaseController == null) {
            purchaseController = new PurchaseController(getPurchaseService());
        }
        return purchaseController;
    }
}
