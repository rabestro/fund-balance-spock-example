package lv.id.jc.worker;

import lv.id.jc.workscope.Surface;

import java.math.BigDecimal;

public interface Worker {
    BigDecimal salary(Surface surface);

    BigDecimal personalBonus(BigDecimal vendorBonus);
}
