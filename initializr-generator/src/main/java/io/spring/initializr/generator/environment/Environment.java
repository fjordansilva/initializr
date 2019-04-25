package io.spring.initializr.generator.environment;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.Objects;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public interface Environment {

    String id();

    static Environment forId(String id) {
        return SpringFactoriesLoader
                .loadFactories(EnvironmentFactory.class, Environment.class.getClassLoader())
                .stream()
                .map((factory) -> factory.createEnvironment(id))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unrecognized container id '" + id + "'"));
    }
}
