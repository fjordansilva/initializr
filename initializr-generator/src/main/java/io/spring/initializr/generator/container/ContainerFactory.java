package io.spring.initializr.generator.container;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public interface ContainerFactory {

    /**
     * Creates and returns a {@link Container} for the given id. If the factory does not
     * recognise the given {@code id}, {@code null} should be returned.
     *
     * @param id
     *         the id of the Container
     *
     * @return the Container or {@code null}
     */
    Container createContainer(String id);

}
