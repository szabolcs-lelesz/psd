package org.babosoft.psd.algorithms;

import org.babosoft.psd.configurations.DataProvider;

public interface ISorter {
    String getName();
    void setDataProvider(DataProvider dataProvider);
    double[] compute();
}
