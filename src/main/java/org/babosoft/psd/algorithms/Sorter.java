package org.babosoft.psd.algorithms;

import lombok.Setter;
import org.babosoft.psd.configurations.DataProvider;
import org.babosoft.psd.services.BenchmarkService;

public abstract class Sorter implements ISorter {
    protected final BenchmarkService benchmarkService;
    protected @Setter DataProvider dataProvider;

    public Sorter(BenchmarkService benchmarkService, DataProvider dataProvider) {
        this.benchmarkService = benchmarkService;
        this.dataProvider = dataProvider;
        this.benchmarkService.registerAlgorithm(this);
    }

    @Override
    public String getName() {
        SorterComponent sorterComponent = this.getClass().getAnnotation(SorterComponent.class);
        if (sorterComponent == null) {
            throw new IllegalArgumentException("SorterComponent annotation should be used with @" + SorterComponent.class.getSimpleName());
        }
        return sorterComponent.value();
    }
}
