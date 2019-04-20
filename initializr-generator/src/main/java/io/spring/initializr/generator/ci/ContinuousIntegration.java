package io.spring.initializr.generator.ci;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.Objects;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public interface ContinuousIntegration {

    String id();

    static ContinuousIntegration forId(String id) {
        return SpringFactoriesLoader
                .loadFactories(ContinuousIntegrationFactory.class, ContinuousIntegration.class.getClassLoader())
                .stream().map((factory) -> factory.createContinuousIntegration(id))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unrecognized ContiousIntegration id '" + id + "'"));
    }
}
