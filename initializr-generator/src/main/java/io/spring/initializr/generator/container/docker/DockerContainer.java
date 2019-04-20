package io.spring.initializr.generator.container.docker;

import io.spring.initializr.generator.container.Container;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class DockerContainer implements Container {
    /**
     * Docker {@link Container} identifier.
     */
    public static final String ID = "docker";

    @Override
    public String id() {
        return DockerContainer.ID;
    }

    @Override
    public String toString() {
        return id();
    }
}
