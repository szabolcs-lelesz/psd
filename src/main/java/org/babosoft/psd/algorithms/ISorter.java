package org.babosoft.psd.algorithms;

import org.babosoft.psd.configurations.DataProvider;

public interface ISorter {
    void setDataProvider(DataProvider dataProvider);
    double[] compute();
}
