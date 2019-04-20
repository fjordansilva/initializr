package io.spring.initializr.generator.container.docker;

import io.spring.initializr.generator.container.Container;
import io.spring.initializr.generator.container.ContainerFactory;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class DockerContainerFactory implements ContainerFactory {
    /**
     * Creates and returns a {@link Container} for the given id. If the factory does not
     * recognise the given {@code id}, {@code null} should be returned.
     *
     * @param id
     *         the id of the Container
     *
     * @return the Container or {@code null}
     */
    @Override
    public Container createContainer(String id) {
        if (DockerContainer.ID.equals(id)) {
            return new DockerContainer();
        }
        return null;
    }
}
