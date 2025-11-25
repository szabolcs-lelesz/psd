package org.babosoft.psd.algorithms.bitonic.merge;

import java.util.concurrent.RecursiveAction;

public class ParallelSwapMergeTask extends RecursiveAction {
    static class ParallelSwapTask extends RecursiveAction {
        // Itt a küszöbérték lehet nagyobb, mert a compareAndSwap művelet nagyon gyors (olcsó).
        // Ha túl kicsire vesszük, a szálkezelés drágább lesz, mint a csere.
        private static final int SWAP_THRESHOLD = 8000;
        private final double[] array;
        private final int start;
        private final int end;
        private final int offset;
        private final boolean ascending;


        public ParallelSwapTask(double[] array, int start, int end, int offset, boolean ascending) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.offset = offset;
            this.ascending = ascending;
        }
        @Override
        protected void compute() {
            // Ha a tartomány kicsi, csináljuk meg sima ciklussal
            if (end - start < SWAP_THRESHOLD) {
                for (int i = start; i < end; i++) {
                    compareAndSwap(array, i, i + offset, ascending);
                }
            } else {
                // Ha nagy, felezzük meg a tartományt
                int mid = (start + end) / 2;
                ParallelSwapTask first = new ParallelSwapTask(array, start, mid, offset, ascending);
                ParallelSwapTask second = new ParallelSwapTask(array, mid, end, offset, ascending);
                invokeAll(first, second);
            }
        }

        private void compareAndSwap(double[] arr, int i, int j, boolean direction) {
            if (direction == (arr[i] > arr[j])) {
                double temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }



    }


    private final double[] array;
    private final int low;
    private final int count;
    private final boolean ascending;

    public ParallelSwapMergeTask(double[] array, int low, int count, boolean ascending) {
        this.array = array;
        this.low = low;
        this.count = count;
        this.ascending = ascending;
    }

    @Override
    protected void compute() {
        if (count <= 1) return;
        int k = count / 2;
        ParallelSwapTask swapTask = new ParallelSwapTask(array, low, low + k, k, ascending);
        swapTask.invoke(); // Megvárjuk, amíg minden csere lefut!
        ParallelSwapMergeTask left = new ParallelSwapMergeTask(array, low, k, ascending);
        ParallelSwapMergeTask right = new ParallelSwapMergeTask(array, low + k, k, ascending);
        invokeAll(left, right);
    }
}

