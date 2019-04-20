package io.spring.initializr.generator.ci.jenkins;

import io.spring.initializr.generator.ci.ContinuousIntegration;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class JenkinsContinuousIntegration implements ContinuousIntegration {
    /**
     * Jenkins {@link ContinuousIntegration} identifier.
     */
    public static final String ID = "jenkins";

    @Override
    public String id() {
        return JenkinsContinuousIntegration.ID;
    }

    @Override
    public String toString() {
        return id();
    }

}
