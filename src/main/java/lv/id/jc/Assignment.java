package lv.id.jc;

import lv.id.jc.worker.Worker;
import lv.id.jc.workscope.WorkScope;

import java.math.BigDecimal;

public final class Assignment implements FundBalance {
    private final Worker worker;
    private final WorkScope workScope;
    private final BigDecimal bonus;

    public Assignment(Worker worker, WorkScope workScope, BigDecimal bonus) {
        this.workScope = workScope;
        this.worker = worker;
        this.bonus = bonus;
    }

    @Override
    public BigDecimal fundBalance() {
        return workScope.bill()
                .subtract(worker.salary(workScope))
                .subtract(worker.personalBonus(bonus));
    }

}
