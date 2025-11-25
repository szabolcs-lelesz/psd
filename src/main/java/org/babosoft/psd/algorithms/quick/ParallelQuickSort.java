package org.babosoft.psd.algorithms.quick;

import org.babosoft.psd.algorithms.ParallelSorter;
import org.babosoft.psd.algorithms.SorterComponent;
import org.babosoft.psd.algorithms.SortType;
import org.babosoft.psd.configurations.DataProvider;
import org.babosoft.psd.services.ForkJoinPoolProvider;
import org.babosoft.psd.services.BenchmarkService;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;


@SorterComponent(value = "parallelQuickSort",type = SortType.PARALLEL)
class ParallelQuickSort extends ParallelSorter {
    static class QuickSortTask extends RecursiveAction {
        // Küszöbérték a párhuzamosításhoz, ki kelelne vezetni property-be
        private static final int THRESHOLD = 1000;
        private final double[] array;
        private final int low;
        private final int high;
        public QuickSortTask(double[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        private int partition(double[] array, int low, int high) {
            double pivot = array[high]; // A pivot most double típusú
            int i = (low - 1);

            for (int j = low; j < high; j++) {
                // TODO: Ide lehetne behozni a EPSILONT
                // Itt hasonlítjuk össze a double értékeket
                if (array[j] <= pivot) {
                    i++;
                    swap(array, i, j);
                }
            }
            swap(array, i + 1, high);
            return i + 1;
        }

        // Csere metódus double típusra
        private void swap(double[] array, int i, int j) {
            double temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        @Override
        protected void compute() {
            if (high - low < THRESHOLD) {
                // Kis méretnél a beépített Arrays.sort-ot hívjuk
                Arrays.sort(array, low, high + 1);
            } else {
                int pivotIndex = partition(array, low, high);
                QuickSortTask leftTask = new QuickSortTask(array, low, pivotIndex - 1);
                QuickSortTask rightTask = new QuickSortTask(array, pivotIndex + 1, high);
                invokeAll(leftTask, rightTask);
            }
        }
    }


    public ParallelQuickSort(BenchmarkService benchmarkService, DataProvider dataProvider, ForkJoinPoolProvider forkJoinPoolProvider) {
        super(benchmarkService, dataProvider, forkJoinPoolProvider);
    }

    @Override
    public double[] compute() {
        double[] result = this.dataProvider.getListToSort();
        if (result.length < 2) {
            return result;
        }
        QuickSortTask mainTask = new QuickSortTask(result, 0, result.length - 1);
        this.forkJoinPoolProvider.getForkJoinPool().invoke(mainTask);
        return result;
    }
}
