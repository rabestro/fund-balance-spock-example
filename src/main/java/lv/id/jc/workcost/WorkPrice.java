package lv.id.jc.workcost;

import lv.id.jc.workscope.Surface;

import java.math.BigDecimal;

public final class WorkPrice implements PriceFunction {
    private static final BigDecimal MULTI_DAY_PRICE_FACTOR = BigDecimal.valueOf(1.1);
    private static final BigDecimal ONE_DAY_MAX_AREA = new BigDecimal(50);

    private final BigDecimal price;

    private WorkPrice(double price) {
        this.price = BigDecimal.valueOf(price);
    }

    public static WorkPrice of(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("the work price must be a positive number or zero");
        }
        return new WorkPrice(price);
    }

    private boolean isOneDayWork(Surface surface) {
        return surface.area().compareTo(ONE_DAY_MAX_AREA) < 0;
    }

    private BigDecimal oneDayCost(Surface surface) {
        return surface.area().multiply(price);
    }

    private BigDecimal multiDayCost(Surface surface) {
        return oneDayCost(surface).multiply(MULTI_DAY_PRICE_FACTOR);
    }

    @Override
    public BigDecimal apply(Surface surface) {
        return isOneDayWork(surface) ? oneDayCost(surface) : multiDayCost(surface);
    }
}
