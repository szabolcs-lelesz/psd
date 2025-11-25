package org.babosoft.psd.algorithms.bitonic;

import org.babosoft.psd.algorithms.SortType;
import org.babosoft.psd.algorithms.SorterComponent;
import org.babosoft.psd.algorithms.bitonic.merge.SequentialSwapMergeTask;
import org.babosoft.psd.configurations.DataProvider;
import org.babosoft.psd.services.ForkJoinPoolProvider;
import org.babosoft.psd.services.BenchmarkService;

import java.util.concurrent.RecursiveAction;

@SorterComponent(value = "sequentialSwapBitonicSort",type = SortType.PARALLEL)
class SequentialSwapBitonicSort extends BitonicSort{
    public SequentialSwapBitonicSort(BenchmarkService benchmarkService, DataProvider dataProvider, ForkJoinPoolProvider forkJoinPoolProvider) {
        super(benchmarkService, dataProvider, forkJoinPoolProvider);
    }

    @Override
    protected RecursiveAction getMergeTask(double[] array, int low, int count, boolean ascending) {
        return new SequentialSwapMergeTask(array, low, count, ascending);
    }
}
