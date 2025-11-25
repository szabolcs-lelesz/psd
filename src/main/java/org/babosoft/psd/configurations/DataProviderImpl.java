package org.babosoft.psd.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
class DataProviderImpl implements DataProvider {
    private boolean initialized = false;
    private final PsdProperties psdProperties;

    private double[] unsortedArray= new double[]{};

    DataProviderImpl(PsdProperties psdProperties) {
        this.psdProperties = psdProperties;
    }

    public double[] getListToSort(){
        if(!initialized){
            generate();
        }
        return unsortedArray.clone();
    }

    @Override
    public int getArraySize() {
        return psdProperties.getArraySize();
    }

    @PostConstruct
    void initialize(){
        generate();
    }
    public void generate(){
        this.unsortedArray = ThreadLocalRandom.current()
                .doubles(psdProperties.getArraySize(), 0.0, 10000.0)
                .toArray();
        this.initialized = true;
    }
}
