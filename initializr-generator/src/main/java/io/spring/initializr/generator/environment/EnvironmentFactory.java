package io.spring.initializr.generator.environment;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public interface EnvironmentFactory {

    /**
     * Creates and returns a {@link Environment} for the given id. If the factory does not
     * recognise the given {@code id}, {@code null} should be returned.
     *
     * @param id
     *         the id of the Container
     *
     * @return the Container or {@code null}
     */
    Environment createEnvironment(String id);
}
