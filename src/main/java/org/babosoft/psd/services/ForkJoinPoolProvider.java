package org.babosoft.psd.services;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Getter
@Service
public class ForkJoinPoolProvider {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

}
