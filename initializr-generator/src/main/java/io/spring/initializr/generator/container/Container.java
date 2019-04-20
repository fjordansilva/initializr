package io.spring.initializr.generator.container;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.Objects;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public interface Container {

    String id();

    static Container forId(String id) {
        return SpringFactoriesLoader
                .loadFactories(ContainerFactory.class, Container.class.getClassLoader())
                .stream()
                .map((factory) -> factory.createContainer(id))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unrecognized container id '" + id + "'"));
    }
}
