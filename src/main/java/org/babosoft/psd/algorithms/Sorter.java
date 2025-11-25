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

}
