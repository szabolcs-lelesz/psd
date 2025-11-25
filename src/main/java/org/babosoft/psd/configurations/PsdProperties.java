package org.babosoft.psd.configurations;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
class PsdProperties {
    private @NotNull @Min(1) Integer arraySize= 10_000;
}
