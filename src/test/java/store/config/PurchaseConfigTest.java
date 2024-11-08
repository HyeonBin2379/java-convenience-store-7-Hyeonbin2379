package store.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.PurchaseController;
import store.service.PurchaseService;

class PurchaseConfigTest {

    @Test
    @DisplayName("생성한 PurchaseService 객체는 싱글톤 객체이다.")
    void getPurchaseServiceTest() {
        PurchaseService sample1 = PurchaseConfig.getPurchaseService();
        assertNotNull(sample1);

        PurchaseService sample2 = PurchaseConfig.getPurchaseService();
        assertSame(sample1, sample2);
    }

    @Test
    @DisplayName("생성한 PurchaseController 객체는 싱글톤 객체이다.")
    void getPurchaseControllerTest() {
        PurchaseController sample1 = PurchaseConfig.getPurchaseController();
        assertNotNull(sample1);

        PurchaseController sample2 = PurchaseConfig.getPurchaseController();
        assertSame(sample1, sample2);
    }
}