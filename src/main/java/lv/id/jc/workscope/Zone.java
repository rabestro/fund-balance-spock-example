package lv.id.jc.workscope;

import lv.id.jc.workcost.PriceFunction;

import java.math.BigDecimal;

public final class Zone implements WorkScope {
    private final PriceFunction priceFunction;
    private final Surface totalSurface;
    private final Surface apertures;

    public Zone(PriceFunction priceFunction, Surface totalSurface, Surface apertures) {
        this.priceFunction = priceFunction;
        this.totalSurface = totalSurface;
        this.apertures = apertures;
    }

    public Zone(PriceFunction priceFunction, Surface totalSurface) {
        this(priceFunction, totalSurface, Surface.zero());
    }

    @Override
    public BigDecimal area() {
        return totalSurface.subtract(apertures).area();
    }

    @Override
    public BigDecimal bill() {
        return priceFunction.apply(this);
    }
}
