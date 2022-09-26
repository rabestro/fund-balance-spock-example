package lv.id.jc.workscope;

import lv.id.jc.workcost.MaterialPrice;
import lv.id.jc.workcost.PriceFunction;
import lv.id.jc.workcost.WorkPrice;
import lv.id.jc.workcost.ZonePrice;

import java.util.*;

public final class ZoneFactory {
    private final Map<String, PriceFunction> types;

    public ZoneFactory(Map<String, PriceFunction> types) {
        this.types = types;
    }

    public ZoneFactory() {
        this(new HashMap<>());
    }

    public ZoneFactory add(String name, double workPrice) {
        Objects.requireNonNull(name);
        PriceFunction zonePrice = ZonePrice.of(MaterialPrice.norm(), WorkPrice.of(workPrice));
        types.put(name.toLowerCase(), zonePrice);
        return this;
    }

    public Zone create(String type, Surface surface, Surface apertures) {
        return new Zone(zonePriceFunction(type), surface, apertures);
    }

    public Zone create(String type, Surface surface) {
        return new Zone(zonePriceFunction(type), surface);
    }

    private PriceFunction zonePriceFunction(String type) {
        return Optional
                .ofNullable(types.get(type.toLowerCase()))
                .orElseThrow(() -> new NoSuchElementException("no such zone type"));
    }
}
