package org.babosoft.psd.configurations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface QuickSortConfig {
    @Getter
    @NoArgsConstructor
    class QuickSortInfo {
        private @NotNull @Min(1) Integer threshold = 1000;

    }
    @NotNull @Valid QuickSortInfo getQuickSortInfo();
}
