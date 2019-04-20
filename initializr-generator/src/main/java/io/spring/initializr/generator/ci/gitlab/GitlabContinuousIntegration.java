package io.spring.initializr.generator.ci.gitlab;

import io.spring.initializr.generator.ci.ContinuousIntegration;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class GitlabContinuousIntegration implements ContinuousIntegration {
    /**
     * Gitlab {@link ContinuousIntegration} identifier.
     */
    public static final String ID = "gitlab";

    @Override
    public String id() {
        return GitlabContinuousIntegration.ID;
    }

    @Override
    public String toString() {
        return id();
    }
}
