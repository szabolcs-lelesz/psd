package org.babosoft.psd.services;

import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class ComparatorService implements Comparator<Double> {


    @Override
    public int compare(Double o1, Double o2) {
        return o1.compareTo(o2);
    }
}
