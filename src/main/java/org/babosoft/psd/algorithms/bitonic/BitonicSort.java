package org.babosoft.psd.algorithms.bitonic;

import org.babosoft.psd.algorithms.ParallelSorter;
import org.babosoft.psd.configurations.DataProvider;
import org.babosoft.psd.services.ForkJoinPoolProvider;
import org.babosoft.psd.services.BenchmarkService;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public abstract class BitonicSort extends ParallelSorter {
    public BitonicSort(BenchmarkService benchmarkService,
                       DataProvider dataProvider,
                       ForkJoinPoolProvider forkJoinPoolProvider
    ) {
        super(benchmarkService, dataProvider, forkJoinPoolProvider);
    }

    protected abstract RecursiveAction getMergeTask(double[] array, int low, int count, boolean ascending);




    private double[] normalize(double[] array) {
        int originalSize = array.length;
        // 1. Kiszámoljuk a következő 2-es hatványt a paddinghez
        int paddedSize = 1;
        while (paddedSize < array.length) {
            paddedSize <<= 1; // szorzás 2-vel (biteltolás)
        }
        double[] paddedArray = Arrays.copyOf(array, paddedSize);
        for (int i = originalSize; i < paddedSize; i++) {
            paddedArray[i] = Double.MAX_VALUE;
        }
        return paddedArray;
    }

    @Override
    public double[] compute() {
        double[] result = this.dataProvider.getListToSort();
        int originalSize = result.length;
        double[] paddedArray = this.normalize(result);
        int paddedSize = paddedArray.length;
        RecursiveAction sortTask = getMergeTask(paddedArray, 0, paddedSize, true);
        this.forkJoinPoolProvider.getForkJoinPool().invoke(sortTask);
        return (originalSize ==  paddedSize)
                ? paddedArray
                : Arrays.copyOf(paddedArray, originalSize);
    }
}
