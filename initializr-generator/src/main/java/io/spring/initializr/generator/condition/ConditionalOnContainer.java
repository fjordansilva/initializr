package io.spring.initializr.generator.condition;

import io.spring.initializr.generator.container.Container;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * Condition that matches when a generated project will use a particular
 * {@link Container}.
 *
 * @author Andy Wilkinson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnContainerCondition.class)
public @interface ConditionalOnContainer {

    String value();

}
