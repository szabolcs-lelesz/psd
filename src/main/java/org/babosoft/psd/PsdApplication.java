package org.babosoft.psd;

import org.babosoft.psd.services.BenchmarkService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PsdApplication {

    public static void main(String[] args) {
       ConfigurableApplicationContext context = SpringApplication.run(PsdApplication.class, args);
       BenchmarkService benchmarkService = context.getBean(BenchmarkService.class);
       benchmarkService.warmup();
    }

}
