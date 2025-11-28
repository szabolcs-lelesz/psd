package org.babosoft.psd.configurations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "application")
class PsdProperties implements QuickSortConfig, WarmupConfig {
    private @Getter @NotNull @Min(1) Integer arraySize= 1_000_000;
    private @Valid WarmupInfo warmup;
    private @NotNull @Valid QuickSortInfo quickSort;

    @Override
    public QuickSortInfo getQuickSortInfo() {
        return this.quickSort;
    }

    @Override
    public Optional<WarmupInfo> getWarmupInfo() {
        return Optional.ofNullable(this.warmup);
    }
}
