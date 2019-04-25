package io.spring.initializr.generator.environment.intra;

import io.spring.initializr.generator.environment.Environment;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class IntraEnvironment implements Environment {

    /**
     * Renault Intranet Platform {@link Environment} identifier.
     */
    public static final String ID = "intra";

    @Override
    public String id() {
        return IntraEnvironment.ID;
    }

    @Override
    public String toString() {
        return id();
    }
}
