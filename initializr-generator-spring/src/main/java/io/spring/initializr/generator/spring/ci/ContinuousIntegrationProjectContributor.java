package io.spring.initializr.generator.spring.ci;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public abstract class ContinuousIntegrationProjectContributor implements ProjectContributor {

    protected final ContinuousIntegrationDocument containerDocument;

    public ContinuousIntegrationProjectContributor(ContinuousIntegrationDocument containerDocument) {
        this.containerDocument = containerDocument;
    }
}
