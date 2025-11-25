package org.babosoft.psd.algorithms.bitonic.merge;

import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.concurrent.RecursiveAction;

public class SimdParallelSwapMergeTask extends RecursiveAction {
    static class SimdParallelSwapTask extends RecursiveAction {
        private final double[] array;
        private final int start;
        private final int end;
        private final int offset;
        private final boolean ascending;

        // Hardverfüggő vektor méret (pl. 256 vagy 512 bit)
        private static final VectorSpecies<Double> SPECIES = DoubleVector.SPECIES_PREFERRED;
        // Küszöbérték: ennyi elem alatt már nem bontjuk tovább szálakra, hanem megcsináljuk SIMD-vel egyben
        private static final int THRESHOLD = 16_000;

        public SimdParallelSwapTask(double[] array, int start, int end, int offset, boolean ascending) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.offset = offset;
            this.ascending = ascending;
        }

        @Override
        protected void compute() {
            if (end - start < THRESHOLD) {
                computeSimd();
            } else {
                int mid = (start + end) / 2;
                invokeAll(
                        new SimdParallelSwapTask(array, start, mid, offset, ascending),
                        new SimdParallelSwapTask(array, mid, end, offset, ascending)
                );
            }
        }

        private void computeSimd() {
            int i = start;
            int loopBound = SPECIES.loopBound(end - start) + start;

            // A. Vektoros szakasz (gyors)
            for (; i < loopBound; i += SPECIES.length()) {
                var v1 = DoubleVector.fromArray(SPECIES, array, i);
                var v2 = DoubleVector.fromArray(SPECIES, array, i + offset);

                var minVec = v1.min(v2);
                var maxVec = v1.max(v2);

                if (ascending) {
                    minVec.intoArray(array, i);
                    maxVec.intoArray(array, i + offset);
                } else {
                    maxVec.intoArray(array, i);
                    minVec.intoArray(array, i + offset);
                }
            }

            // B. Maradék szakasz (ha van)
            for (; i < end; i++) {
                double v1 = array[i];
                double v2 = array[i + offset];
                boolean swapNeeded = ascending ? (v1 > v2) : (v1 < v2);
                if (swapNeeded) {
                    array[i] = v2;
                    array[i + offset] = v1;
                }
            }
        }
    }

    private final double[] array;
    private final int low;
    private final int count;
    private final boolean ascending;

    public SimdParallelSwapMergeTask(double[] array, int low, int count, boolean ascending) {
        this.array = array;
        this.low = low;
        this.count = count;
        this.ascending = ascending;
    }
    @Override
    protected void compute() {
        if (count <= 1) return;

        int k = count / 2;
        SimdParallelSwapTask swapTask = new SimdParallelSwapTask(array, low, low + k, k, ascending);
        swapTask.invoke();
        SimdParallelSwapMergeTask left = new SimdParallelSwapMergeTask(array, low, k, ascending);
        SimdParallelSwapMergeTask right = new SimdParallelSwapMergeTask(array, low + k, k, ascending);
        invokeAll(left, right);
    }
}
