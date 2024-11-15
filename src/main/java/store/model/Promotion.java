package store.model;

import static store.util.constants.FormatString.DATE_FORMAT;
import static store.util.constants.StringConstants.NO_PROMOTION;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public enum Promotion {

    SODA_PROMOTION("탄산2+1", 2, 1, "2024-01-01", "2024-12-31"),
    RECOMMENDED("MD추천상품", 1, 1, "2024-01-01", "2024-12-31"),
    SURPRISE("반짝할인", 1, 1, "2024-11-01", "2024-11-30"),
    VALENTINE_PROMOTION("발렌타인1+1", 1, 1, "2024-02-11", "2024-02-17"),
    NULL(NO_PROMOTION, 1, 0, "1970-01-01", "9999-12-31");

    private final String name;
    private final int bundleCount;
    private final int freeCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    Promotion(String name, Integer buyCount, Integer freeCount, String startDate, String endDate) {
        this.name = name;
        this.bundleCount = buyCount + freeCount;
        this.freeCount = freeCount;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
    }

    public String getName() {
        return name;
    }

    public Integer getFreeCount() {
        return freeCount;
    }

    public Integer getBundleCount() {
        return bundleCount;
    }

    public static boolean isAvailable(Promotion promotion, LocalDateTime now) {
        return promotion != Promotion.NULL && promotion.isInProgress(now);
    }

    public boolean isInProgress(LocalDateTime now) {
        LocalDate today = now.toLocalDate();
        now.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        return (!today.isBefore(startDate)) && (!today.isAfter(endDate));
    }

    public static Promotion findPromotion(String name) {
        return Arrays.stream(Promotion.values())
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst()
                .orElse(NULL);
    }
}
