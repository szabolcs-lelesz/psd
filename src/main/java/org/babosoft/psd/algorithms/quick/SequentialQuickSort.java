package org.babosoft.psd.algorithms.quick;

import org.babosoft.psd.algorithms.SortType;
import org.babosoft.psd.algorithms.Sorter;
import org.babosoft.psd.algorithms.SorterComponent;
import org.babosoft.psd.configurations.DataProvider;
import org.babosoft.psd.services.BenchmarkService;

import java.util.Arrays;

@SorterComponent(value = "sequentialQuickSort",type = SortType.SEQUENTIAL)
public class SequentialQuickSort extends Sorter {
    public SequentialQuickSort(BenchmarkService benchmarkService, DataProvider dataProvider) {
        super(benchmarkService, dataProvider);
    }

    @Override
    public double[] compute() {
        double[] result = dataProvider.getListToSort();
        Arrays.sort(result);
        return result;
    }
}
