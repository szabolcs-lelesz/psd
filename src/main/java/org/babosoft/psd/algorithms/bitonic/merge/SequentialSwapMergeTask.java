package org.babosoft.psd.algorithms.bitonic.merge;

import java.util.concurrent.RecursiveAction;

public class SequentialSwapMergeTask extends RecursiveAction {
    private final double[] array;
    private final int low;
    private final int count;
    private final boolean ascending;

    public SequentialSwapMergeTask(double[] array, int low, int count, boolean ascending) {
        this.array = array;
        this.low = low;
        this.count = count;
        this.ascending = ascending;
    }

    @Override
    protected void compute() {
        if (count <= 1) return;

        int k = count / 2;

        // 1. Lépés: Összehasonlítás és csere a távoli elemekkel
        // Ez a lépés nagyon jól párhuzamosítható lenne, de a memória sávszélesség miatt
        // gyakran jobb egyszerű ciklusban futtatni ezen a szinten.
        for (int i = low; i < low + k; i++) {
            compareAndSwap(array, i, i + k, ascending);
        }

        // 2. Lépés: Rekurzív hívás a két félre
        SequentialSwapMergeTask left = new SequentialSwapMergeTask(array, low, k, ascending);
        SequentialSwapMergeTask right = new SequentialSwapMergeTask(array, low + k, k, ascending);

        invokeAll(left, right);
    }
    private void compareAndSwap(double[] arr, int i, int j, boolean direction) {
        if (direction == (arr[i] > arr[j])) {
            double temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
