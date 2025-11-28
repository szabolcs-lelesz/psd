package org.babosoft.psd.configurations;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public interface BitonicConfig {
    interface ParallelSwapConfig {
        @NotNull @Min(1) Integer getSwapThreshold();
    }
    interface SimdConfig {}
}
