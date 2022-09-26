package lv.id.jc.workcost;

import lv.id.jc.workscope.Surface;

import java.math.BigDecimal;
import java.util.function.Function;

@FunctionalInterface
public interface PriceFunction extends Function<Surface, BigDecimal> {

}
