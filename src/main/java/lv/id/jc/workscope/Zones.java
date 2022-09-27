package lv.id.jc.workscope;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;

public class Zones implements WorkScope {
    private final Collection<? extends WorkScope> workScopes;

    private Zones(Collection<? extends WorkScope> workScopes) {
        this.workScopes = workScopes;
    }

    public static Zones of(WorkScope... workScopes) {
        return new Zones(Arrays.asList(workScopes));
    }

    @Override
    public BigDecimal bill() {
        return workScopes.stream()
                .map(WorkScope::bill)
                .reduce(ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal area() {
        return Surface.summing(workScopes).area();
    }
}
