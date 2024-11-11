package store.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dao.InventoryDao;
import store.view.InputView;
import store.view.OutputView;

class AppConfigTest {

    @Test
    @DisplayName("생성된 InputView 객체는 null이 아닌 싱글톤 객체이다.")
    void getInputViewTest() {
        InputView sample1 = AppConfig.getInputView();
        assertNotNull(sample1);

        InputView sample2 = AppConfig.getInputView();
        assertSame(sample1, sample2);
    }

    @Test
    @DisplayName("생성된 OutputView 객체는 싱글톤 객체이다.")
    void getOutputViewTest() {
        OutputView sample1 = AppConfig.getOutputView();
        assertNotNull(sample1);

        OutputView sample2 = AppConfig.getOutputView();
        assertSame(sample1, sample2);
    }

    @Test
    @DisplayName("생성된 InventoryDao 객체는 싱글톤 객체이다.")
    void getInventoryDaoTest() {
        InventoryDao sample1 = AppConfig.getInventoryDao();
        assertNotNull(sample1);

        InventoryDao sample2 = AppConfig.getInventoryDao();
        assertSame(sample1, sample2);
    }
}