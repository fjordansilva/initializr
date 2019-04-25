package io.spring.initializr.generator.environment.gcp;

import io.spring.initializr.generator.environment.Environment;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class GCPEnvironment implements Environment {

    /**
     * Google Cloud Platform {@link Environment} identifier.
     */
    public static final String ID = "gcp";

    @Override
    public String id() {
        return GCPEnvironment.ID;
    }

    @Override
    public String toString() {
        return id();
    }
}
