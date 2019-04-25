package io.spring.initializr.generator.environment.aws;

import io.spring.initializr.generator.environment.Environment;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class AWSEnvironment implements Environment {

    /**
     * AWS {@link Environment} identifier.
     */
    public static final String ID = "aws";

    @Override
    public String id() {
        return AWSEnvironment.ID;
    }

    @Override
    public String toString() {
        return id();
    }
}
