package org.babosoft.psd.services;

import lombok.AllArgsConstructor;
import org.babosoft.psd.algorithms.ISorter;
import org.babosoft.psd.configurations.DataProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class BenchmarkService {
    private final static int WARMUP_ITERATIONS = 5;
    private final DataProvider dataProvider;
    private final List<ISorter> algorithmList = new ArrayList<>();
    public void registerAlgorithm(ISorter algorithm) {
        algorithmList.add(algorithm);
    }
    public void list(){
        for (ISorter algorithm : algorithmList) {
            System.out.println(algorithm);
        }
    }

    private DataProvider warmupDataProvider() {
        return new DataProvider() {
            private static final int SIZE = 50_000;
            private double[] array;
            private boolean initialized = false;

            @Override
            public double[] getListToSort() {
                return this.array.clone();
            }

            @Override
            public int getArraySize() {
                return SIZE;
            }

            @Override
            public void generate() {
                if (!initialized) {
                    this.array = new double[SIZE];
                    Random r = new Random();
                    for (int i = 0; i < SIZE; i++) {
                        this.array[i] = r.nextDouble();
                    }
                    this.initialized = true;
                }
            }
        };
    }

    public void warmup(){
        for(int i = 0; i < WARMUP_ITERATIONS; i++) {
            DataProvider warmupDataProvider = warmupDataProvider();
            for (ISorter algorithm : algorithmList) {
                algorithm.setDataProvider(warmupDataProvider);
                algorithm.compute();
            }

        }
    }

}
