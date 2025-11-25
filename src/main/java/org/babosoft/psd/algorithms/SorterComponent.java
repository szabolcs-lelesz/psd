package org.babosoft.psd.algorithms;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface SorterComponent {
    @AliasFor(annotation = Component.class, attribute = "value")
    String value();
    SortType type();
}
