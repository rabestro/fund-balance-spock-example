package lv.id.jc.workscope;

import java.math.BigDecimal;

@FunctionalInterface
public interface Billable {

    BigDecimal bill();
}
