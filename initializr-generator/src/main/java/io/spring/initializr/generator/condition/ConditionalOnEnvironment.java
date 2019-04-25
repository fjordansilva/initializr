package io.spring.initializr.generator.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnEnvironmentCondition.class)
public @interface ConditionalOnEnvironment {

    String value();

}
