package store.model;

import static store.util.constants.StringConstants.DATE_FORMAT;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public enum Promotion {

    SODA_PROMOTION("탄산2+1", 2, 1, "2024-01-01", "2024-12-31"),
    RECOMMENDED("MD추천상품", 1, 1, "2024-01-01", "2024-12-31"),
    SURPRISE("반짝할인", 1, 1, "2024-11-01", "2024-11-30"),
    NONE("null", 1, 0, "1970-01-01", "9999-12-31");

    private final String name;
    private final int buyCount;
    private final int giveawayCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    Promotion(String name, int buyCount, int giveawayCount, String startDate, String endDate) {
        this.name = name;
        this.buyCount = buyCount;
        this.giveawayCount = giveawayCount;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
    }

    public String getName() {
        return name;
    }

    public int getGiveawayCount() {
        return giveawayCount;
    }

    public int getPromotionCount() {
        return buyCount + giveawayCount;
    }

    public boolean isInProgress() {
        LocalDate now = DateTimes.now().toLocalDate();
        now.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        return (!now.isBefore(startDate)) && (!now.isAfter(endDate));
    }

    public static Promotion findPromotion(String name) {
        return Arrays.stream(Promotion.values())
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst()
                .orElse(NONE);
    }
}
