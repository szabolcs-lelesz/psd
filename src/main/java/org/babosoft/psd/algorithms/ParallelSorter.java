package org.babosoft.psd.algorithms;

import org.babosoft.psd.configurations.DataProvider;
import org.babosoft.psd.services.ForkJoinPoolProvider;
import org.babosoft.psd.services.BenchmarkService;

public abstract class ParallelSorter extends Sorter {
    protected final ForkJoinPoolProvider forkJoinPoolProvider;

    public ParallelSorter(BenchmarkService benchmarkService, DataProvider dataProvider, ForkJoinPoolProvider forkJoinPoolProvider) {
        super(benchmarkService,dataProvider);
        this.forkJoinPoolProvider = forkJoinPoolProvider;
    }
}
