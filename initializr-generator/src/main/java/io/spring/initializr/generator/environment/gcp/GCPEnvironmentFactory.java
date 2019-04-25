package io.spring.initializr.generator.environment.gcp;

import io.spring.initializr.generator.environment.Environment;
import io.spring.initializr.generator.environment.EnvironmentFactory;
import io.spring.initializr.generator.environment.aws.AWSEnvironment;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class GCPEnvironmentFactory implements EnvironmentFactory {

    /**
     * Creates and returns a {@link Environment} for the given id. If the factory does not
     * recognise the given {@code id}, {@code null} should be returned.
     *
     * @param id
     *         the id of the Container
     *
     * @return the Container or {@code null}
     */
    @Override
    public Environment createEnvironment(String id) {
        if (GCPEnvironment.ID.equals(id)) {
            return new GCPEnvironment();
        }
        return null;
    }
}
