package org.babosoft.psd.services;

import org.babosoft.psd.algorithms.ISorter;
import org.babosoft.psd.configurations.DataProvider;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BenchmarkService {
    private final static int WARMUP_ITERATIONS = 5;
    private final DataProvider dataProvider;
    private final ConsoleTable consoleTable;
    private DataProvider warmupDataProvider;

    private final List<ISorter> algorithmList = new ArrayList<>();

    public BenchmarkService(DataProvider dataProvider, ConsoleTable consoleTable) {
        this.dataProvider = dataProvider;
        this.consoleTable = consoleTable;
    }

    public void registerAlgorithm(ISorter algorithm) {
        algorithmList.add(algorithm);
    }
    public void list(){
        for (ISorter algorithm : algorithmList) {
            System.out.println(algorithm);
        }
    }

    private DataProvider warmupDataProvider() {
        if (warmupDataProvider == null) {
            warmupDataProvider = new DataProvider() {
                private static final int SIZE = 50_000;
                private double[] array;
                private boolean initialized = false;

                @Override
                public double[] getListToSort() {
                    this.generate();
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
                            this.array[i] = r.nextInt();
                        }
                        this.initialized = true;
                    }
                }
            };
        }
        return this.warmupDataProvider;

    }

    public void warmup(){
        for(int i = 0; i < WARMUP_ITERATIONS; i++) {
            DataProvider warmupDataProvider = warmupDataProvider();
            for (ISorter algorithm : algorithmList) {
                    algorithm.setDataProvider(warmupDataProvider);
                    double[] result = algorithm.compute();
                    if (!isValid(result)) {
                        System.err.println(MessageFormat.format("Algorithm failed validation: {0}", algorithm));
                    }
            }
        }
    }

    public void execute(){
        for (ISorter algorithm : algorithmList) {
            algorithm.setDataProvider(this.dataProvider);
            long start = System.currentTimeMillis();
            double[] result = algorithm.compute();
            long time = System.currentTimeMillis() - start;
            consoleTable.addRow(algorithm.getName(), dataProvider.getArraySize(), time + " ms", "✅ OK");
            if (!isValid(result)) {
                consoleTable.addRow(algorithm.getName(), dataProvider.getArraySize(), "HIBA", "❌ ");
            }
        }
    }

    public boolean isValid(double[] array) {
        for(int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

}
