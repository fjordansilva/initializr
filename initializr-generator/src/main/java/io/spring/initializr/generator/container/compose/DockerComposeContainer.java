package io.spring.initializr.generator.container.compose;

import io.spring.initializr.generator.container.Container;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class DockerComposeContainer implements Container {
    /**
     * Docker Compose {@link Container} identifier.
     */
    public static final String ID = "dockercompose";

    @Override
    public String id() {
        return DockerComposeContainer.ID;
    }

    @Override
    public String toString() {
        return id();
    }
}
