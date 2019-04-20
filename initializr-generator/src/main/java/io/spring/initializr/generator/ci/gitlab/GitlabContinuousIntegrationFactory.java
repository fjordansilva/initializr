package io.spring.initializr.generator.ci.gitlab;

import io.spring.initializr.generator.ci.ContinuousIntegration;
import io.spring.initializr.generator.ci.ContinuousIntegrationFactory;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class GitlabContinuousIntegrationFactory implements ContinuousIntegrationFactory {
    /**
     * Creates and returns a {@link ContinuousIntegration} for the given id. If the factory does not
     * recognise the given {@code id}, {@code null} should be returned.
     *
     * @param id
     *         the id of the ContinuousIntegration
     *
     * @return the ContinuousIntegration or {@code null}
     */
    @Override
    public ContinuousIntegration createContinuousIntegration(String id) {
        if (GitlabContinuousIntegration.ID.equals(id)) {
            return new GitlabContinuousIntegration();
        }
        return null;
    }
}
