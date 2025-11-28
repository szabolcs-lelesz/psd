package org.babosoft.psd.configurations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;

public interface WarmupConfig {
    @Data
    class WarmupInfo{
        private @NotNull @Min(1) Integer iterations;
        private @NotNull @Min(1) Integer size;
    }
    @NotNull
    Optional<@Valid WarmupInfo> getWarmupInfo();
}
